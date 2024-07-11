package ru.clevertec.check.interfaces.rest.response;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private boolean wholesale;
}
