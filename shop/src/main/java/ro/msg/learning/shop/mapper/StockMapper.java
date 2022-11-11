package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.entities.Location;
import ro.msg.learning.shop.model.entities.Product;
import ro.msg.learning.shop.model.entities.Stock;

import static ro.msg.learning.shop.helper.MapperHelper.getIdFromEntity;

@Component
public class StockMapper implements DtoMapper<Stock, StockDto> {
    @Override
    public StockDto mapToDto(Stock entity) {
        return StockDto.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .locationId(getIdFromEntity(entity.getLocation()))
                .productId(getIdFromEntity(entity.getProduct()))
                .build();
    }

    @Override
    public Stock mapToEntity(StockDto dto) {
        return Stock.builder()
                .id(dto.getId())
                .quantity(dto.getQuantity())
                .location(Location.builder().id(dto.getLocationId()).build())
                .product(Product.builder().id(dto.getProductId()).build())
                .build();
    }
}
