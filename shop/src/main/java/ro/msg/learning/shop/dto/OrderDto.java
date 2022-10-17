package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ro.msg.learning.shop.model.Address;

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
    private Address deliveryAddress;
    private LocalDateTime createdAt;
    private Integer customerId;
    private List<OrderDetailDto> orderedProducts;
}