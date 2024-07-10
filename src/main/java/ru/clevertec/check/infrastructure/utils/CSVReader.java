package ru.clevertec.check.infrastructure.utils;

import ru.clevertec.check.CheckRunner;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface CSVReader {

    List<String[]> readRecords(Path pathToFile) throws IOException;

    List<String[]> readAndFilterRecords(Path pathToFile, List<Predicate<String>> predicates) throws IOException;

    default List<String> readLines(Path resourceFileName) throws IOException {
        ClassLoader classLoader = CheckRunner.class.getClassLoader();

        String textResource = resourceFileName.toString();
        if (Objects.nonNull(classLoader.getResource(textResource))) {
            try (InputStream inputStream = classLoader.getResourceAsStream(textResource)) {
                if (inputStream == null) {
                    throw new IOException("Resource not found: discountCards.csv");
                }
                return new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.toList());
            }
        } else {
            try (InputStream inputStream = new FileInputStream(textResource)) {
                return new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.toList());
            }

        }
    }
}
