package ro.msg.learning.shop.strategy;

import org.junit.jupiter.api.BeforeEach;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.Map;

public abstract class AbstractStrategyTest {
    protected List<OrderDetailDto> orderedProducts;
    protected Map<Integer, List<Stock>> resultingStocks;

    @BeforeEach
    void setUp() {
        orderedProducts = List.of(
                OrderDetailDto.builder().productId(1).quantity(5).build(),
                OrderDetailDto.builder().productId(2).quantity(1).build(),
                OrderDetailDto.builder().productId(3).quantity(3).build(),
                OrderDetailDto.builder().productId(4).quantity(6).build()
        );
        resultingStocks = Map.of(
                1, List.of(
                        Stock.builder().id(1).location(Location.builder().id(1).build()).product(Product.builder().id(1).build()).quantity(10).build(),
                        Stock.builder().id(2).location(Location.builder().id(2).build()).product(Product.builder().id(1).build()).quantity(6).build()
                ),
                2, List.of(
                        Stock.builder().id(3).location(Location.builder().id(1).build()).product(Product.builder().id(2).build()).quantity(9).build(),
                        Stock.builder().id(4).location(Location.builder().id(2).build()).product(Product.builder().id(2).build()).quantity(2).build()
                ),
                3, List.of(
                        Stock.builder().id(5).location(Location.builder().id(1).build()).product(Product.builder().id(3).build()).quantity(10).build(),
                        Stock.builder().id(6).location(Location.builder().id(2).build()).product(Product.builder().id(3).build()).quantity(6).build()
                ),
                4, List.of(
                        Stock.builder().id(7).location(Location.builder().id(1).build()).product(Product.builder().id(4).build()).quantity(3).build(),
                        Stock.builder().id(8).location(Location.builder().id(2).build()).product(Product.builder().id(4).build()).quantity(1).build()
                )
        );
    }
}
