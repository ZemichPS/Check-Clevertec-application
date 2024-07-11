package ru.clevertec.check.interfaces.rest.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CheckItemDto {
    private Integer id;
    private Integer quantity;
    BigDecimal price;
    BigDecimal discount;
    BigDecimal total;
}
