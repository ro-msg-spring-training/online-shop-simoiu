package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.exception.NoSuchElementFoundException;
import ro.msg.learning.shop.model.entities.Product;
import ro.msg.learning.shop.repository.ProductRepository;

import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return getProductEntityById(id);
    }

    @Transactional
    public Product deleteProductById(String id) {
        var product = getProductEntityById(id);
        productRepository.deleteById(id);
        return product;
    }

    @Transactional
    public Product createProduct(@Valid Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProductById(String id, Product updatedProduct) {
        var product = getProductEntityById(id);
        mergeUpdatedProduct(product, updatedProduct);
        return productRepository.save(product);
    }

    private void mergeUpdatedProduct(Product productEntity, Product updatedProduct) {
        productEntity.setName(requireNonNullElse(updatedProduct.getName(), productEntity.getName()));
        productEntity.setDescription(requireNonNullElse(updatedProduct.getDescription(), productEntity.getDescription()));
        productEntity.setPrice(requireNonNullElse(updatedProduct.getPrice(), productEntity.getPrice()));
        productEntity.setWeight(requireNonNullElse(updatedProduct.getWeight(), productEntity.getWeight()));
        productEntity.setImageUrl(requireNonNullElse(updatedProduct.getImageUrl(), productEntity.getImageUrl()));
    }

    private Product getProductEntityById(String id) {
        var product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NoSuchElementFoundException("There is no product with id=%s".formatted(id));
        }
        return product.get();
    }
}
