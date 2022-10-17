package ro.msg.learning.shop.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Product")
@SuperBuilder
public class Product extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    private Double weight;
    private String imageUrl;

    @ManyToOne
    @JoinColumn
    private ProductCategory category;

    @ManyToOne
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Stock> stocks;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderDetail> orderDetails;
}