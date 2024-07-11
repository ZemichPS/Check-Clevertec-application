package ru.clevertec.check.infrastructure.output.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.check.infrastructure.output.jpa.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

}
