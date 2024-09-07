package ru.clevertec.check.domain.specification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NotEmptyOrderMapSpecificationTest {

    private NotEmptyOrderMapSpecification specification = new NotEmptyOrderMapSpecification();

    @Test
    void testIsSatisfiedBy_WithNonEmptyOrderMap_ShouldReturnTrue() {
        Map<ProductId, Integer> orderMap = new HashMap<>();
        orderMap.put(new ProductId(1), 2);
        boolean result = specification.isSatisfiedBy(orderMap);
        assertTrue(result, "The method should return true for a non-empty orderMap");
    }

    @Test
    void testIsSatisfiedBy_WithEmptyOrderMap_ShouldReturnFalse() {
        Map<ProductId, Integer> orderMap = new HashMap<>();
        boolean result = specification.isSatisfiedBy(orderMap);
        assertFalse(result, "The method should return true for a non-empty orderMap");
    }

    @Test
    void testCheck_WithEmptyOrderMap_ShouldThrowException() {
        Map<ProductId, Integer> emptyOrderMap = new HashMap<>();
        assertThrows(GenericSpecificationException.class, () -> {
            specification.check(emptyOrderMap);
        }, "A GenericSpecificationException is expected to be thrown when the orderMap is empty");
    }

    @Test
    void testCheck_WithNonEmptyOrderMap_ShouldNotThrowException() {
        Map<ProductId, Integer> nonEmptyOrderMap = new HashMap<>();
        nonEmptyOrderMap.put(new ProductId(1), 2);
        specification.check(nonEmptyOrderMap);
    }
}