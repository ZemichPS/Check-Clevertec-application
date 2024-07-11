package ru.clevertec.check.infrastructure.output.jpa;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.check.application.ports.output.CheckOutputPort;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.infrastructure.output.jpa.entity.CheckEntity;
import ru.clevertec.check.infrastructure.output.jpa.mapper.CheckMapper;
import ru.clevertec.check.infrastructure.output.jpa.repository.CheckRepository;


public class CheckJpaOutputAdapter implements CheckOutputPort {

    private final CheckRepository checkRepository;

    public CheckJpaOutputAdapter(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Check persist(Check check) {
        CheckEntity checkEntity = CheckMapper.map(check);
        CheckEntity savedCheck = checkRepository.save(checkEntity);
        return CheckMapper.map(savedCheck);
    }
}
