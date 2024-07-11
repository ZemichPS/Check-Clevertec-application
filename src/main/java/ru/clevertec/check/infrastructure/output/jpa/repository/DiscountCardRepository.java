package ru.clevertec.check.infrastructure.output.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.infrastructure.output.jpa.entity.CheckEntity;
import ru.clevertec.check.infrastructure.output.jpa.entity.CheckItemEntity;
import ru.clevertec.check.infrastructure.output.jpa.entity.DiscountCardEntity;

import java.util.List;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCardEntity, Integer> {

}
