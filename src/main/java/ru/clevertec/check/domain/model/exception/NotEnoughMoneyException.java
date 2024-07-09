package ru.clevertec.check.domain.model.exception;

import ru.clevertec.check.domain.model.exception.shared.AbstractException;

public class NotEnoughMoneyException extends AbstractException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }

    @Override
    public String getErrorText() {
        return "not enough money";
    }
}
