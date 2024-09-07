package ru.clevertec.check.infrastructure.output.file.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCSVStructureMapperTest {

    private final SimpleCSVStructureMapper mapper = new SimpleCSVStructureMapper();

    @Test
    @DisplayName("Test getLineFromValues with default delimiter")
    void testGetLineFromValues() {
        String result = mapper.getLineFromValues("value1", "value2", "value3");
        assertEquals("value1;value2;value3\n", result);
    }

    @Test
    @DisplayName("Test getLineFromValues with register type")
    void testGetLineFromValuesWithRegisterType() {
        String result = mapper.getLineFromValues(CSVStructureMapper.RegisterType.UPPER_CASE, "value1", "value2", "value3");
        assertEquals("VALUE1;VALUE2;VALUE3\n", result);
    }

    @Test
    @DisplayName("Test getLineFromClassFieldsName with Record class")
    void testGetLineFromClassFieldsNameWithRecord() {
        record TestRecord(String fieldOne, int fieldTwo) {
        }
        String result = mapper.getLineFromClassFieldsName(CSVStructureMapper.RegisterType.LOWER_CASE, TestRecord.class);
        assertEquals("field one;field two\n", result);
    }

    @Test
    @DisplayName("Test getLinesFromObjectValuesWithAppendixes with Record object")
    void testGetLinesFromObjectValuesWithAppendixes() {
        record TestRecord(String fieldOne, BigDecimal fieldTwo) {
        }
        List<TestRecord> records = List.of(new TestRecord("value1", BigDecimal.TEN));
        String result = mapper.getLinesFromObjectValuesWithAppendixes(records, "append1", "append2");
        assertEquals("value1append1;10.00append2;", result);
    }

}
