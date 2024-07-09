package ru.clevertec.check.domain.model.valueobject;

import ru.clevertec.check.domain.model.entity.Product;

import java.math.BigDecimal;

public record ProductPosition(Product product, Integer quantity) {

}
