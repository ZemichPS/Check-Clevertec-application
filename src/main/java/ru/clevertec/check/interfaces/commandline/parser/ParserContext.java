package ru.clevertec.check.interfaces.commandline.parser;

import java.util.ArrayList;
import java.util.List;

public class ParserContext {

    private final List<ArgumentParser> parsers = new ArrayList<>();

    public void addParser(ArgumentParser parser) {
        parsers.add(parser);
    }


    public ArgumentParsingContext parseArguments(String[] args) {

        ArgumentParsingContext context = new ArgumentParsingContext();

        for (String arg : args) {
            for (ArgumentParser parser : parsers) {
                parser.parse(arg, context);
            }
        }
        return context;
    }


}
