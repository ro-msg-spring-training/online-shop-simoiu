package ro.msg.learning.shop.strategy;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.StockDto;

import java.util.List;

@Component
public interface LocationStrategy {
    List<StockDto> findStocksForOrder(OrderDto order);
}
