package ru.clevertec.check.interfaces.rest.request;

import lombok.*;
import ru.clevertec.check.domain.model.valueobject.Price;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.domain.model.valueobject.ProductName;
import ru.clevertec.check.domain.model.valueobject.SaleConditionType;

import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewProductRequestDto {
    private String name;
    private BigDecimal price;
    private boolean wholesale;
}
