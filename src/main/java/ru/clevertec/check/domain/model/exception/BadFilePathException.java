package ru.clevertec.check.domain.model.exception;

import ru.clevertec.check.domain.model.exception.shared.AbstractException;

public class BadFilePathException extends AbstractException {

    public BadFilePathException(String message) {
        super(message);
    }

    @Override
    public String getErrorText() {
        return "BAD REQUEST";
    }
}
