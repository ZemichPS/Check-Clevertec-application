package ru.clevertec.check.infrastructure.output.file.mapper;

import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.reflect.Field;

public class SimpleCSVStructureMapper implements CSVStructureMapper {
    @Override
    public String getLineFromValues(String... values) {
        return String.join(";", values) + "\n";
    }

    @Override
    public String getLineFromValues(RegisterType registerType, String... values) {
        return Stream.of(values)
                .map(value -> getAccordingRegisterType(value, registerType))
                .collect(Collectors.joining(";")) + "\n";
    }

    @Override
    public String getLineFromClassFieldsName(RegisterType registerType, Class<?> aClass) {
        if (aClass.isRecord()) {
            return Arrays.stream(aClass.getRecordComponents())
                    .map(RecordComponent::getName)
                    .map(this::splitCamelCase)
                    .map(fieldName-> getAccordingRegisterType(fieldName, registerType))
                    .collect(Collectors.joining(";")) + "\n";
        } else {
            return Arrays.stream(aClass.getDeclaredFields())
                    .peek(field -> field.setAccessible(true))
                    .map(Field::getName)
                    .map(fieldName-> getAccordingRegisterType(fieldName, registerType))
                    .collect(Collectors.joining(";")) + "\n";
        }
    }



    @Override
    public String getLinesFromObjectValuesWithAppendixes(List<?> sourceList, String... appendixes) throws RuntimeException {
        if (sourceList == null || sourceList.isEmpty()) return "";

        Object firstOobject = sourceList.getFirst();
        Class<?> aClass = firstOobject.getClass();

        return sourceList.stream()
                .map(object -> {
                            try {
                                return getLineFromObjectValues(object, appendixes);
                            } catch (RuntimeException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).collect(Collectors.joining("\n"));
    }

    public String getLineFromObjectValues(Object object, String... appendixes) throws
            RuntimeException {
        int appendixesLength = appendixes.length;

        Class<?> aClass = object.getClass();

        if (aClass.isRecord()) {
            int fieldsCount = aClass.getRecordComponents().length;
            checkOnArgumentEquals(appendixesLength, fieldsCount);
            List<String> withOutAppendixList = Arrays.stream(aClass.getRecordComponents())
                    .map(rComponent -> {
                        try {
                            return rComponent.getAccessor().invoke(object);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(this::getFieldValueByType)
                    .toList();
            return concatFieldsValuesWithAppendixes(withOutAppendixList, appendixes);

        } else {
            int fieldsCount = aClass.getDeclaredFields().length;
            checkOnArgumentEquals(appendixesLength, fieldsCount);

            List<String> declaredFieldsList = Arrays.stream(aClass.getDeclaredFields())
                    .peek(field -> field.setAccessible(true))
                    .map(field -> {
                        try {
                            return field.get(object);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }).map(this::getFieldValueByType)
                    .toList();
            return concatFieldsValuesWithAppendixes(declaredFieldsList, appendixes);
        }
    }

    private String concatFieldsValuesWithAppendixes(List<String> values, String... appendixes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < appendixes.length; i++) {
            String fieldValue = values.get(i);
            String appendix = appendixes[i];
            result.append(fieldValue).append(appendix).append(";");
        }
        return result.toString();
    }

    private String getFieldValueByType(Object o) {
        return switch (o) {
            case BigDecimal bd -> bd.setScale(2, RoundingMode.DOWN).toString();
            case Integer i -> i.toString();
            case Double d -> d.toString();
            case String s -> s;
            default -> o.toString();
        };
    }

    private void checkOnArgumentEquals(int appendixesLength, int fieldsCount) throws RuntimeException{
        if (fieldsCount != appendixesLength)
            throw new RuntimeException("The id of fields in the passed object is less than or equal to the id of endings in the array. Field count: %s. Array of endings: %s"
                    .formatted(fieldsCount, appendixesLength));
    }


    private String splitCamelCase(String input) {
        List<String> result = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c) && !currentWord.isEmpty()) {
                result.add(currentWord.toString());
                currentWord = new StringBuilder();
            }
            currentWord.append(c);
        }

        if (!currentWord.isEmpty()) {
            result.add(currentWord.toString());
        }

        return String.join(" ", result);
    }


    private String getAccordingRegisterType(String source, CSVStructureMapper.RegisterType registerType) {
        return switch (registerType) {
            case NONE -> source;
            case UPPER_CASE -> source.toUpperCase();
            case LOWER_CASE -> source.toLowerCase();
        };

    }
}


