package ru.clevertec.check.domain.model.valueobject;

import org.junit.jupiter.api.*;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemHolderTest {

    private ItemHolder itemHolder = new ItemHolder();

    @Test
    @DisplayName("Should add item to ItemHolder and verify its presence")
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
                () -> assertTrue(itemHolder.getItems().contains(checkItem))
        );
    }

    @Test
    @DisplayName("Should return correct items and their count")
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
                () -> assertEquals(1, itemHolder.getItemsCount()),
                () -> assertEquals(checkItem,itemHolder.getItems().getFirst())
        );
    }

    @Test
    @DisplayName("Should return the correct count of items in ItemHolder")
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