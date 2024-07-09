package ru.clevertec.check.domain.model.valueobject;

import java.math.BigDecimal;

public record CheckItem(Integer qty,
                          String description,
                          BigDecimal price,
                          BigDecimal discount,
                          BigDecimal total) {
}
