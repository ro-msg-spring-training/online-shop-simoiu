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
@SuperBuilder(toBuilder = true)
public class Stock extends BaseEntity {
    @DocumentReference(lazy = true)
    private Product product;

    @DocumentReference(lazy = true)
    private Location location;

    private int quantity;
}