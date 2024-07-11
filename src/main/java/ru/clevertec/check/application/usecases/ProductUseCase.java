package ru.clevertec.check.application.usecases;

import ru.clevertec.check.domain.model.entity.Product;

import java.util.List;

public interface ProductUseCase{
    Product create(Product product);
    List<Product> fetchAll();


}
