package ru.clevertec.check.application.ports.output;

import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface DiscountCardOutputPort {
    List<RealDiscountCard> findAll() throws URISyntaxException, IOException;
}
