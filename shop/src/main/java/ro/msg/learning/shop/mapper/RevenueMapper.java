package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.RevenueDto;
import ro.msg.learning.shop.model.entities.Location;
import ro.msg.learning.shop.model.entities.Revenue;

import static ro.msg.learning.shop.helper.MapperHelper.getIdFromEntity;

@Component
public class RevenueMapper implements DtoMapper<Revenue, RevenueDto> {
    @Override
    public RevenueDto mapToDto(Revenue entity) {
        return RevenueDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .sum(entity.getSum())
                .locationId(getIdFromEntity(entity.getLocation()))
                .build();
    }

    @Override
    public Revenue mapToEntity(RevenueDto dto) {
        return Revenue.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .sum(dto.getSum())
                .location(Location.builder().id(dto.getLocationId()).build())
                .build();
    }

}
