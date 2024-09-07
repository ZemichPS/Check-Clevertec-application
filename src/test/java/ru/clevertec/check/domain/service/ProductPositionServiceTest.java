package ru.clevertec.check.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.entity.Product;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.domain.model.valueobject.ProductPosition;
import ru.clevertec.check.domain.model.valueobject.SaleConditionType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductPositionServiceTest {

    @Test
    @DisplayName("Should find and return ProductPosition by ProductId if present in the list")
    void findProductPositionByProductId_whenProductIdIsPresent_ShouldReturnProductPosition() {
        ProductId productId = new ProductId(1);
        Product product = new Product(productId, SaleConditionType.WHOLESALE);
        ProductPosition position = new ProductPosition(product, 10);
        List<ProductPosition> positions = List.of(position);
        Optional<ProductPosition> result = ProductPositionService.findProductPositionByProductId(positions, productId);

        Assertions.assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(position, result.get())
        );
    }

    @Test
    @DisplayName("Should return empty Optional when ProductId is not present in the list")
    void findProductPositionByProductId_whenProductIdIsNotPresent_ShouldReturnEmptyOptional() {
        ProductId searchId = new ProductId(2);
        ProductId productId = new ProductId(1);
        Product product = new Product(productId, SaleConditionType.WHOLESALE);
        ProductPosition position = new ProductPosition(product, 10);
        List<ProductPosition> positions = List.of(position);

        Optional<ProductPosition> result = ProductPositionService.findProductPositionByProductId(positions, searchId);
        assertTrue(result.isEmpty());
    }
}
