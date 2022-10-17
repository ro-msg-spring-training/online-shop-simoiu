package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.exception.NoSuchElementFoundException;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.ProductRepository;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::convertToDto).toList();
    }

    public ProductDto getProductById(Integer id) {
        return convertToDto(getProductEntityById(id));
    }

    @Transactional
    public ProductDto deleteProductById(Integer id) {
       var product = getProductEntityById(id);
       productRepository.deleteById(id);
       return convertToDto(product);
    }

    @Transactional
    public ProductDto createProduct(@Valid ProductDto product) {
        var savedProduct = productRepository.save(convertToEntity(product));
        return convertToDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProductById(Integer id, ProductDto updatedProduct) {
        var product = getProductEntityById(id);
        modelMapper.map(updatedProduct, product);
        var savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    private Product getProductEntityById(Integer id) {
        var product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NoSuchElementFoundException(String.format("There is no product with id=%d", id));
        }
        return product.get();
    }

    private ProductDto convertToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private Product convertToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }
}
