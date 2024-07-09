package ru.clevertec.check.domain.specification;

import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductName;
import ru.clevertec.check.domain.specification.shared.AbstractSpecification;

public class DiscountCardNumberSpecification extends AbstractSpecification<CardNumber> {

    private final int VALID_NUMBER_OF_DIGITS = 4;
    int currentCardNumberLength;

    @Override
    public void check(CardNumber item) {
        if (!isSatisfiedBy(item)) {
            int currentCardNumberLength = item.number().toString().length();
            throw new GenericSpecificationException("Length discount card number must be %s, but current card number %s ".formatted(VALID_NUMBER_OF_DIGITS, currentCardNumberLength));
        }
    }

    @Override
    public boolean isSatisfiedBy(CardNumber item) {
        this.currentCardNumberLength = item.number().toString().length();
        return this.currentCardNumberLength == VALID_NUMBER_OF_DIGITS;
    }
}
