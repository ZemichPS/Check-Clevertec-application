package ru.clevertec.check.domain.model.valueobject;

import org.junit.jupiter.api.*;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemHolderTest {

    private ItemHolder itemHolder = new ItemHolder();

    @Test
    @Order(1)
    void addItem() {
        CheckItem checkItem = new CheckItem(
                5,
                "Milk 1l",
                BigDecimal.ONE,
                BigDecimal.valueOf(0.2),
                BigDecimal.valueOf(4.8)
        );

        itemHolder.addItem(checkItem);
        Assertions.assertAll(
                () -> assertEquals(1, itemHolder.getItems().size()),
                () -> assertNotNull(itemHolder.getItems().getFirst()),
                () -> assertDoesNotThrow(() -> itemHolder.addItem(checkItem))
        );
    }

    @Test
    @Order(2)
    void getItems() {
        CheckItem checkItem = new CheckItem(
                5,
                "Milk 1l",
                BigDecimal.ONE,
                BigDecimal.valueOf(0.2),
                BigDecimal.valueOf(4.8)
        );

        itemHolder.addItem(checkItem);

        Assertions.assertAll(
                () -> assertEquals(1, itemHolder.getItems().size()),
                () -> assertNotNull(itemHolder.getItems().getFirst())
        );
    }

    @Test
    @Order(3)
    void getItemsCount() {
        CheckItem checkItem = new CheckItem(
                5,
                "Milk 1l",
                BigDecimal.ONE,
                BigDecimal.valueOf(0.2),
                BigDecimal.valueOf(4.8)
        );

        itemHolder.addItem(checkItem);
        Assertions.assertAll(
                () -> assertEquals(1, itemHolder.getItems().size())
        );
    }
}