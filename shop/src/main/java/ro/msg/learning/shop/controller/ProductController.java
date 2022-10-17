package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PostMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProductById(@PathVariable Integer id, @RequestBody ProductDto productToUpdate) {
        return productService.updateProductById(id, productToUpdate);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto deleteProductById(@PathVariable Integer id) {
        return productService.deleteProductById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }
}
