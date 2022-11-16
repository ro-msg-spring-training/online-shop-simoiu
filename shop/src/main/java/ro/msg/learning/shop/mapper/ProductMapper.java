package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.model.entities.Product;
import ro.msg.learning.shop.model.entities.ProductCategory;
import ro.msg.learning.shop.model.entities.Supplier;

import static ro.msg.learning.shop.helper.MapperHelper.getIdFromEntity;

@Component
public class ProductMapper implements DtoMapper<Product, ProductDto> {

    @Override
    public ProductDto mapToDto(Product entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .weight(entity.getWeight())
                .imageUrl(entity.getImageUrl())
                .categoryId(getIdFromEntity(entity.getCategory()))
                .supplierId(getIdFromEntity(entity.getSupplier()))
                .build();
    }

    @Override
    public Product mapToEntity(ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .weight(dto.getWeight())
                .imageUrl(dto.getImageUrl())
                .category(ProductCategory.builder().id(dto.getId()).build())
                .supplier(Supplier.builder().id(dto.getSupplierId()).build())
                .build();
    }
}
