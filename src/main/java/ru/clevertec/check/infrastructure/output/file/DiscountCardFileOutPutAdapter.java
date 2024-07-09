package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.CheckRunner;
import ru.clevertec.check.application.ports.output.DiscountCardOutputPort;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.infrastructure.output.file.mapper.CSVStructureToDiscountCardsMapper;
import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureToObjectMapper;
import ru.clevertec.check.infrastructure.utils.CSVReader;
import ru.clevertec.check.infrastructure.utils.SimpleCVSFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;

public class DiscountCardFileOutPutAdapter implements DiscountCardOutputPort {

    private final String DISCOUNT_CARDS_RESOURCE_FILE_NAME = "discountCards.csv";
    private final CSVReader csvReader = new SimpleCVSFileReader();
    CSVStructureToObjectMapper<RealDiscountCard> mapper = new CSVStructureToDiscountCardsMapper();

    @Override
    public List<RealDiscountCard> findAll() throws URISyntaxException, IOException {
        Predicate<String> notEmpty = line -> !line.isEmpty();
        Predicate<String> startWithDigit = line -> Character.isDigit(line.charAt(0));
        List<Predicate<String>> predicates = List.of(notEmpty, startWithDigit);
        final List<String[]> rawDiscountCardData = csvReader.readAndFilterRecords(DISCOUNT_CARDS_RESOURCE_FILE_NAME, predicates);
        return mapper.map(rawDiscountCardData);
    }
}
