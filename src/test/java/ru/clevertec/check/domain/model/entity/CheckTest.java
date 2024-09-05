package ru.clevertec.check.domain.model.entity;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.application.ports.intput.CheckInputPort;
import ru.clevertec.check.application.ports.output.*;
import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.domain.model.valueobject.*;
import ru.clevertec.check.infrastructure.output.file.CheckFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.DiscountCardFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.ErrorFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.file.ProductFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.file.mapper.SimpleCSVStructureMapper;
import ru.clevertec.check.infrastructure.output.std.StdOutputAdapter;
import ru.clevertec.check.infrastructure.utils.ErrorToCSVFileWriter;
import ru.clevertec.check.infrastructure.utils.SimpleCVSFileReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckTest {
    private static CheckUseCase createCheckUseCase;

    private Check check = null;

    @BeforeAll
    static void setupBeforeAll() {
        CheckOutputPort checkOutputPort = new CheckFileOutPutAdapter();
        DiscountCardOutputPort discountCardOutputPort = new DiscountCardFileOutPutAdapter(new SimpleCVSFileReader());
        ErrorOutputPort errorOutputPort = new ErrorFileOutputAdapter();
        ProductOutputPort productOutputPort = new ProductFileOutputAdapter();
        StdOutputPort stdOutputPort = new StdOutputAdapter();

        createCheckUseCase = new CheckInputPort(checkOutputPort,
                productOutputPort,
                discountCardOutputPort,
                errorOutputPort,
                stdOutputPort
        );
    }

    @BeforeEach
    void setupBeforeEach() {
        check = new Check(
                new CheckId(UUID.randomUUID()),
                LocalDate.now(),
                LocalTime.now()
        );

    }

    @ParameterizedTest
    @MethodSource({"findDiscountCard"})
    @DisplayName("Should  add DiscountCard object")
    void addDiscountCart(DiscountCard discountCard) {
        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> check.addDiscountCart(discountCard)),
                () -> assertNotNull(discountCard)
        );
    }

    @Test
    void addCheckItem() {
        CheckItem checkItem = new CheckItem(
                5,
                "Milk 1l.",
                BigDecimal.valueOf(1.1),
                BigDecimal.valueOf(0.2),
                BigDecimal.valueOf(5.3)
        );

        check.addCheckItem(checkItem);
        Assertions.assertAll(
                () -> assertFalse(check.getItems().isEmpty()),
                () -> assertEquals(5, check.getItems().getFirst().qty())
        );
    }

    @Test
    void getItems() {
        Assertions.assertAll(
                () -> assertTrue(check.getItems().isEmpty()),
                () -> assertInstanceOf(List.class, check.getItems())
        );
    }

    @Test
    void getItemsCount() {
        CheckItem checkItem = new CheckItem(
                5,
                "Milk 1l.",
                BigDecimal.valueOf(1.1),
                BigDecimal.valueOf(0.2),
                BigDecimal.valueOf(5.3)
        );
        check.addCheckItem(checkItem);
        assertEquals(1, check.getItemsCount());
    }

    @ParameterizedTest
    @MethodSource({"getCheck"})
    void getCheckID(Check check) {
        Assertions.assertAll(
                () -> assertNotNull(check.getCheckID()),
                () -> assertInstanceOf(CheckId.class, check.getCheckID())
        );

    }

    @Test
    void getCreationDate() {
        Assertions.assertAll(
                () -> assertNotNull(check.getCreationDate()),
                () -> assertEquals(LocalDate.now(), check.getCreationDate())
        );
    }

    @Test
    void getCreationTime() {
        Assertions.assertAll(
                () -> assertNotNull(check.getCreationTime()),
                () -> assertEquals(LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                        check.getCreationTime().truncatedTo(ChronoUnit.SECONDS)
                )
        );
    }

    @Test
    void getDiscountCard() {
        DiscountCard discountCard = new RealDiscountCard(new CardId(1), BigDecimal.valueOf(5));
        check.addDiscountCart(discountCard);

        Assertions.assertAll(
                () -> assertNotNull(check.getDiscountCard()),
                () -> assertInstanceOf(DiscountCard.class, check.getDiscountCard()),
                () -> assertEquals(discountCard, check.getDiscountCard()),
                () -> assertDoesNotThrow(()-> check.getDiscountCard())
        );
    }

    @ParameterizedTest
    @MethodSource({"getCheck"})
    void computeAndGetTotalPrices(Check check) {
        Assertions.assertAll(
                () -> assertEquals(BigDecimal.valueOf(22.09), check.computeAndGetTotalPrices().totalPrice()),
                () -> assertEquals(BigDecimal.valueOf(1.60).setScale(2, RoundingMode.DOWN), check.computeAndGetTotalPrices().totalDiscount()),
                () -> assertEquals(BigDecimal.valueOf(20.49), check.computeAndGetTotalPrices().totalWithDiscount())
        );

    }

    static Stream<Arguments> getCheck() {
        Map<ProductId, Integer> orderList = Map.of(
                new ProductId(1), 2,
                new ProductId(2), 5,
                new ProductId(6), 4
        );

        CardNumber cardNumber = new CardNumber(1111);
        BigDecimal debitCardBalance = BigDecimal.valueOf(500);
        Check check = createCheckUseCase.create(orderList, cardNumber, debitCardBalance);
        return Stream.of(Arguments.of(check));
    }

    static Stream<Arguments> findDiscountCard() throws URISyntaxException, IOException {
        DiscountCardFileOutPutAdapter adapter = new DiscountCardFileOutPutAdapter(new SimpleCVSFileReader());
        return adapter.findAll().stream().map(Arguments::of);
    }


}