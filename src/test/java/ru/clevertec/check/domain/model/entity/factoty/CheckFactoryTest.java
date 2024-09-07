package ru.clevertec.check.domain.model.entity.factoty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.valueobject.CheckItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CheckFactoryTest {

    @Test
    @DisplayName("Create new Check from CheckItems")
    void testGetNewFromCheckItems() {
        CheckItem checkItem = new CheckItem(
                5,
                "Milk 1l",
                BigDecimal.valueOf(1.14),
                BigDecimal.valueOf(0.35),
                BigDecimal.valueOf(5.35)
        );

        List<CheckItem> checkItems = Collections.singletonList(checkItem);
        Check newCheck = CheckFactory.getNewFromCheckItems(checkItems);

        Assertions.assertAll(
                () -> assertNotNull(newCheck),
                () -> assertEquals(LocalDate.now(), newCheck.getCreationDate()),
                () -> assertEquals(LocalTime.now().getHour(), newCheck.getCreationTime().getHour()),
                () -> assertEquals(LocalTime.now().getMinute(), newCheck.getCreationTime().getMinute()),
                () -> assertEquals(1, newCheck.getItems().size()),
                () -> assertEquals(checkItem, newCheck.getItems().get(0))
        );

    }
}
