package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.application.ports.output.ProductOutputPort;
import ru.clevertec.check.domain.model.exception.BadFilePathException;
import ru.clevertec.check.domain.model.valueobject.ProductPosition;
import ru.clevertec.check.infrastructure.output.file.mapper.CSVStructureToProductPositionsMapper;
import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureToObjectMapper;
import ru.clevertec.check.infrastructure.utils.CSVReader;
import ru.clevertec.check.infrastructure.utils.SimpleCVSFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public class ProductFileOutputAdapter implements ProductOutputPort {
    private final Path PRODUCT_LIST_PATH;
    private final CSVReader csvReader = new SimpleCVSFileReader();
    CSVStructureToObjectMapper<ProductPosition> mapper = new CSVStructureToProductPositionsMapper();

    public ProductFileOutputAdapter(Path productListPath) {
        PRODUCT_LIST_PATH = productListPath;
    }

    @Override
    public List<ProductPosition> findAll() throws IOException, BadFilePathException {
        if (Files.exists(PRODUCT_LIST_PATH)
                && !Files.isRegularFile(PRODUCT_LIST_PATH)
                && PRODUCT_LIST_PATH.toString().isEmpty()
        ) throw new BadFilePathException("An invalid path to the product file was passed");

        //TODO рефактор
        if (!Files.exists(PRODUCT_LIST_PATH)
                && !Files.isRegularFile(PRODUCT_LIST_PATH)
        ) throw new BadFilePathException("An invalid path to the product file was passed");

        Predicate<String> notEmpty = line -> !line.isEmpty();
        Predicate<String> startWithDigit = line -> Character.isDigit(line.charAt(0));
        List<Predicate<String>> predicates = List.of(notEmpty, startWithDigit);

        List<String[]> rawProductData = csvReader.readAndFilterRecords(PRODUCT_LIST_PATH, predicates);
        return mapper.map(rawProductData);
    }


}
