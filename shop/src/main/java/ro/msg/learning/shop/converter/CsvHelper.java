package ro.msg.learning.shop.converter;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvHelper<T> {
    private final CsvMapper csvMapper = new CsvMapper();

    public List<T> fromCsv(final Class<?> pojoType, final InputStream csvInputStream) throws IOException {
        final CsvSchema schema = csvMapper.schemaFor(pojoType).withHeader();
        return csvMapper.readerFor(pojoType).with(schema).<T>readValues(csvInputStream).readAll();
    }

    public void toCsv(final Class<?> pojoType, final List<T> pojoList, final OutputStream outputStream) throws IOException {
        final CsvSchema schema = csvMapper.schemaFor(pojoType).withHeader();
        csvMapper.writer(schema).writeValue(outputStream, pojoList);
    }
}
