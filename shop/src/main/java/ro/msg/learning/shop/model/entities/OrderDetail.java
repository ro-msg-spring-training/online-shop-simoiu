package ro.msg.learning.shop.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ORDER_DETAIL")
@SuperBuilder
public class OrderDetail extends BaseEntity {
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Location shippedFrom;

    private int quantity;
}