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

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document
@SuperBuilder
public class Location extends BaseEntity {
    private String name;

    @JsonUnwrapped
    private Address address;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'shippedFrom':?#{#self._id} }")
    @ToString.Exclude
    private List<OrderDetail> orderDetails;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'location':?#{#self._id} }")
    @ToString.Exclude
    private List<Revenue> revenues;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'location':?#{#self._id} }")
    @ToString.Exclude
    private List<Stock> stocks;
}