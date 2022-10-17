package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.strategy.LocationStrategy;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final LocationStrategy locationStrategy;
    private final StockService stockService;
    private final ModelMapper modelMapper;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        var stocks = locationStrategy.findStocksForOrder(orderDto);
        var order = convertToEntity(orderDto);
        order.setOrderedProducts(
                stocks.stream()
                        .map(stockDto -> convertStockToOrderDetail(stockDto, order))
                        .toList()
        );
        order.getOrderedProducts().forEach(od -> od.setOrder(order));
        var savedOrder = orderRepository.save(order);
        stocks.forEach(stockDto -> stockService.updateStock(stockDto.getProductId(), stockDto.getLocationId(), stockDto.getQuantity()));
        return convertToDto(savedOrder);
    }

    private OrderDetail convertStockToOrderDetail(StockDto stockDto, Order order) {
        return OrderDetail.builder()
                .order(order)
                .product(Product.builder().id(stockDto.getProductId()).build())
                .shippedFrom(Location.builder().id(stockDto.getLocationId()).build())
                .quantity(stockDto.getQuantity())
                .build();
    }

    private OrderDto convertToDto(Order product) {
        return modelMapper.map(product, OrderDto.class);
    }

    private Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }
}
