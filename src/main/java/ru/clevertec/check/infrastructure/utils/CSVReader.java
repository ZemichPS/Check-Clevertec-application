package ru.clevertec.check.infrastructure.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public interface CSVReader {

    List<String[]> readRecords(String resourceFileName) throws IOException;

    List<String[]> readAndFilterRecords(String resourceFileName, List<Predicate<String>> predicates) throws IOException;
}
