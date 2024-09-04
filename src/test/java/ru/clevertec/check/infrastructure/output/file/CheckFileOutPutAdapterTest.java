package ru.clevertec.check.infrastructure.output.file;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.valueobject.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckFileOutPutAdapterTest {
    private static Path pathToResultFile;

    @Spy
    private CheckFileOutPutAdapter checkFileOutPutAdapter;

    @BeforeAll
    static void setup() {
        pathToResultFile = Paths.get("result.csv");
    }

    @Test
    @DisplayName("Should print check object to file result.csv")
    void testPersist() throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {

        Check check = new Check(
                new CheckId(UUID.randomUUID()),
                LocalDate.now(),
                LocalTime.now()
        );
        DiscountCard discountCard = new RealDiscountCard(new CardId(1));
        discountCard.addDiscountAmount(BigDecimal.valueOf(5));
        discountCard.addCardNumber(new CardNumber(1111));

        check.addCheckItem(new CheckItem(7, "Coca cola", BigDecimal.valueOf(1.1), BigDecimal.valueOf(0.9), BigDecimal.valueOf(6.9)));
        check.addCheckItem(new CheckItem(8, "Free fish", BigDecimal.valueOf(4), BigDecimal.valueOf(2), BigDecimal.valueOf(8)));
        check.addDiscountCart(discountCard);
        checkFileOutPutAdapter.persist(check);

        Assertions.assertAll("print details",
                () -> Assertions.assertNotNull(checkFileOutPutAdapter.persist(check)),
                () -> Assertions.assertTrue(Files.exists(pathToResultFile) && Files.size(pathToResultFile) > 0)
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