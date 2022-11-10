package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.AddressDto;
import ro.msg.learning.shop.model.entities.Address;

@Component
public class AddressMapper implements DtoMapper<Address, AddressDto> {

    @Override
    public AddressDto mapToDto(Address entity) {
        return AddressDto.builder()
                .city(entity.getCity())
                .country(entity.getCountry())
                .county(entity.getCounty())
                .street(entity.getStreet())
                .build();
    }

    @Override
    public Address mapToEntity(AddressDto dto) {
        return Address.builder()
                .city(dto.getCity())
                .country(dto.getCountry())
                .county(dto.getCounty())
                .street(dto.getStreet())
                .build();
    }
}
