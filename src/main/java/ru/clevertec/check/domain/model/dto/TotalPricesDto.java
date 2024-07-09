package ru.clevertec.check.domain.model.dto;

import java.math.BigDecimal;

public record TotalPricesDto(BigDecimal totalPrice,
                             BigDecimal totalDiscount,
                             BigDecimal totalWithDiscount) {
}
