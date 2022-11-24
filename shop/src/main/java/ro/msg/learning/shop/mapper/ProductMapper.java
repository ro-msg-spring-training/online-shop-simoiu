package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.model.entities.Product;

@Component
@RequiredArgsConstructor
public class ProductMapper implements DtoMapper<Product, ProductDto> {
    private final ProductCategoryMapper productCategoryMapper;
    private final SupplierMapper supplierMapper;

    @Override
    public ProductDto mapToDto(Product entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .weight(entity.getWeight())
                .imageUrl(entity.getImageUrl())
                .category(productCategoryMapper.mapToDto(entity.getCategory()))
                .supplier(supplierMapper.mapToDto(entity.getSupplier()))
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
                .category(productCategoryMapper.mapToEntity(dto.getCategory()))
                .supplier(supplierMapper.mapToEntity(dto.getSupplier()))
                .build();
    }
}
