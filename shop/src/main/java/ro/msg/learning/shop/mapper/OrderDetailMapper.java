package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.model.entities.Location;
import ro.msg.learning.shop.model.entities.OrderDetail;
import ro.msg.learning.shop.model.entities.Product;

@Component
public class OrderDetailMapper implements DtoMapper<OrderDetail, OrderDetailDto> {
    @Override
    public OrderDetailDto mapToDto(OrderDetail entity) {
        return OrderDetailDto.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .productId(entity.getProduct().getId())
                .locationId(entity.getShippedFrom().getId())
                .build();
    }

    @Override
    public OrderDetail mapToEntity(OrderDetailDto dto) {
        return OrderDetail.builder()
                .id(dto.getId())
                .quantity(dto.getQuantity())
                .product(Product.builder().id(dto.getProductId()).build())
                .shippedFrom(Location.builder().id(dto.getLocationId()).build())
                .build();
    }
}
