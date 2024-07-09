package ru.clevertec.check.interfaces.commandline.parser;

import ru.clevertec.check.domain.model.valueobject.CardNumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiscountCardNumberParser implements ArgumentParser {
    private final String STATIC_PREFIX = "discountCard=";
    private final String REGEX = new StringBuilder()
            .append("^")
            .append(STATIC_PREFIX)
            .append("\\d+$")
            .toString();

    Pattern pattern = Pattern.compile(REGEX);

    @Override
    public void parse(String arg, ArgumentParsingContext context) {
        Matcher matcher = pattern.matcher(arg);
        if (matcher.find()) {
            String cardNumber = arg.substring(STATIC_PREFIX.length());
            Integer number = Integer.parseInt(cardNumber);
            CardNumber discountCardNumber = new CardNumber(number);
            context.setCardNumber(discountCardNumber);
        }
    }
}
