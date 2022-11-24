package ro.msg.learning.shop.dto.thymeleaf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.dto.OrderDetailDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDto {
    private List<OrderDetailDto> shoppingItems;
}
