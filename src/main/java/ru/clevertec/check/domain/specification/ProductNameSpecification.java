package ru.clevertec.check.domain.specification;

import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.ProductName;
import ru.clevertec.check.domain.specification.shared.AbstractSpecification;

public class ProductNameSpecification extends AbstractSpecification<ProductName> {

    private final Integer MIN_NUMBER_CHARACTERS = 3;

    @Override
    public void check(ProductName item) {
        if(!isSatisfiedBy(item))
            throw new GenericSpecificationException("The product description must be at least %s characters long".formatted(MIN_NUMBER_CHARACTERS));
    }

    @Override
    public boolean isSatisfiedBy(ProductName item) {
        return item.description().length() >= MIN_NUMBER_CHARACTERS;
    }
}
