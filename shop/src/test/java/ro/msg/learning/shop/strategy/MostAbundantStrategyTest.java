package ro.msg.learning.shop.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.exception.NoLocationFoundException;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MostAbundantStrategyTest extends AbstractStrategyTest {
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private MostAbundantStrategy locationStrategy;

    @Test
    void findStocksForOrder_whenThereAreNoLocationsToFulfilTheOrder_shouldReturnEmptyList() {
        var orderDto = OrderDto.builder().orderedProducts(orderedProducts).build();
        when(stockRepository.findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(any(Integer.class), anyInt())).thenReturn(null);
        assertThrows(
                NoLocationFoundException.class,
                () -> locationStrategy.findStocksForOrder(orderDto)
        );
    }

    @Test
    void findStocksForOrder_whenThereAreMultipleLocationsToFulfilTheOrder_shouldReturnMostAbundantStockForEachOrderDetail() {
        var orderDto = OrderDto.builder().orderedProducts(orderedProducts).build();
        var resultingList = mockRepository(1, 2, 3, 4);
        assertThat(locationStrategy.findStocksForOrder(orderDto)).hasSize(resultingList.size());
    }

    private List<Stock> mockRepository(Integer... productIds) {
        return Stream.of(productIds).map(productId -> {
            var stock = resultingStocks.get(productId).get(0);
            when(stockRepository.findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(eq(productId), anyInt())).thenReturn(stock);
            return stock;
        }).toList();
    }
}