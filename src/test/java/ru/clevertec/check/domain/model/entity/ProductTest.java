package ru.clevertec.check.domain.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product = null;

    @BeforeEach
    void setup() {
        SaleConditionType conditionType = SaleConditionType.WHOLESALE;
        ProductId id = new ProductId(1);
        product = new Product(id, conditionType);
    }

    @Test
    @DisplayName("Should add valid product name and reject invalid one")
    void addProductName() {
        ProductName validProductName = new ProductName("Milk 1l.");
        ProductName invalidProductName = new ProductName("1l");

        product.addProductName(validProductName);
        Assertions.assertAll(
                () -> assertNotNull(product.getName()),
                () -> assertDoesNotThrow(() -> product.addProductName(validProductName)),
                () -> assertThrows(GenericSpecificationException.class, () -> product.addProductName(invalidProductName)),
                () -> assertEquals(validProductName, product.getName())
        );
    }


    @Test
    @DisplayName("Should add valid product price and reject invalid one")
    void addProductPrice() {
        Price validPrice = new Price(BigDecimal.TWO);
        Price invalidPrice = new Price(BigDecimal.valueOf(-10));
        product.addProductPrice(validPrice);

        Assertions.assertAll(
                () -> assertNotNull(product.getPrice()),
                () -> assertDoesNotThrow(() -> product.addProductPrice(validPrice)),
                () -> assertEquals(BigDecimal.TWO, product.getPrice().value()),
                () -> assertThrows(GenericSpecificationException.class, () -> product.addProductPrice(invalidPrice))
        );
    }

    @Test
    @DisplayName("Should return the correct product ID")
    void getId() {
        Assertions.assertAll(
                () -> assertNotNull(product.getId()),
                () -> assertEquals(new ProductId(1), product.getId())
        );
    }

    @Test
    @DisplayName("Should return the correct product name")
    void getName() {
        ProductName validProductName = new ProductName("Milk 1l.");
        product.addProductName(validProductName);

        Assertions.assertAll(
                () -> assertNotNull(product.getName()),
                () -> assertEquals(validProductName, product.getName())
        );
    }

    @Test
    @DisplayName("Should return the correct product price")
    void getPrice() {
        Price validPrice = new Price(BigDecimal.TWO);
        product.addProductPrice(validPrice);

        Assertions.assertAll(
                () -> assertNotNull(product.getPrice()),
                () -> assertEquals(BigDecimal.TWO, product.getPrice().value())
        );
    }

    @Test
    @DisplayName("Should return the correct sale condition type")
    void getSaleConditionType() {
        Assertions.assertAll(
                () -> assertNotNull(product.getSaleConditionType()),
                () -> assertEquals(SaleConditionType.WHOLESALE, product.getSaleConditionType())
        );
    }
}