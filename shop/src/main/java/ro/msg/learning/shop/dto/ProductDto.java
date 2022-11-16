package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    @NotEmpty
    private String name;
    private String description;
    private Double price;
    private Double weight;
    private String imageUrl;
    private String categoryId;
    private String supplierId;
}