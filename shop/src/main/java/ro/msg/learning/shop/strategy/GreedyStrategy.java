package ro.msg.learning.shop.strategy;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.exception.NoLocationFoundException;
import ro.msg.learning.shop.model.entities.*;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class GreedyStrategy implements LocationStrategy {
    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<Stock> findStocksForOrder(Order order, List<BigDecimal> distances) {
        var allLocations = locationRepository.findAll();
        var distanceToLocations = mapDistancesToDto(distances, allLocations);
        var productToQuantity =
                order.getOrderedProducts().stream().collect(Collectors.toMap(OrderDetail::getProduct, OrderDetail::getQuantity));
        var stocks = distanceToLocations
                .stream()
                .map(distance -> extract(distance.getLocation().getId(), productToQuantity))
                .flatMap(Collection::stream)
                .toList();
        if (!productToQuantity.isEmpty()) {
            throw new NoLocationFoundException("There is not enough stock for productIds=%s".formatted(productToQuantity.keySet()));
        }
        return stocks;
    }

    private List<Stock> extract(String locationId, Map<Product, Integer> productIdToQuantity) {
        var selectedStocks = new ArrayList<Stock>();
        productIdToQuantity.replaceAll((product, quantity) -> {
            var currentStock = stockRepository.findByProductIdAndLocationId(product.getId(), locationId);
            if (currentStock == null) {
                return quantity;
            }
            var selectedStock = currentStock.toBuilder().quantity(Math.min(currentStock.getQuantity(), quantity)).build();
            selectedStocks.add(selectedStock);
            return quantity - selectedStock.getQuantity();
        });
        productIdToQuantity.entrySet().removeIf(e -> e.getValue() == 0);
        return selectedStocks;
    }

    private List<DistanceToLocation> mapDistancesToDto(List<BigDecimal> distances, List<Location> allLocations) {
        return IntStream
                .range(1, distances.size())
                .mapToObj(index ->
                        DistanceToLocation.builder()
                                .distanceFromTarget(distances.get(index))
                                .location(allLocations.get(index - 1))
                                .build())
                .sorted(Comparator.comparing(DistanceToLocation::getDistanceFromTarget))
                .toList();
    }

    @Data
    @Builder
    private static class DistanceToLocation {
        private Location location;
        private BigDecimal distanceFromTarget;
    }
}
