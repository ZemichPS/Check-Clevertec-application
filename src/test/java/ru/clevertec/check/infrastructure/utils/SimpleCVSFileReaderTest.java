package ru.clevertec.check.infrastructure.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleCVSFileReaderTest {

    private final SimpleCVSFileReader simpleCVSFileReader = new SimpleCVSFileReader();

    @Test
    @DisplayName("Test readRecords method with valid resource")
    void testReadRecords() throws IOException {
        String filename = "products.csv";
        List<String[]> result = simpleCVSFileReader.readRecords(filename);
        Assertions.assertAll(
                () -> assertEquals(21, result.size()),
                () -> assertEquals("id", result.getFirst()[0])
        );

    }

    @Test
    @DisplayName("Test readAndFilterRecords method filters correctly")
    void testReadAndFilterRecords() throws IOException {
        String filename = "products.csv";
        Predicate<String> notEmpty = line -> !line.isEmpty();
        Predicate<String> startsWithLetter = line -> Character.isLetter(line.charAt(0));
        List<String[]> result = simpleCVSFileReader.readAndFilterRecords(filename, List.of(notEmpty, startsWithLetter));
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Test readRecords throws IOException for missing file")
    void testReadRecordsThrowsIOException() {
        assertThrows(IOException.class, () -> simpleCVSFileReader.readRecords("missing.csv"));
    }

    @Test
    @DisplayName("Test readAndFilterRecords throws IOException for missing file")
    void testReadAndFilterRecordsThrowsIOException() {
        assertThrows(IOException.class, () -> simpleCVSFileReader.readAndFilterRecords("missing.csv", List.of()));
    }
}
