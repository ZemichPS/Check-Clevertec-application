package ru.clevertec.check.application.ports.intput;

import ru.clevertec.check.application.ports.output.*;
import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.domain.model.dto.OrderItemDto;
import ru.clevertec.check.domain.model.entity.*;
import ru.clevertec.check.domain.model.entity.factoty.CheckFactory;
import ru.clevertec.check.domain.model.exception.*;
import ru.clevertec.check.domain.model.exception.shared.AbstractException;
import ru.clevertec.check.domain.policy.discountpolicy.CardDiscountAccrualPolicy;
import ru.clevertec.check.domain.service.DiscountCardService;
import ru.clevertec.check.domain.service.DiscountService;
import ru.clevertec.check.domain.model.valueobject.*;
import ru.clevertec.check.domain.service.ProductPositionService;
import ru.clevertec.check.domain.specification.NotEmptyOrderMapSpecification;
import ru.clevertec.check.domain.specification.PositiveDebitCardBalanceSpecification;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class CheckInputPort implements CheckUseCase {
    private final CheckOutputPort checkOutputPort;
    private final ProductOutputPort productOutputPort;
    private final DiscountCardOutputPort discountCardOutputPort;
    private final ErrorOutputPort errorOutputPort;
    private final StdOutputPort stdOutputPort;
    private final DiscountService discountService = new DiscountService();

    public CheckInputPort(CheckOutputPort checkOutputPort,
                          ProductOutputPort productOutputPort,
                          DiscountCardOutputPort discountCardOutputPort,
                          ErrorOutputPort errorOutputPort, StdOutputPort stdOutputPort) {
        this.checkOutputPort = checkOutputPort;
        this.productOutputPort = productOutputPort;
        this.discountCardOutputPort = discountCardOutputPort;
        this.errorOutputPort = errorOutputPort;
        this.stdOutputPort = stdOutputPort;
    }


    @Override
    public Check create(Map<ProductId, Integer> orderMap, BigDecimal debitCardBalance) {

        checkDebitCardBalance(debitCardBalance);
        checkOnEmptyOrderMap(orderMap);

        DiscountCard discountCard = new NullDiscountCard();
        List<ProductPosition> productPositionsInStock = getProductPositionsInStock();

        final List<OrderItemDto> orderItems = mapOrderListToOrderItems(orderMap, discountCard, productPositionsInStock);
        final List<CheckItem> checkItems = mapOrderItemsToCheckItems(orderItems);

        Check newCheck = CheckFactory.getNewFromCheckItems(checkItems);
        newCheck.addDiscountCart(discountCard);
        checkForAbilityToPay(newCheck, debitCardBalance);
        stdOutputPort.printCheck(newCheck);

        try {
            return checkOutputPort.persist(newCheck);
        } catch (InvocationTargetException | IllegalAccessException | URISyntaxException | IOException e) {
            handleException(new InternalServerErrorException("Failed to persist check"));
            throw new RuntimeException();
        }
    }

    @Override
    public Check create(Map<ProductId, Integer> orderMap,
                        CardNumber cardNumber,
                        BigDecimal debitCardBalance) {

        checkOnEmptyOrderMap(orderMap);
        checkDebitCardBalance(debitCardBalance);

        final List<RealDiscountCard> allDiscountCard = findAllDiscountCard();
        DiscountCard discountCard = null;

        try {
            discountCard = DiscountCardService.findByCardNumber(allDiscountCard, cardNumber)
                    .orElseGet(() -> {
                        RealDiscountCard newDiscountCard = new RealDiscountCard(new CardId(100));
                        newDiscountCard.addCardNumber(cardNumber);
                        CardDiscountAccrualPolicy policy = new CardDiscountAccrualPolicy();
                        policy.apply(newDiscountCard);
                        return newDiscountCard;
                    });
        } catch (GenericSpecificationException exception) {
            handleException(exception);
        }

        List<ProductPosition> productPositionsInStock = getProductPositionsInStock();
        final List<OrderItemDto> orderItems = mapOrderListToOrderItems(orderMap, discountCard, productPositionsInStock);
        final List<CheckItem> checkItems = mapOrderItemsToCheckItems(orderItems);

        Check newCheck = CheckFactory.getNewFromCheckItems(checkItems);
        newCheck.addDiscountCart(discountCard);

        checkForAbilityToPay(newCheck, debitCardBalance);
        stdOutputPort.printCheck(newCheck);

        try {
            return checkOutputPort.persist(newCheck);
        } catch (InvocationTargetException | IllegalAccessException | IOException | URISyntaxException e) {
            handleException(new InternalServerErrorException("Failed to persist check"));
            throw new RuntimeException();
        }
    }

    private List<ProductPosition> getProductPositionsInStock() {
        List<ProductPosition> productPositionsInStock = List.of();
        try {
            productPositionsInStock = productOutputPort.findAll();
        } catch (URISyntaxException | IOException e) {
            handleException(new InternalServerErrorException(e.getMessage()));
        }
        return productPositionsInStock;
    }


    private void handleException(AbstractException exception) {
        String errorText = exception.getErrorText();
        try {
            errorOutputPort.writeError(errorText);
            stdOutputPort.printError(errorText);
            throw exception;
        } catch (IOException | IllegalAccessException | InvocationTargetException | URISyntaxException e) {
            throw exception;
        }

    }

    private void checkOnEmptyOrderMap(Map<ProductId, Integer> orderMap) {
        NotEmptyOrderMapSpecification notEmptyOrderMapSpecification = new NotEmptyOrderMapSpecification();
        try {
            notEmptyOrderMapSpecification.check(orderMap);
        } catch (GenericSpecificationException genericSpecificationException) {
            handleException(genericSpecificationException);
        }
    }

    private void checkDebitCardBalance(BigDecimal balance) {
        PositiveDebitCardBalanceSpecification specification = new PositiveDebitCardBalanceSpecification();
        try {
            specification.check(balance);
        } catch (NotEnoughMoneyException e) {
            handleException(e);
        }
    }

    private List<RealDiscountCard> findAllDiscountCard() {
        try {
            return discountCardOutputPort.findAll();
        } catch (URISyntaxException | IOException e) {
            handleException(new InternalServerErrorException("Failed to get discount cards"));
        }
        return List.of();
    }

    private List<OrderItemDto> mapOrderListToOrderItems(Map<ProductId, Integer> orderMap,
                                                        DiscountCard discountCard,
                                                        List<ProductPosition> productPositionsInStock) {
        return orderMap.entrySet().stream().map(
                entry -> {
                    ProductId productId = entry.getKey();
                    Integer requestedQuantity = entry.getValue();
                    ProductPosition position = null;
                    try {
                        position = ProductPositionService
                                .findProductPositionByProductId(productPositionsInStock, productId)
                                .orElseThrow(() -> new ProductNotFoundException("Exception"));
                    } catch (ProductNotFoundException exception) {
                        handleException(new ProductNotFoundException("Product with such id (%s) is no where to be found".formatted(productId.id())));
                    }
                    Integer quantityInStock = position.quantity();

                    if (quantityInStock < requestedQuantity)

                        handleException(new RequestedProductQuantityOutOfStockException(
                                "The requested quantity is out of stock. Requested quantity %S %s pieces, %s in stock"
                                        .formatted(position.product().getName().description(),
                                                requestedQuantity,
                                                quantityInStock)
                        ));

                    Product requestedProduct = position.product();

                    return new OrderItemDto(
                            discountCard,
                            requestedProduct.getSaleConditionType(),
                            requestedQuantity,
                            requestedProduct.getPrice().value(),
                            requestedProduct.getName().description()
                    );
                }
        ).toList();
    }

    private List<CheckItem> mapOrderItemsToCheckItems(List<OrderItemDto> orderItems) {
        return orderItems.stream().map(
                orderItem -> {
                    Integer quantity = orderItem.quantity();
                    String description = orderItem.description();
                    BigDecimal price = orderItem.price();
                    BigDecimal discount = discountService.computeDiscount(orderItem);
                    BigDecimal total = price.multiply(new BigDecimal(quantity));

                    return new CheckItem(
                            quantity,
                            description,
                            price,
                            discount,
                            total
                    );
                }
        ).toList();
    }

    public void checkForAbilityToPay(Check check, BigDecimal debitCardBalance) {
        BigDecimal totalWithDiscount = check.computeAndGetTotalPrices().totalWithDiscount();
        if (debitCardBalance.compareTo(totalWithDiscount) < 0)
            handleException(new NotEnoughMoneyException("Debit card balance is less than the total cost including the discount"));
    }
}

