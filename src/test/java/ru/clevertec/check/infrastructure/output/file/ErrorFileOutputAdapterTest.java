package ru.clevertec.check.infrastructure.output.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ErrorFileOutputAdapterTest {


    ErrorFileOutputAdapter errorFileOutputAdapter = new ErrorFileOutputAdapter();

    @ParameterizedTest
    @ValueSource(strings = {"Error message 1", "Error message 2", "Error message 3"})
    @DisplayName("Should write error messages in result file")
    void writeError(String errorMessage) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {
        errorFileOutputAdapter.writeError(errorMessage);
        List<String> fileContentlist = Files.readAllLines(Path.of("result.csv"));
        assertAll(
                () -> assertNotNull(fileContentlist),
                () -> assertEquals(2, fileContentlist.size()),
                () -> assertEquals("ERROR", fileContentlist.getFirst()),
                () -> assertEquals(errorMessage.toUpperCase(), fileContentlist.get(1))
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