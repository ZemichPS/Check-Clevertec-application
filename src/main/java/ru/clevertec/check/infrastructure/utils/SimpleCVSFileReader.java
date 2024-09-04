package ru.clevertec.check.infrastructure.utils;

import ru.clevertec.check.CheckRunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleCVSFileReader implements CSVReader {


    @Override
    public List<String[]> readRecords(String resourceFileName) throws IOException {
        return readLines(resourceFileName).stream()
                .map(line -> Arrays.stream(line.split(";")).map(String::trim).toArray(String[]::new))
                .toList();
    }

    @Override
    public List<String[]> readAndFilterRecords(String resourceFileName, List<Predicate<String>> predicates) throws IOException {
        Predicate<String> combinedPredicate = predicates.stream()
                .reduce(x -> true, Predicate::and);

        return readLines(resourceFileName).stream()
                .filter(combinedPredicate)
                .map(line -> Arrays.stream(line.split(";")).map(String::trim).toArray(String[]::new))
                .toList();
    }

    private List<String> readLines(String resourceFileName) throws IOException {

        try (InputStream inputStream = CheckRunner.class.getClassLoader().getResourceAsStream(resourceFileName)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: %s".formatted(resourceFileName));
            }
            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.toList());
        }
    }
}
