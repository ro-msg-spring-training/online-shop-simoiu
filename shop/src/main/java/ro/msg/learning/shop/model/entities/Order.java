package ro.msg.learning.shop.model.entities;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ORDER")
@SuperBuilder
public class Order extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Customer customer;

    private LocalDateTime createdAt;

    @Embedded
    @JsonUnwrapped
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderDetail> orderedProducts;
}