package ru.clevertec.check.domain.specification;

import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.domain.model.valueobject.ProductName;
import ru.clevertec.check.domain.specification.shared.AbstractSpecification;

import java.util.Map;

public class NotEmptyOrderMapSpecification extends AbstractSpecification<Map<ProductId, Integer>> {


    @Override
    public void check(Map<ProductId, Integer> orderMap) {
        if(!isSatisfiedBy(orderMap))
            throw new GenericSpecificationException("An empty order list is provided");
    }

    @Override
    public boolean isSatisfiedBy(Map<ProductId, Integer> orderMap) {
        return !orderMap.isEmpty();
    }
}
