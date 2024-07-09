package ru.clevertec.check.domain.specification;

import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.Price;
import ru.clevertec.check.domain.specification.shared.AbstractSpecification;

import java.math.BigDecimal;

public class PositiveCheckItemPriceSpecification extends AbstractSpecification<BigDecimal> {


    @Override
    public void check(BigDecimal itemPrice) {
        if(!isSatisfiedBy(itemPrice))
            throw new GenericSpecificationException("The price must be greater than 0. Current item price: %s".formatted(itemPrice));
    }

    @Override
    public boolean isSatisfiedBy(BigDecimal itemPrice) {
        return itemPrice.doubleValue() > 0;
    }
}
