package ru.clevertec.check.infrastructure.output.jpa;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.check.application.ports.output.ProductOutputPort;
import ru.clevertec.check.domain.model.exception.BadFilePathException;
import ru.clevertec.check.domain.model.valueobject.ProductPosition;
import ru.clevertec.check.infrastructure.output.jpa.mapper.ProductMapper;
import ru.clevertec.check.infrastructure.output.jpa.repository.ProductRepository;

import java.io.IOException;
import java.util.List;

public class ProductJpaOutputAdapter implements ProductOutputPort {

    private final ProductRepository productRepository;

    public ProductJpaOutputAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPosition> findAll() throws IOException, BadFilePathException {
        return productRepository.findAll().stream()
                .map(ProductMapper::map)
                .toList();
    }
}
