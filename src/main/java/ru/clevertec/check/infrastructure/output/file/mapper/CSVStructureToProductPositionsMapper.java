package ru.clevertec.check.infrastructure.output.file.mapper;

import ru.clevertec.check.domain.model.entity.Product;
import ru.clevertec.check.domain.model.valueobject.ProductPosition;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.ProductName;
import ru.clevertec.check.domain.model.valueobject.Price;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.domain.model.valueobject.SaleConditionType;
import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureToObjectMapper;

import java.math.BigDecimal;
import java.util.List;


public class CSVStructureToProductPositionsMapper implements CSVStructureToObjectMapper<ProductPosition> {

    @Override
    public List<ProductPosition> map(List<String[]> source) throws GenericSpecificationException {
        return source.stream().map(array -> {
            Integer id = Integer.parseInt(array[0]);
            String description = array[1];
            String stringPrice = array[2].replace(",", ".");
            BigDecimal price = new BigDecimal(stringPrice);
            int quantity = Integer.parseInt(array[3]);
            SaleConditionType type = SaleConditionType.fromString(array[4]);

            Product product = new Product(
                    new ProductId(id),
                    type
            );
            product.addProductName(new ProductName(description));
            product.addProductPrice(new Price(price));
            return new ProductPosition(product, quantity);
        }).toList();
    }
}
