package ru.clevertec.check.infrastructure.output.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.infrastructure.output.jpa.entity.CheckEntity;

import java.util.UUID;

@Repository
public interface CheckRepository extends JpaRepository<CheckEntity, UUID> {

}
