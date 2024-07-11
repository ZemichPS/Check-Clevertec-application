package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.application.ports.output.DiscountCardOutputPort;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.infrastructure.output.file.mapper.CSVStructureToDiscountCardsMapper;
import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureToObjectMapper;
import ru.clevertec.check.infrastructure.utils.CSVReader;
import ru.clevertec.check.infrastructure.utils.SimpleCVSFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class DiscountCardFileOutputAdapter implements DiscountCardOutputPort {

    private final Path DISCOUNT_CARDS_RESOURCE_FILE_NAME;
    private final CSVReader csvReader = new SimpleCVSFileReader();
    CSVStructureToObjectMapper<RealDiscountCard> mapper = new CSVStructureToDiscountCardsMapper();

    public DiscountCardFileOutputAdapter(Path discountCardsResourceFileName) {
        DISCOUNT_CARDS_RESOURCE_FILE_NAME = discountCardsResourceFileName;
    }


    @Override
    public List<RealDiscountCard> findAll() throws URISyntaxException, IOException {
        Predicate<String> notEmpty = line -> !line.isEmpty();
        Predicate<String> startWithDigit = line -> Character.isDigit(line.charAt(0));
        List<Predicate<String>> predicates = List.of(notEmpty, startWithDigit);
        final List<String[]> rawDiscountCardData = csvReader.readAndFilterRecords(DISCOUNT_CARDS_RESOURCE_FILE_NAME, predicates);
        return mapper.map(rawDiscountCardData);
    }
}
