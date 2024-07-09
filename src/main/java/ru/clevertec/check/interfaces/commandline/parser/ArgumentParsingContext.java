package ru.clevertec.check.interfaces.commandline.parser;

import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.math.BigDecimal;
import java.util.*;

public class ArgumentParsingContext {
    private CardNumber cardNumber;
    private BigDecimal balanceDebitCard;
    private Map<ProductId, Integer> productIdQuantityMap = new HashMap<>();

    public ArgumentParsingContext(CardNumber cardNumber,
                                  BigDecimal balanceDebitCard,
                                  Map<ProductId, Integer> productIdIntegerMap
    ) {
        this.cardNumber = cardNumber;
        this.balanceDebitCard = balanceDebitCard;
        this.productIdQuantityMap = productIdIntegerMap;
    }

    public ArgumentParsingContext() {
    }

    public Optional<CardNumber> getCardNumber() {
        return Optional.ofNullable(cardNumber);
    }

    public ArgumentParsingContext setCardNumber(CardNumber cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public Optional<BigDecimal> getBalanceDebitCard() {
        return Optional.ofNullable(balanceDebitCard);
    }

    public ArgumentParsingContext setBalanceDebitCard(BigDecimal balanceDebitCard) {
        this.balanceDebitCard = balanceDebitCard;
        return this;
    }

    public Map<ProductId, Integer> getProductIdQuantityMap() {
        return productIdQuantityMap;
    }

    public void addProductIdAndQuantity(ProductId productId, Integer quantity) {
        productIdQuantityMap.merge(productId, quantity, Integer::sum);
    }

    @Override
    public String toString() {
        return "ArgumentParsingContext{" +
                "cardNumber=" + cardNumber +
                ", balanceDebitCard=" + balanceDebitCard +
                ", productIdQuantityEntryList=" + productIdQuantityMap +
                '}';
    }
}
