package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class OrderDto {
    private Integer id;
    @JsonUnwrapped
    private AddressDto deliveryAddress;
    private LocalDateTime createdAt;
    private Integer customerId;
    private List<OrderDetailDto> orderedProducts;
}