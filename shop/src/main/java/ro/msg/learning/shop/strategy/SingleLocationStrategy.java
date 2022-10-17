package ro.msg.learning.shop.strategy;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.exception.NoLocationFoundException;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ro.msg.learning.shop.repository.StockCustomRepositoryImpl.hasLocationAndProducts;

@RequiredArgsConstructor
public class SingleLocationStrategy implements LocationStrategy {
    private final StockRepository stockRepository;

    @Override
    public List<StockDto> findStocksForOrder(OrderDto order) {
        var locationsHavingRequiredProducts = stockRepository.findLocationsHavingRequiredProducts(order.getOrderedProducts());
        if (locationsHavingRequiredProducts.isEmpty()) {
            throw new NoLocationFoundException();
        }
        var productIdToQuantity =
                order.getOrderedProducts().stream().collect(Collectors.toMap(OrderDetailDto::getProductId, OrderDetailDto::getQuantity));
        return stockRepository
                .findAll(hasLocationAndProducts(locationsHavingRequiredProducts.get(0), order.getOrderedProducts()))
                .stream()
                .map(stock -> mapToStockDto(stock, productIdToQuantity))
                .toList();
    }

    private StockDto mapToStockDto(Stock stock, Map<Integer, Integer> productIdToQuantity) {
        return StockDto.builder()
                .locationId(stock.getLocation().getId())
                .productId(stock.getProduct().getId())
                .quantity(productIdToQuantity.get(stock.getProduct().getId()))
                .build();
    }
}
