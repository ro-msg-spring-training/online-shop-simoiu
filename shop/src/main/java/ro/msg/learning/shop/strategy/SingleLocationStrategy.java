package ro.msg.learning.shop.strategy;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.exception.NoLocationFoundException;
import ro.msg.learning.shop.model.entities.Order;
import ro.msg.learning.shop.model.entities.OrderDetail;
import ro.msg.learning.shop.model.entities.Product;
import ro.msg.learning.shop.model.entities.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ro.msg.learning.shop.repository.StockCustomRepositoryImpl.hasLocationAndProducts;

@RequiredArgsConstructor
public class SingleLocationStrategy implements LocationStrategy {
    private final StockRepository stockRepository;

    @Override
    public List<Stock> findStocksForOrder(Order order, List<BigDecimal> distances) {
        var locationsHavingRequiredProducts = stockRepository.findLocationsHavingRequiredProducts(order.getOrderedProducts());
        if (locationsHavingRequiredProducts.isEmpty()) {
            throw new NoLocationFoundException();
        }
        var productToQuantity =
                order.getOrderedProducts().stream().collect(Collectors.toMap(OrderDetail::getProduct, OrderDetail::getQuantity));
        return stockRepository
                .findAll(hasLocationAndProducts(locationsHavingRequiredProducts.get(0), order.getOrderedProducts()))
                .stream()
                .map(stock -> updateStockQuantity(stock, productToQuantity))
                .toList();
    }

    private Stock updateStockQuantity(Stock stock, Map<Product, Integer> productIdToQuantity) {
        return stock.toBuilder().quantity(productIdToQuantity.get(stock.getProduct())).build();
    }
}
