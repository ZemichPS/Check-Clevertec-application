package ru.clevertec.check.infrastructure.output.file.mapper;

import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.*;
import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureToObjectMapper;

import java.math.BigDecimal;
import java.util.List;

public class CSVStructureToDiscountCardsMapper implements CSVStructureToObjectMapper<RealDiscountCard> {
    @Override
    public List<RealDiscountCard> map(List<String[]> source) throws GenericSpecificationException {
        return source.stream().map(array -> {
            Integer id = Integer.parseInt(array[0]);
            Integer number = Integer.parseInt(array[1]);
            BigDecimal discountAmount = new BigDecimal(array[2]);
            CardNumber cardNumber = new CardNumber(number);

            RealDiscountCard discountCard = new RealDiscountCard(
                    new CardId(id),
                    discountAmount
            );
            discountCard.addCardNumber(cardNumber);
            return discountCard;
        }).toList();
    }
}
