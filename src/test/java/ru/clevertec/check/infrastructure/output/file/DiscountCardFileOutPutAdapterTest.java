package ru.clevertec.check.infrastructure.output.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.infrastructure.utils.SimpleCVSFileReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiscountCardFileOutPutAdapterTest {

    @Mock
    private SimpleCVSFileReader csvReader;

    @InjectMocks
    private DiscountCardFileOutPutAdapter discountCardFileOutPutAdapter; // Внедряем мок в тестируемый класс

    @Test
    @DisplayName("Should return a list of discount cards")
    void testFindAll() throws URISyntaxException, IOException {
        Predicate<String> notEmpty = line -> !line.isEmpty();
        Predicate<String> startWithDigit = line -> Character.isDigit(line.charAt(0));
        List<Predicate<String>> predicates = List.of(notEmpty, startWithDigit);

        List<String[]> mockedData = List.of(
                new String[]{"1", "7777", "10"},
                new String[]{"2", "1111", "1"},
                new String[]{"3", "9999", "90"}
        );

        when(csvReader.readAndFilterRecords(eq("discountCards.csv"), anyList())).thenReturn(mockedData);
        List<RealDiscountCard> discountCards = discountCardFileOutPutAdapter.findAll();

        Assertions.assertAll(
                () -> assertNotNull(discountCards),
                () -> assertFalse(discountCards.isEmpty()),
                () -> assertEquals(7777, discountCards.getFirst().getCardNumber().number()),
                () -> assertEquals(BigDecimal.valueOf(10), discountCards.getFirst().getDiscountAmount()),
                () -> assertEquals(3, discountCards.size())
        );
    }
}
