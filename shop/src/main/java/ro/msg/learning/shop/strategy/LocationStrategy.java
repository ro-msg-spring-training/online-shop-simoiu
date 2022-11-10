package ro.msg.learning.shop.strategy;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.model.entities.Order;
import ro.msg.learning.shop.model.entities.Stock;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface LocationStrategy {
    List<Stock> findStocksForOrder(Order order, List<BigDecimal> distances);
}
