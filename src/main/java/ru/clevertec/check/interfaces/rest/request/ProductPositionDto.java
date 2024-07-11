package ru.clevertec.check.interfaces.rest.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductPositionDto {
    private Integer id;
    private Integer quantity;
}
