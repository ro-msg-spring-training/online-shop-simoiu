package ro.msg.learning.shop.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document
@SuperBuilder
public class ProductCategory extends BaseEntity {
    private String name;

    private String description;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'category':?#{#self._id} }")
    @ToString.Exclude
    private List<Product> products;
}