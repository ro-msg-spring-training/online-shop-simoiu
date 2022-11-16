package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.config.serializer.BigDecimalToCurrencySerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDto {
    private String id;
    private String locationId;
    private LocalDate date;
    @JsonSerialize(using = BigDecimalToCurrencySerializer.class)
    private BigDecimal sum;
}