package ru.clevertec.check.domain.model.entity;

import ru.clevertec.check.domain.model.valueobject.Price;
import ru.clevertec.check.domain.model.valueobject.SaleConditionType;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.domain.model.valueobject.ProductName;
import ru.clevertec.check.domain.specification.PositiveProductPriceSpecification;
import ru.clevertec.check.domain.specification.ProductNameSpecification;

public class Product {
    private ProductId id;
    private ProductName name;
    private Price price;
    private SaleConditionType saleConditionType;

    public Product(ProductId id,
                   SaleConditionType saleConditionType) {
        this.id = id;
        this.saleConditionType = saleConditionType;
    }

    public Product() {
    }

    public void addProductName(ProductName name) {
        ProductNameSpecification spec = new ProductNameSpecification();
        spec.check(name);
        this.name = name;
    }

    public void addProductPrice(Price price) {
        PositiveProductPriceSpecification spec = new PositiveProductPriceSpecification();
        spec.check(price);
        this.price = price;
    }

    public ProductId getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public SaleConditionType getSaleConditionType() {
        return saleConditionType;
    }

    public void setSaleConditionType(SaleConditionType saleConditionType) {
        this.saleConditionType = saleConditionType;
    }
}
