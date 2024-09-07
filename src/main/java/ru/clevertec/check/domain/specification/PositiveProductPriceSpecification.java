package ru.clevertec.check.domain.specification;

import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.Price;
import ru.clevertec.check.domain.specification.shared.AbstractSpecification;

public class PositiveProductPriceSpecification extends AbstractSpecification<Price> {


    @Override
    public void check(Price item) {
        if(!isSatisfiedBy(item))
            throw new GenericSpecificationException("The price of the product should be positive. Current price: %s".formatted(item.value()));
    }

    @Override
    public boolean isSatisfiedBy(Price item) {
        return item.value().doubleValue() > 0;
    }
}
