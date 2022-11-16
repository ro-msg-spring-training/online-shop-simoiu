package ro.msg.learning.shop.model.entities;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document
@SuperBuilder
public class Order extends BaseEntity {
    private LocalDateTime createdAt;

    @JsonUnwrapped
    private Address address;

    @DocumentReference(lazy = true)
    private Customer customer;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'order':?#{#self._id} }")
    @ToString.Exclude
    private List<OrderDetail> orderedProducts;
}