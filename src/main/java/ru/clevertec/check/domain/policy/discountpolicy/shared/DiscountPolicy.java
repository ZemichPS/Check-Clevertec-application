package ru.clevertec.check.domain.policy.discountpolicy.shared;

import java.math.BigDecimal;

public interface DiscountPolicy<T> {
    boolean isApplicable(T applicable);

    BigDecimal apply(T applicable);
}
