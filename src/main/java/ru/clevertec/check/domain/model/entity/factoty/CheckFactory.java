package ru.clevertec.check.domain.model.entity.factoty;

import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.valueobject.CheckId;
import ru.clevertec.check.domain.model.valueobject.CheckItem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class CheckFactory {

    public static Check getNewFromCheckItems(List<CheckItem> checkItems){
        Check newCheck = new Check(
                new CheckId(UUID.randomUUID()),
                LocalDate.now(),
                LocalTime.now()
        );

        checkItems.forEach(newCheck::addCheckItem);
        return newCheck;
    }
}
