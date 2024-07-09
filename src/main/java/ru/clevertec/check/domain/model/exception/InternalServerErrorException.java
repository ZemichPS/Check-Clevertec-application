package ru.clevertec.check.domain.model.exception;

import ru.clevertec.check.domain.model.exception.shared.AbstractException;

public class InternalServerErrorException extends AbstractException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    @Override
    public String getErrorText() {
        return "internal server error";
    }
}
