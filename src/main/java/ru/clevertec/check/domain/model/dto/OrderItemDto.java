package ru.clevertec.check.domain.model.dto;

import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.model.valueobject.SaleConditionType;

import java.math.BigDecimal;

public record OrderItemDto(DiscountCard discountCard,
                           SaleConditionType saleConditionType,
                           Integer quantity,
                           BigDecimal price,
                           String description
                           ) {

}
