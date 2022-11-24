package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.model.entities.ProductCategory;

@Component
public class ProductCategoryMapper implements DtoMapper<ProductCategory, ProductCategoryDto> {
    @Override
    public ProductCategoryDto mapToDto(ProductCategory entity) {
        return ProductCategoryDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .name(entity.getName())
                .build();
    }

    @Override
    public ProductCategory mapToEntity(ProductCategoryDto dto) {
        return ProductCategory.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .name(dto.getName())
                .build();
    }
}
