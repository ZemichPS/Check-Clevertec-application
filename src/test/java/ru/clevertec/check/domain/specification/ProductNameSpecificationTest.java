package ru.clevertec.check.domain.specification;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.ProductName;

import static org.junit.jupiter.api.Assertions.*;

class ProductNameSpecificationTest {

    private final ProductNameSpecification specification = new ProductNameSpecification();

    @Test
    void testIsSatisfiedBy_whenNameLengthIsEqualToMin_ShouldReturnTrue() {
        ProductName validName = new ProductName("egg");
        assertTrue(specification.isSatisfiedBy(validName));
    }

    @Test
    void testIsSatisfiedBy_whenNameLengthIsGreaterThanMin_ShouldReturnTrue() {
        ProductName validName = new ProductName("Milk 1l.");
        assertTrue(specification.isSatisfiedBy(validName));
    }

    @Test
    void testIsSatisfiedBy_whenNameLengthIsLessThanMin_ShouldReturnFalse() {
        ProductName invalidName = new ProductName("Mi");
        assertFalse(specification.isSatisfiedBy(invalidName));
    }

    @Test
    void testCheck_whenNameLengthIsEqualToMin_ShouldNotThrowException() {
        ProductName validName = new ProductName("egg");
        assertDoesNotThrow(() -> specification.check(validName));
    }

    @Test
    void testCheck_whenNameLengthIsGreaterThanMin_ShouldNotThrowException() {
        ProductName validName = new ProductName("Vino 1l.");
        assertDoesNotThrow(() -> specification.check(validName));
    }

    @Test
    void testCheck_whenNameLengthIsLessThanMin_ShouldThrowGenericSpecificationException() {
        ProductName invalidName = new ProductName("Vi");
        GenericSpecificationException exception = assertThrows(GenericSpecificationException.class,
                () -> specification.check(invalidName));
        assertEquals("The product description must be at least 3 characters long", exception.getMessage());
    }
}
