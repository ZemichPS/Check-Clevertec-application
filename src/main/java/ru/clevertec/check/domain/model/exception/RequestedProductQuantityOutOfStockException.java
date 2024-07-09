package ru.clevertec.check.domain.model.exception;

import ru.clevertec.check.domain.model.exception.shared.AbstractException;

public class RequestedProductQuantityOutOfStockException extends AbstractException {
    public RequestedProductQuantityOutOfStockException(String message) {
        super(message);
    }

    @Override
    public String getErrorText() {
        return "bad request";
    }
}
