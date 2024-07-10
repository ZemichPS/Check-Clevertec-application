package ru.clevertec.check.infrastructure.utils;

import ru.clevertec.check.CheckRunner;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleCVSFileReader implements CSVReader {


    @Override
    public List<String[]> readRecords(Path pathToFile) throws IOException {
        return readLines(pathToFile).stream()
                .map(line -> Arrays.stream(line.split(";")).map(String::trim).toArray(String[]::new))
                .toList();
    }

    @Override
    public List<String[]> readAndFilterRecords(Path pathToFile, List<Predicate<String>> predicates) throws IOException {
        Predicate<String> combinedPredicate = predicates.stream()
                .reduce(x -> true, Predicate::and);

        return readLines(pathToFile).stream()
                .filter(combinedPredicate)
                .map(line -> Arrays.stream(line.split(";")).map(String::trim).toArray(String[]::new))
                .toList();
    }


}
