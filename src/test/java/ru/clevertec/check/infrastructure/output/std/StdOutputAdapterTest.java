package ru.clevertec.check.infrastructure.output.std;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.valueobject.CheckItem;
import testconfig.InjectCheck;
import testconfig.InjectCheckProcessor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InjectCheckProcessor.class)
class StdOutputAdapterTest {

    @InjectCheck
    Check check;

    StdOutputAdapter stdOutputAdapter = new StdOutputAdapter();

    @Test
    void printCheck() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        stdOutputAdapter.printCheck(check);
        List<String> outPutList = outputStream.toString().lines().toList();
        String[] split = outPutList.get(3).split("\\|");
        System.out.println(Arrays.toString(split));
        CheckItem checkItem = new CheckItem(
                Integer.parseInt(split[1].replace("quantity:", "").trim()),
                split[0].trim(),
                new BigDecimal(split[2].substring(7, split[2].length() - 1).trim()),
                new BigDecimal(split[3].replace("discount:", "").replace("$", "").trim()),
                new BigDecimal(split[4].replace("total:", "").replace("$", "").trim())
        );
        System.setOut(originalOut);
        System.out.println(outputStream.toString());
        Assertions.assertAll(
                () -> assertFalse(outputStream.toString().trim().isEmpty()),
                () -> assertEquals(checkItem, check.getItems().getFirst())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Fatal error", "Exception message", "Other message"})
    void printError(String error) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        stdOutputAdapter.printError(error);

        Assertions.assertAll(
                () -> assertFalse(outputStream.toString().trim().isEmpty()),
                () -> assertEquals(error.toUpperCase(), outputStream.toString().trim())
        );

        System.setOut(originalOut);
    }
}