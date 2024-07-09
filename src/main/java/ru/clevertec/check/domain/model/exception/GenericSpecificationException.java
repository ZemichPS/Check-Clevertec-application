package ru.clevertec.check.domain.model.exception;

import ru.clevertec.check.domain.model.exception.shared.AbstractException;

public class GenericSpecificationException extends AbstractException {

    public GenericSpecificationException(String message) {
        super(message);
    }

    @Override
    public String getErrorText() {
        return "INTERNAL SERVER ERROR";
    }
}
