package ru.clevertec.check.interfaces.commandline.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathToSaveFileRegexParser implements ArgumentParser {
    private final String STATIC_PREFIX = "saveToFile=";
    private final String REGEX = new StringBuilder()
            .append("^")
            .append(STATIC_PREFIX)
            .append("(([^\\\\/]+[\\\\/])+[^\\\\/]+\\.[a-zA-Z]{2,})")
            .toString();
    Pattern pattern = Pattern.compile(REGEX);

    @Override
    public void parse(String arg, ArgumentParsingContext context) {
        Matcher matcher = pattern.matcher(arg);
        if (matcher.find()) {
            //String relativePathToSaveFile = arg.substring(STATIC_PREFIX.length());
            String relativePathToSaveFile = matcher.group(1);
            context.setRelativePathToSave(relativePathToSaveFile);
        }
    }
}
