package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Location")
@SuperBuilder
public class Location extends BaseEntity {
    private String name;

    @Embedded
    @JsonUnwrapped
    private Address address;

    @OneToMany(mappedBy = "shippedFrom")
    @ToString.Exclude
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<Revenue> revenues;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<Stock> stocks;
}