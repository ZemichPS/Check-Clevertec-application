package ru.clevertec.check.application.ports.input;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.application.ports.intput.CheckInputPort;
import ru.clevertec.check.application.ports.output.*;
import ru.clevertec.check.domain.model.entity.*;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.exception.NotEnoughMoneyException;
import ru.clevertec.check.domain.model.exception.ProductNotFoundException;
import ru.clevertec.check.domain.model.exception.RequestedProductQuantityOutOfStockException;
import ru.clevertec.check.domain.model.valueobject.*;
import ru.clevertec.check.domain.service.DiscountService;
import ru.clevertec.check.infrastructure.output.file.CheckFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.DiscountCardFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.ErrorFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.file.ProductFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.std.StdOutputAdapter;
import ru.clevertec.check.infrastructure.utils.CSVReader;
import ru.clevertec.check.infrastructure.utils.SimpleCVSFileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class CheckInputPortTest {

    @Spy
    private CheckFileOutPutAdapter checkFileOutPutAdapter;

    @Mock
    private ProductFileOutputAdapter productFileOutputAdapter;

    @Mock
    private DiscountCardFileOutPutAdapter discountCardFileOutPutAdapter;
    @Mock
    private ErrorFileOutputAdapter errorFileOutputAdapter;
    @Mock
    private StdOutputAdapter stdOutputAdapter;
    @InjectMocks
    private CheckInputPort checkInputPort;

    @ParameterizedTest
    @DisplayName("create method without discount card number")
    @MethodSource("provideArgumentsForCreateMethod")
    void testCreate(Map<ProductId, Integer> orderMap, BigDecimal debitCardBalance) throws URISyntaxException, IOException, InvocationTargetException, IllegalAccessException {

        List<ProductPosition> mockProductPositions = provideProductPositions();
        when(productFileOutputAdapter.findAll()).thenReturn(mockProductPositions);

        Map<ProductId, Integer> requestedProductMap = new HashMap<>();
        requestedProductMap.put(new ProductId(2), 2);
        requestedProductMap.put(new ProductId(3), 1);


        assertAll("Exception details",
                () -> assertThrowsExactly(NotEnoughMoneyException.class, () -> checkInputPort.create(Map.of(new ProductId(1), 5), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(ProductNotFoundException.class, () -> checkInputPort.create(Map.of(new ProductId(8), 1), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(GenericSpecificationException.class, () -> checkInputPort.create(Map.of(), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(RequestedProductQuantityOutOfStockException.class, () -> checkInputPort.create(Map.of(new ProductId(2), 150), BigDecimal.valueOf(5_000)))
        );

        assertAll("Check details",
                () -> Assertions.assertNotNull(checkInputPort.create(orderMap, debitCardBalance), "Cannot be null"),
                () -> assertEquals(2, checkInputPort.create(requestedProductMap, BigDecimal.valueOf(100)).getItemsCount()),
                () -> assertEquals(LocalDate.now(), checkInputPort.create(requestedProductMap, BigDecimal.valueOf(100)).getCreationDate()),
                () -> assertEquals(BigDecimal.valueOf(9.39), checkInputPort.create(requestedProductMap, BigDecimal.valueOf(5_000)).computeAndGetTotalPrices().totalPrice()),
                () -> assertInstanceOf(NullDiscountCard.class, checkInputPort.create(requestedProductMap, BigDecimal.valueOf(5_000)).getDiscountCard())
        );
    }

    private static Stream<Arguments> provideArgumentsForCreateMethod() {
        return Stream.of(
                Arguments.of(Map.of(new ProductId(1), 1), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(2), 1), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(3), 1), BigDecimal.valueOf(200))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateMethodWithDebitCardNumber")
    void create(Map<ProductId, Integer> orderMap,
                CardNumber cardNumber,
                BigDecimal debitCardBalance) throws URISyntaxException, IOException {


        List<ProductPosition> mockProductPositions = provideProductPositions();
        when(productFileOutputAdapter.findAll()).thenReturn(mockProductPositions);

        assertAll("Exception details",
                () -> assertThrowsExactly(NotEnoughMoneyException.class, () -> checkInputPort.create(Map.of(new ProductId(3), 5), new CardNumber(1111), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(ProductNotFoundException.class, () -> checkInputPort.create(Map.of(new ProductId(8), 1), new CardNumber(1111), BigDecimal.valueOf(150))),
                () -> assertThrowsExactly(GenericSpecificationException.class, () -> checkInputPort.create(Map.of(), new CardNumber(2222), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(GenericSpecificationException.class, () -> checkInputPort.create(Map.of(), new CardNumber(111), BigDecimal.valueOf(5)), "invalid card number"),
                () -> assertThrowsExactly(RequestedProductQuantityOutOfStockException.class, () -> checkInputPort.create(Map.of(new ProductId(2), 150), new CardNumber(3333), BigDecimal.valueOf(5_000)))
        );

        Map<ProductId, Integer> requestedProductMap = new HashMap<>();
        requestedProductMap.put(new ProductId(2), 7);
        requestedProductMap.put(new ProductId(3), 1);
        CardNumber discountCardNumber = new CardNumber(1111);

        List<RealDiscountCard> mockDiscountCards = getDiscountCards();
        Mockito.when(discountCardFileOutPutAdapter.findAll()).thenReturn(mockDiscountCards);

        assertAll("Check details",
                () -> assertEquals(2, checkInputPort.create(requestedProductMap, discountCardNumber, BigDecimal.valueOf(100)).getItemsCount()),
                () -> assertEquals(LocalDate.now(), checkInputPort.create(requestedProductMap, discountCardNumber, BigDecimal.valueOf(100)).getCreationDate()),
                () -> assertEquals(BigDecimal.valueOf(18.84).setScale(2, RoundingMode.DOWN), checkInputPort.create(requestedProductMap, discountCardNumber, BigDecimal.valueOf(5_000)).computeAndGetTotalPrices().totalPrice()),
                () -> assertInstanceOf(RealDiscountCard.class, checkInputPort.create(requestedProductMap, discountCardNumber, BigDecimal.valueOf(5_000)).getDiscountCard()),
                () -> Assertions.assertNotNull(checkInputPort.create(orderMap, cardNumber, debitCardBalance), "Cannot be null")
        );
        Mockito.verify(discountCardFileOutPutAdapter, times(8)).findAll();
    }

    private static List<RealDiscountCard> getDiscountCards() {
        RealDiscountCard discountCard1 = new RealDiscountCard(new CardId(1), BigDecimal.ONE);
        RealDiscountCard discountCard2 = new RealDiscountCard(new CardId(2), BigDecimal.TWO);
        RealDiscountCard discountCard3 = new RealDiscountCard(new CardId(3), BigDecimal.TEN);

        discountCard1.addCardNumber(new CardNumber(1111));
        discountCard2.addCardNumber(new CardNumber(2222));
        discountCard3.addCardNumber(new CardNumber(3333));

        List<RealDiscountCard> all = List.of(
                discountCard1, discountCard2, discountCard3
        );
        return all;
    }

    private static Stream<Arguments> provideArgumentsForCreateMethodWithDebitCardNumber() {
        return Stream.of(
                Arguments.of(Map.of(new ProductId(1), 1), new CardNumber(1111), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(2), 1), new CardNumber(2222), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(3), 1), new CardNumber(3333), BigDecimal.valueOf(200))
        );
    }


    private List<ProductPosition> provideProductPositions() {
        var product1 = new Product(new ProductId(1), SaleConditionType.WHOLESALE);
        var product2 = new Product(new ProductId(2), SaleConditionType.USUAL_PRICE);
        var product3 = new Product(new ProductId(3), SaleConditionType.WHOLESALE);
        var product4 = new Product(new ProductId(4), SaleConditionType.USUAL_PRICE);
        product1.addProductPrice(new Price(BigDecimal.valueOf(2.75)));
        product2.addProductPrice(new Price(BigDecimal.valueOf(1.89)));
        product3.addProductPrice(new Price(BigDecimal.valueOf(3.12)));
        product3.addProductPrice(new Price(BigDecimal.valueOf(5.61)));
        product1.addProductName(new ProductName("Milk1 3l"));
        product2.addProductName(new ProductName("Banana 1kg"));
        product3.addProductName(new ProductName("Vodka 'Belka'"));
        product4.addProductName(new ProductName("Tasty Bear 10l"));

        return List.of(
                new ProductPosition(product1, 20),
                new ProductPosition(product2, 14),
                new ProductPosition(product3, 17)
        );
    }

    @AfterEach
    void afterAll() {
        String filePath = "result.csv";
        try (FileWriter writer = new FileWriter(filePath, false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
