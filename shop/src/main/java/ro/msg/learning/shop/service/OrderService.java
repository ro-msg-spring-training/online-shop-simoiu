package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.entities.*;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.strategy.LocationStrategy;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final LocationStrategy locationStrategy;
    private final StockService stockService;

    @Transactional
    public Order createOrder(Order order, List<BigDecimal> distances) {
        var stocks = locationStrategy.findStocksForOrder(order, distances);
        order.setOrderedProducts(
                stocks.stream()
                        .map(stock -> convertStockToOrderDetail(stock, order))
                        .toList()
        );
        order.getOrderedProducts().forEach(od -> od.setOrder(order));
        var savedOrder = orderRepository.save(order);
        stocks.forEach(stock -> stockService.updateStock(stock.getProduct().getId(), stock.getLocation().getId(), stock.getQuantity()));
        return savedOrder;
    }

    private OrderDetail convertStockToOrderDetail(Stock stock, Order order) {
        return OrderDetail.builder()
                .order(order)
                .product(Product.builder().id(stock.getProduct().getId()).build())
                .shippedFrom(Location.builder().id(stock.getLocation().getId()).build())
                .quantity(stock.getQuantity())
                .build();
    }
}
