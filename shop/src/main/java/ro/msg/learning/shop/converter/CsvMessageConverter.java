package ro.msg.learning.shop.converter;

import lombok.SneakyThrows;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.fasterxml.jackson.databind.type.TypeFactory.rawClass;

@Component
public class CsvMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {
    private static final MediaType SUPPORTED_MEDIA_TYPE = new MediaType("text", "csv");
    private final CsvHelper<T> csvHelper;

    public CsvMessageConverter(final CsvHelper<T> csvHelper) {
        super(SUPPORTED_MEDIA_TYPE);
        this.csvHelper = csvHelper;
    }

    @SneakyThrows
    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        if (!(type instanceof ParameterizedType parameterizedType)) {
            return false;
        }
        return mediaType != null && mediaType.equals(SUPPORTED_MEDIA_TYPE) && super.canRead(mediaType)
                && Class.forName(parameterizedType.getRawType().getTypeName()).isAssignableFrom(List.class);
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        var listType = type instanceof Class ? type.getClass() : (Class<?>) ((ParameterizedType) type).getRawType();

        if (!listType.isAssignableFrom(List.class)) {
            return false;
        }

        return this.canWrite(clazz, mediaType);
    }

    @Override
    protected void writeInternal(List<T> ts, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        csvHelper.toCsv(rawClass(((ParameterizedType) type).getActualTypeArguments()[0]), ts, outputMessage.getBody());
    }

    @Override
    protected List<T> readInternal(Class clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException, IOException {
        return csvHelper.fromCsv(clazz, inputMessage.getBody());
    }

    @Override
    public List<T> read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return readInternal(contextClass, inputMessage);
    }

}
