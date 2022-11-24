package ro.msg.learning.shop.service.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.OrderDetailDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartService {
    private final Map<String, OrderDetailDto> cartProducts = new HashMap<>();

    public OrderDetailDto addToShoppingCart(OrderDetailDto orderDetailDto) {
        return cartProducts.merge(orderDetailDto.getProductId(), orderDetailDto, (o1, o2) -> {
            o1.setQuantity(o1.getQuantity() + o2.getQuantity());
            return o1;
        });
    }

    public List<OrderDetailDto> getShoppingCart() {
        return new ArrayList<>(cartProducts.values());
    }

    public void updateItemsQuantity(List<OrderDetailDto> shoppingItems) {
        shoppingItems.forEach(item -> cartProducts.computeIfPresent(item.getProductId(), (k, v) -> {
            v.setQuantity(item.getQuantity());
            return v.getQuantity() == 0 ? null : v;
        }));
    }

    public void clearCart() {
        cartProducts.clear();
    }
}
