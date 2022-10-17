package ro.msg.learning.shop.strategy;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.config.LocationConfiguration;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.exception.NoLocationFoundException;
import ro.msg.learning.shop.exception.RouteMatrixApiException;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class GreedyStrategy implements LocationStrategy {
    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;
    private final RestTemplate restTemplate;
    private final LocationConfiguration locationConfiguration;

    @Override
    public List<StockDto> findStocksForOrder(OrderDto order) {
        var allLocations = locationRepository.findAll();
        var distances = getClosestLocationsFromOrder(order.getDeliveryAddress(), allLocations);
        var productIdToQuantity =
                order.getOrderedProducts().stream().collect(Collectors.toMap(OrderDetailDto::getProductId, OrderDetailDto::getQuantity));
        var stocks = distances
                .stream()
                .map(distance -> extract(distance.getLocation().getId(), productIdToQuantity))
                .flatMap(Collection::stream)
                .toList();
        if (!productIdToQuantity.isEmpty()) {
            throw new NoLocationFoundException("There is not enough stock for productIds=%s".formatted(productIdToQuantity.keySet()));
        }
        return stocks;
    }

    private List<StockDto> extract(Integer locationId, Map<Integer, Integer> productIdToQuantity) {
        var selectedStocks = new ArrayList<StockDto>();
        productIdToQuantity.replaceAll((productId, quantity) -> {
            var currentStock = stockRepository.findByProductIdAndLocationId(productId, locationId);
            if (currentStock == null) {
                return quantity;
            }
            var selectedStock = mapToStockDto(currentStock, Math.min(currentStock.getQuantity(), quantity));
            selectedStocks.add(selectedStock);
            return quantity - selectedStock.getQuantity();
        });
        productIdToQuantity.entrySet().removeIf(e -> e.getValue() == 0);
        return selectedStocks;
    }

    private StockDto mapToStockDto(Stock stock, int quantity) {
        return StockDto.builder()
                .locationId(stock.getLocation().getId())
                .productId(stock.getProduct().getId())
                .quantity(quantity)
                .build();
    }

    private List<BigDecimal> getDistancesFromLocations(Address deliveryAddress, List<Address> stockLocations) {
        var addressList = Stream.concat(Stream.of(deliveryAddress), stockLocations.stream()).toList();
        var request = LocationRequest.builder().locations(addressList).build();
        var response = restTemplate.postForObject("/routematrix?key=%s".formatted(locationConfiguration.getApiKey()), request, RouteMatrixResponse.class);
        if (response == null || response.getInfo() == null) {
            throw new RouteMatrixApiException("Unable to get any response from the API");
        }
        if (response.getInfo().getStatusCode() != 0) {
            throw new RouteMatrixApiException(String.join("\n", response.getInfo().getMessages()));
        }
        if (response.getDistance().size() != addressList.size()) {
            throw new RouteMatrixApiException("API returned %d elements instead of %d".formatted(response.getDistance().size(), addressList.size()));
        }
        return response.getDistance();
    }

    private List<DistanceToLocation> getClosestLocationsFromOrder(Address deliveryAddress, List<Location> allLocations) {
        var stockLocations = allLocations.stream().map(Location::getAddress).toList();
        var distances = getDistancesFromLocations(deliveryAddress, stockLocations);
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
