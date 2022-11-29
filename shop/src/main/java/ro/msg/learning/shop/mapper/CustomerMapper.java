package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.CustomerDto;
import ro.msg.learning.shop.model.entities.Customer;

@Component
public class CustomerMapper implements DtoMapper<Customer, CustomerDto> {
    @Override
    public CustomerDto mapToDto(Customer entity) {
        return CustomerDto.builder()
                .customerId(entity.getId())
                .emailAddress(entity.getEmailAddress())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .username(entity.getUsername())
                .build();
    }

    @Override
    public Customer mapToEntity(CustomerDto dto) {
        return Customer.builder()
                .id(dto.getCustomerId())
                .emailAddress(dto.getEmailAddress())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .build();
    }
}
