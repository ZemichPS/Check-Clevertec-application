package ru.clevertec.check.interfaces.rest.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateNewCheckRequestDto {
    List<ProductPositionDto> positions;
    BigDecimal debitCardBalance;
    Integer discountCardNumber;

}


