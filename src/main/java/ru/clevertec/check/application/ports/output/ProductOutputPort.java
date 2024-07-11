package ru.clevertec.check.application.ports.output;

import ru.clevertec.check.domain.model.exception.BadFilePathException;
import ru.clevertec.check.domain.model.valueobject.ProductPosition;

import java.io.IOException;
import java.util.List;

public interface ProductOutputPort {

    List<ProductPosition> findAll() throws IOException, BadFilePathException;

}
