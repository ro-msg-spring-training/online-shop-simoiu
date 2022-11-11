package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.model.entities.Customer;
import ro.msg.learning.shop.model.entities.Order;

import static ro.msg.learning.shop.helper.MapperHelper.getIdFromEntity;

@Component
@RequiredArgsConstructor
public class OrderMapper implements DtoMapper<Order, OrderDto> {
    private final AddressMapper addressMapper;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDto mapToDto(Order entity) {
        return OrderDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .customerId(getIdFromEntity(entity.getCustomer()))
                .deliveryAddress(addressMapper.mapToDto(entity.getAddress()))
                .orderedProducts(orderDetailMapper.mapAllToDto(entity.getOrderedProducts()))
                .build();
    }

    @Override
    public Order mapToEntity(OrderDto dto) {
        return Order.builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .customer(Customer.builder().id(dto.getId()).build())
                .address(addressMapper.mapToEntity(dto.getDeliveryAddress()))
                .orderedProducts(orderDetailMapper.mapAllToEntities(dto.getOrderedProducts()))
                .build();
    }
}
