package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.SupplierDto;
import ro.msg.learning.shop.model.entities.Supplier;

@Component
public class SupplierMapper implements DtoMapper<Supplier, SupplierDto> {
    @Override
    public SupplierDto mapToDto(Supplier entity) {
        return SupplierDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public Supplier mapToEntity(SupplierDto dto) {
        return Supplier.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
