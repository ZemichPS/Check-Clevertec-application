package ru.clevertec.check.infrastructure.output.jpa;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.check.application.ports.output.DiscountCardOutputPort;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.infrastructure.output.jpa.mapper.DiscountCardMapper;
import ru.clevertec.check.infrastructure.output.jpa.repository.DiscountCardRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class DiscountCardJpaOutputAdapter implements DiscountCardOutputPort {
    private final DiscountCardRepository discountCardRepository;

    public DiscountCardJpaOutputAdapter(DiscountCardRepository discountCardRepository) {
        this.discountCardRepository = discountCardRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RealDiscountCard> findAll() throws URISyntaxException, IOException {

        return discountCardRepository.findAll().stream()
                .map(DiscountCardMapper::map)
                .toList();
    }
}
