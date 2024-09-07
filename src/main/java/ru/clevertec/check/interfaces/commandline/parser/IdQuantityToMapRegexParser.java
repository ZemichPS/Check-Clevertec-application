package ru.clevertec.check.interfaces.commandline.parser;

import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdQuantityToMapRegexParser implements ArgumentParser {

    private final String REGEX = "\\d+-\\d+";
    Pattern pattern = Pattern.compile(REGEX);

    @Override
    public void parse(String arg, ArgumentParsingContext context) {
        Matcher matcher = pattern.matcher(arg);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String[] values = arg.substring(start, end).split("-");
            Integer id = Integer.parseInt(values[0]);
            Integer quantity = Integer.parseInt(values[1]);
            ProductId productId = new ProductId(id);
            context.addProductIdAndQuantity(productId, quantity);
        }

    }
}
