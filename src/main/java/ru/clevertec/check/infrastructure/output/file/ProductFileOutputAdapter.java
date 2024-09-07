package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.application.ports.output.ProductOutputPort;
import ru.clevertec.check.domain.model.valueobject.ProductPosition;
import ru.clevertec.check.infrastructure.output.file.mapper.CSVStructureToProductPositionsMapper;
import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureToObjectMapper;
import ru.clevertec.check.infrastructure.utils.CSVReader;
import ru.clevertec.check.infrastructure.utils.SimpleCVSFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public class ProductFileOutputAdapter implements ProductOutputPort {
    private final String PRODUCT_LIST_RESOURCE_FILE_NAME = "products.csv";

    //    private final CSVReader csvReader = new SimpleCVSFileReader();
    private final CSVReader csvReader;
    private final CSVStructureToObjectMapper<ProductPosition> mapper;

    public ProductFileOutputAdapter(CSVReader csvReader, CSVStructureToObjectMapper<ProductPosition> mapper) {
        this.csvReader = csvReader;
        this.mapper = mapper;
    }
    //   private final CSVStructureToObjectMapper<ProductPosition> mapper = new CSVStructureToProductPositionsMapper();

    @Override
    public List<ProductPosition> findAll() throws URISyntaxException, IOException {
        Predicate<String> notEmpty = line -> !line.isEmpty();
        Predicate<String> startWithDigit = line -> Character.isDigit(line.charAt(0));

        List<Predicate<String>> predicates = List.of(notEmpty, startWithDigit);
        List<String[]> rawProductData = csvReader.readAndFilterRecords(PRODUCT_LIST_RESOURCE_FILE_NAME, predicates);

        return mapper.map(rawProductData);
    }

}
