package ru.clevertec.check.application.ports.output;

import ru.clevertec.check.domain.model.valueobject.ProductPosition;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

public interface ProductOutputPort {

    List<ProductPosition> findAll() throws URISyntaxException, IOException;

    List<ProductPosition> findAllFromSpecific(Path path) throws URISyntaxException, IOException;
}
