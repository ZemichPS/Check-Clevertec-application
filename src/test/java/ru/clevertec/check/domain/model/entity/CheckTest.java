package ru.clevertec.check.domain.model.entity;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
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
import testconfig.InjectCheck;
import testconfig.InjectCheckProcessor;

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

@ExtendWith(InjectCheckProcessor.class)
class CheckTest {

    @InjectCheck
    private Check check;


    @Test
    void addDiscountCart() {
        RealDiscountCard discountCard = new RealDiscountCard(new CardId(1), BigDecimal.TEN);
        check.addDiscountCart(discountCard);
        assertEquals(new CardId(1), check.getDiscountCard().getId());
    }

    @Test
    void addCheckItem() {
        CheckItem checkItem = new CheckItem(2, "some product", BigDecimal.valueOf(7.41), BigDecimal.ONE,  BigDecimal.valueOf(13.82));
        check.addCheckItem(checkItem);
        assertEquals(3, check.getItemsCount());
    }

    @Test
    void getItems() {
        assertNotNull(check.getItems());
    }

    @Test
    void getItemsCount() {
        assertEquals(2, check.getItemsCount());
    }

    @Test
    void getCheckID() {
        assertNotNull(check.getCheckID());
    }

    @Test
    void getCreationDate() {
        assertNotNull(check.getCreationDate());
    }

    @Test
    void getCreationTime() {
        assertNotNull(check.getCreationTime());
    }

    @Test
    void getDiscountCard() {
        assertNotNull(check.getDiscountCard());
    }

    @Test
    void computeAndGetTotalPrices() {
        Assertions.assertAll(
                ()-> assertNotNull(check.computeAndGetTotalPrices()),
                ()-> assertEquals(BigDecimal.valueOf(14.9), check.computeAndGetTotalPrices().totalPrice()),
                ()-> assertEquals(BigDecimal.valueOf(2.9), check.computeAndGetTotalPrices().totalDiscount()),
                ()-> assertEquals(BigDecimal.valueOf(12.0), check.computeAndGetTotalPrices().totalWithDiscount())
        );

    }
}