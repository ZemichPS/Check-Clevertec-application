package ru.clevertec.check.interfaces.commandline.parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BalanceDebitCardRegexParser implements ArgumentParser {
    private final String STATIC_PREFIX = "balanceDebitCard=";
    private final String REGEX = new StringBuilder()
            .append("^")
            .append(STATIC_PREFIX)
            .append("-?\\d+(\\.\\d{1,})?$")
            .toString();
    Pattern pattern = Pattern.compile(REGEX);

    @Override
    public void parse(String arg, ArgumentParsingContext context) {
        Matcher matcher = pattern.matcher(arg);
        if (matcher.find()) {
            String balance = arg.substring(STATIC_PREFIX.length());
            context.setBalanceDebitCard(new BigDecimal(balance).setScale(2, RoundingMode.DOWN));
        }
    }
}
