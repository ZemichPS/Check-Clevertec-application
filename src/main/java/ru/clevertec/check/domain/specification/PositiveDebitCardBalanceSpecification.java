package ru.clevertec.check.domain.specification;

import ru.clevertec.check.domain.model.exception.NotEnoughMoneyException;
import ru.clevertec.check.domain.specification.shared.AbstractSpecification;

import java.math.BigDecimal;

public class PositiveDebitCardBalanceSpecification extends AbstractSpecification<BigDecimal> {
    @Override
    public void check(BigDecimal item) {
       if(!isSatisfiedBy(item)) throw new NotEnoughMoneyException("The debit card balance must be passed in arguments and be positive");
    }

    @Override
    public boolean isSatisfiedBy(BigDecimal item) {
        return item.doubleValue() > 0;
    }
}
