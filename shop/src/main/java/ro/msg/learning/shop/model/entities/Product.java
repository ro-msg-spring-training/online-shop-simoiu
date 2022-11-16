package ro.msg.learning.shop.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document
@SuperBuilder
public class Product extends BaseEntity {
    @NotEmpty
    private String name;
    private String description;
    private Double price;
    private Double weight;
    private String imageUrl;

    @DocumentReference(lazy = true)
    private ProductCategory category;

    @DocumentReference(lazy = true)
    private Supplier supplier;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'product':?#{#self._id} }")
    @ToString.Exclude
    private List<Stock> stocks;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'product':?#{#self._id} }")
    @ToString.Exclude
    private List<OrderDetail> orderDetails;
}