package ru.clevertec.check.infrastructure.utils;

import ru.clevertec.check.domain.model.dto.TotalPricesDto;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.CheckItem;
import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckToCSVFileWriter implements CSVWriter<Check> {

    private final CSVStructureMapper csvStructureMapper;

    public CheckToCSVFileWriter(CSVStructureMapper csvStructureMapper) {
        this.csvStructureMapper = csvStructureMapper;
    }

    @Override
    public void write(Check source, Path toFilePath) throws InvocationTargetException, IllegalAccessException, IOException {
        String destination = this.getStructure(source);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFilePath.toFile(), true))) {
            writer.write(destination);
            writer.flush();
        }

    }

    private String getStructure(Check source) throws InvocationTargetException, IllegalAccessException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormater = DateTimeFormatter.ofPattern("HH:mm:ss");

        String date = source.getCreationDate().format(dateFormatter);
        String time = source.getCreationTime().format(timeFormater);

        List<CheckItem> items = source.getItems();
        StringBuilder result = new StringBuilder();
        TotalPricesDto totalPrices = source.computeAndGetTotalPrices();

        result.append(csvStructureMapper.getLineFromValues("Date", "Time"))
                .append(csvStructureMapper.getLineFromValues(date, time))
                .append(csvStructureMapper.addDownSpaceLine())
                .append(csvStructureMapper.getLineFromClassFieldsName(CSVStructureMapper.RegisterType.UPPER_CASE, CheckItem.class))
                .append(csvStructureMapper.getLinesFromObjectValuesWithAppendixes(items, "", "", "$", "$", "$"))
                .append(csvStructureMapper.addDownSpaceLine())
                .append(csvStructureMapper.addDownSpaceLine());

        DiscountCard discountCard = source.getDiscountCard();
        if (discountCard.isValid()) {
            CardNumber cardNumber = discountCard.getCardNumber();
            String discountAmount = discountCard.getDiscountAmount().toString();
            result.append(csvStructureMapper.getLineFromValues("DISCOUNT CARD", "DISCOUNT PERCENTAGE"))
                    .append(csvStructureMapper.getLineFromValues(cardNumber.number().toString(), discountAmount))
                    .append(csvStructureMapper.addDownSpaceLine());
        }


        result.append(csvStructureMapper.getLineFromClassFieldsName(CSVStructureMapper.RegisterType.UPPER_CASE, TotalPricesDto.class))
                .append(csvStructureMapper.getLineFromObjectValues(totalPrices, "$", "$", "$"));

        return result.toString();
    }

}
