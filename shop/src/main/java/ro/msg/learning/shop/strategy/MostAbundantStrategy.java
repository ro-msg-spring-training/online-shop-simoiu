package ro.msg.learning.shop.strategy;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.exception.NoLocationFoundException;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;

@RequiredArgsConstructor
public class MostAbundantStrategy implements LocationStrategy {
    private final StockRepository stockRepository;

    @Override
    public List<StockDto> findStocksForOrder(OrderDto order) {
        return order.getOrderedProducts()
                .stream()
                .map(this::mapToStockDto)
                .toList();
    }

    private StockDto mapToStockDto(OrderDetailDto orderDetail) {
        var stock = stockRepository.findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(orderDetail.getProductId(), orderDetail.getQuantity());
        if (stock == null) {
            throw new NoLocationFoundException("There is not enough stock for productId=%s".formatted(orderDetail.getProductId()));
        }
        return StockDto.builder()
                .locationId(stock.getLocation().getId())
                .productId(stock.getProduct().getId())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}
