package ru.clevertec.check.interfaces.commandline.parser;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathFromProductFileRegexParser implements ArgumentParser {
    private final String STATIC_PREFIX = "pathToFile=";
    private final String REGEX = new StringBuilder()
            .append("^")
            .append(STATIC_PREFIX)
            .append("([a-zA-Z0-9_\\-][a-zA-Z0-9_\\-./]*\\.csv)$")
            .toString();
    Pattern pattern = Pattern.compile(REGEX);

    @Override
    public void parse(String arg, ArgumentParsingContext context) {
        Matcher matcher = pattern.matcher(arg);
        if (matcher.find()) {
            String line = matcher.group(0).replace(STATIC_PREFIX, "");
            Path relativePath = Path.of(line);
            context.setPathFromProductFile(relativePath);
        }
    }
}
