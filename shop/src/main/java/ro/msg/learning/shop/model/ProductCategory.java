package ro.msg.learning.shop.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "PRODUCT_CATEGORY")
@SuperBuilder
public class ProductCategory extends BaseEntity {
    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Product> products;
}