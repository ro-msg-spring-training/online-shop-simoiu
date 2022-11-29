package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.model.entities.Order;

@Component
@RequiredArgsConstructor
public class OrderMapper implements DtoMapper<Order, OrderDto> {
    private final AddressMapper addressMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final CustomerMapper customerMapper;

    @Override
    public OrderDto mapToDto(Order entity) {
        return OrderDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .customer(customerMapper.mapToDto(entity.getCustomer()))
                .deliveryAddress(addressMapper.mapToDto(entity.getAddress()))
                .orderedProducts(orderDetailMapper.mapAllToDto(entity.getOrderedProducts()))
                .build();
    }

    @Override
    public Order mapToEntity(OrderDto dto) {
        return Order.builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .customer(customerMapper.mapToEntity(dto.getCustomer()))
                .address(addressMapper.mapToEntity(dto.getDeliveryAddress()))
                .orderedProducts(orderDetailMapper.mapAllToEntities(dto.getOrderedProducts()))
                .build();
    }
}
