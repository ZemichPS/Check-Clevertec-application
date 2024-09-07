package ru.clevertec.check.domain.service;

import ru.clevertec.check.domain.model.valueobject.ProductPosition;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.util.List;
import java.util.Optional;

public class ProductPositionService {

    public static Optional<ProductPosition> findProductPositionByProductId(List<ProductPosition> positions, ProductId productId) {
        return positions.stream()
                .filter(position -> position.product().getId().equals(productId))
                .findFirst();
    }
}
