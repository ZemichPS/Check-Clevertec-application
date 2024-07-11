package ru.clevertec.check.infrastructure.output.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.infrastructure.output.jpa.entity.CheckEntity;
import ru.clevertec.check.infrastructure.output.jpa.entity.CheckItemEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface CheckItemRepository extends JpaRepository<CheckItemEntity, UUID> {
    List<CheckItemEntity> findAllByCheck(CheckEntity checkEntity);
}
