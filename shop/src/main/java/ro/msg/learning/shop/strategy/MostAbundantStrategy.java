package ro.msg.learning.shop.strategy;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.exception.NoLocationFoundException;
import ro.msg.learning.shop.model.entities.Order;
import ro.msg.learning.shop.model.entities.OrderDetail;
import ro.msg.learning.shop.model.entities.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class MostAbundantStrategy implements LocationStrategy {
    private final StockRepository stockRepository;

    @Override
    public List<Stock> findStocksForOrder(Order order, List<BigDecimal> distances) {
        return order.getOrderedProducts()
                .stream()
                .map(this::getStockWithLargestQuantityForProduct)
                .toList();
    }

    private Stock getStockWithLargestQuantityForProduct(OrderDetail orderDetail) {
        var stock = stockRepository.findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(orderDetail.getProduct().getId(), orderDetail.getQuantity());
        if (stock == null) {
            throw new NoLocationFoundException("There is not enough stock for productId=%s".formatted(orderDetail.getProduct().getId()));
        }
        return stock.toBuilder().quantity(orderDetail.getQuantity()).build();
    }
}
