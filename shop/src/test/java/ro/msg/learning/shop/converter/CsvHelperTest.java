package ro.msg.learning.shop.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.dto.StockDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CsvHelperTest {
    public static final String CSV_HEADER = "id,locationId,productId,quantity\n";
    private static final String CSV_AS_STRING = CSV_HEADER + """
            1,5,7,10
            8,10,2,12354
            4,1,19,8
            """;

    @InjectMocks
    private CsvHelper<StockDto> csvHelper;

    @Test
    void toCsv_withMultipleStocks_shouldConvertToCsv() throws IOException {
        var outputStream = new ByteArrayOutputStream();
        csvHelper.toCsv(StockDto.class, generateStockList(), outputStream);
        assertThat(outputStream).hasToString(CSV_AS_STRING);
    }

    @Test
    void toCsv_withNoStock_shouldReturnOnlyCsvHeader() throws IOException {
        var outputStream = new ByteArrayOutputStream();
        csvHelper.toCsv(StockDto.class, List.of(), outputStream);
        assertThat(outputStream).hasToString(CSV_HEADER);
    }

    @Test
    void fromCsv_withMultipleStocksCsv_shouldConvertToList() throws IOException {
        var inputStream = new ByteArrayInputStream(CSV_AS_STRING.getBytes());
        List<StockDto> stockList = csvHelper.fromCsv(StockDto.class, inputStream);
        assertThat(stockList).isEqualTo(generateStockList());
    }

    @Test
    void fromCsv_onlyWithEmptyHeader_shouldConvertToEmptyList() throws IOException {
        var inputStream = new ByteArrayInputStream(CSV_HEADER.getBytes());
        List<StockDto> stockList = csvHelper.fromCsv(StockDto.class, inputStream);
        assertThat(stockList).isEqualTo(List.of());
    }

    private List<StockDto> generateStockList() {
        return List.of(
                StockDto.builder().id(1).locationId(5).productId(7).quantity(10).build(),
                StockDto.builder().id(8).locationId(10).productId(2).quantity(12354).build(),
                StockDto.builder().id(4).locationId(1).productId(19).quantity(8).build()
        );
    }
}