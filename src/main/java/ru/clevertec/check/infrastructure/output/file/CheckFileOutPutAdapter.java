package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.CheckRunner;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.infrastructure.output.file.mapper.SimpleCSVStructureMapper;
import ru.clevertec.check.infrastructure.utils.CSVWriter;
import ru.clevertec.check.infrastructure.utils.CheckToCSVFileWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class CheckFileOutPutAdapter extends AbstractFileOutputPort {

    private final CSVWriter<Check> CSVWriter = new CheckToCSVFileWriter(new SimpleCSVStructureMapper());

    public CheckFileOutPutAdapter(Path defaulResultPath) {
        super(defaulResultPath);
    }

    @Override
    public Check persist(Check check) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {
        Path resultPath = super.getPathToResultFile();
        CSVWriter.write(check, resultPath);
        return check;
    }

}



