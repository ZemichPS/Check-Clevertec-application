package ru.clevertec.check.infrastructure.output.file.mapper.shared;

import ru.clevertec.check.domain.model.exception.InternalServerErrorException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CSVStructureMapper {

    String getLineFromValues(String... values);

    String getLineFromValues(RegisterType registerType, String... values);

    String getLineFromClassFieldsName(RegisterType registerType, Class<?> clazz);

    String getLinesFromObjectValuesWithAppendixes(List<?> sourceList, String... appendixes);

    default String addDownSpaceLine() {
        return ("\n");
    }

    String getLineFromObjectValues(Object object, String... appendixes) throws InternalServerErrorException, InvocationTargetException, IllegalAccessException;

    enum RegisterType {
        NONE,
        UPPER_CASE,
        LOWER_CASE

    }

}
