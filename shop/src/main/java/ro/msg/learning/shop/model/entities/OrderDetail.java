package ro.msg.learning.shop.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document
@SuperBuilder
public class OrderDetail extends BaseEntity {
    @DocumentReference(lazy = true)
    private Order order;

    @DocumentReference(lazy = true)
    private Product product;

    @DocumentReference(lazy = true)
    private Location shippedFrom;

    private int quantity;
}