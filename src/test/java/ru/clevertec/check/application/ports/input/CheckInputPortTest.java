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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.application.ports.intput.CheckInputPort;
import ru.clevertec.check.application.ports.output.*;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.entity.NullDiscountCard;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.exception.NotEnoughMoneyException;
import ru.clevertec.check.domain.model.exception.RequestedProductQuantityOutOfStockException;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.domain.service.DiscountService;
import ru.clevertec.check.infrastructure.output.file.CheckFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.DiscountCardFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.ErrorFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.file.ProductFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.std.StdOutputAdapter;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith({MockitoExtension.class})
public class CheckInputPortTest {

    @Spy
    private CheckFileOutPutAdapter checkFileOutPutAdapter;
    @Spy
    private ProductFileOutputAdapter productFileOutputAdapter;
    @Spy
    private DiscountCardFileOutPutAdapter discountCardFileOutPutAdapter;
    @Spy
    private ErrorFileOutputAdapter errorFileOutputAdapter;
    @Spy
    private StdOutputAdapter stdOutputAdapter;
    @Spy
    private DiscountService discountService = new DiscountService();

    @InjectMocks
    private CheckInputPort checkInputPort;

    @ParameterizedTest
    @DisplayName("create method without discount card number")
    @MethodSource("provideArgumentsForCreateMethod")
    void create(Map<ProductId, Integer> orderMap, BigDecimal debitCardBalance) {

        assertAll("Exception details",
                () -> assertThrowsExactly(NotEnoughMoneyException.class, () -> checkInputPort.create(Map.of(new ProductId(8), 5), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(GenericSpecificationException.class, () -> checkInputPort.create(Map.of(), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(RequestedProductQuantityOutOfStockException.class, () -> checkInputPort.create(Map.of(new ProductId(8), 150), BigDecimal.valueOf(5_000)))
        );

        Map<ProductId, Integer> requestedProductMap = new HashMap<>();
        requestedProductMap.put(new ProductId(2), 2);
        requestedProductMap.put(new ProductId(3), 1);

        assertAll("Check details",
                () -> assertEquals(2, checkInputPort.create(requestedProductMap, BigDecimal.valueOf(100)).getItemsCount()),
                () -> assertEquals(LocalDate.now(), checkInputPort.create(requestedProductMap, BigDecimal.valueOf(100)).getCreationDate()),
                () -> assertEquals(BigDecimal.valueOf(7.52), checkInputPort.create(requestedProductMap, BigDecimal.valueOf(5_000)).computeAndGetTotalPrices().totalPrice()),
                () -> assertInstanceOf(NullDiscountCard.class, checkInputPort.create(requestedProductMap, BigDecimal.valueOf(5_000)).getDiscountCard()),
                () -> Assertions.assertNotNull(checkInputPort.create(orderMap, debitCardBalance), "Cannot be null")
        );
    }

    private static Stream<Arguments> provideArgumentsForCreateMethod() {
        return Stream.of(
                Arguments.of(Map.of(new ProductId(1), 1), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(2), 1), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(3), 1), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(4), 1), BigDecimal.valueOf(200))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateMethodWithDebitCardNumber")
    void create(Map<ProductId, Integer> orderMap,
                CardNumber cardNumber,
                BigDecimal debitCardBalance) {
        assertAll("Exception details",
                () -> assertThrowsExactly(NotEnoughMoneyException.class, () -> checkInputPort.create(Map.of(new ProductId(8), 5), new CardNumber(1111), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(GenericSpecificationException.class, () -> checkInputPort.create(Map.of(), new CardNumber(2222), BigDecimal.valueOf(5))),
                () -> assertThrowsExactly(GenericSpecificationException.class, () -> checkInputPort.create(Map.of(), new CardNumber(111), BigDecimal.valueOf(5)), "invalid card number"),
                () -> assertThrowsExactly(RequestedProductQuantityOutOfStockException.class, () -> checkInputPort.create(Map.of(new ProductId(8), 150), new CardNumber(3333), BigDecimal.valueOf(5_000)))
        );

        Map<ProductId, Integer> requestedProductMap = new HashMap<>();
        requestedProductMap.put(new ProductId(2), 20);
        requestedProductMap.put(new ProductId(3), 1);
        CardNumber discountCardNumber = new CardNumber(1111);

        assertAll("Check details",
                () -> assertEquals(2, checkInputPort.create(requestedProductMap, discountCardNumber, BigDecimal.valueOf(100)).getItemsCount()),
                () -> assertEquals(LocalDate.now(), checkInputPort.create(requestedProductMap, discountCardNumber, BigDecimal.valueOf(100)).getCreationDate()),
                () -> assertEquals(BigDecimal.valueOf(56.30).setScale(2, RoundingMode.DOWN), checkInputPort.create(requestedProductMap, discountCardNumber, BigDecimal.valueOf(5_000)).computeAndGetTotalPrices().totalPrice()),
                () -> assertInstanceOf(RealDiscountCard.class, checkInputPort.create(requestedProductMap, discountCardNumber, BigDecimal.valueOf(5_000)).getDiscountCard()),
                () -> Assertions.assertNotNull(checkInputPort.create(orderMap, cardNumber, debitCardBalance), "Cannot be null")
        );
    }

    private static Stream<Arguments> provideArgumentsForCreateMethodWithDebitCardNumber() {
        return Stream.of(
                Arguments.of(Map.of(new ProductId(1), 1), new CardNumber(1111), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(2), 1), new CardNumber(2222), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(3), 1), new CardNumber(3333), BigDecimal.valueOf(200)),
                Arguments.of(Map.of(new ProductId(4), 1), new CardNumber(4444), BigDecimal.valueOf(200))
        );
    }

    @AfterEach
    void afterAll() {
        String filePath = "result.csv"; // Замените на имя вашего файла
        try (FileWriter writer = new FileWriter(filePath, false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
