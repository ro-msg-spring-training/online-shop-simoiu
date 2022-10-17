package ro.msg.learning.shop.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ro.msg.learning.shop.dto.RevenueForLocation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NamedNativeQuery(name = "Revenue.findAllRevenuesPerLocation",
        query = """
                select OD.SHIPPED_FROM_ID as locationId, SUM(OD.QUANTITY * P.PRICE) as revenue
                from (select Id
                      from "ORDER"
                      where cast(CREATED_AT as date) = :date) OID
                         join ORDER_DETAIL OD on OD.ORDER_ID = OID.ID
                         join PRODUCT P on OD.PRODUCT_ID = P.ID
                group by OD.SHIPPED_FROM_ID
                  """,
        resultSetMapping = "RevenueForLocation")
@SqlResultSetMapping(name = "RevenueForLocation",
        classes = @ConstructorResult(
                targetClass = RevenueForLocation.class,
                columns = {
                        @ColumnResult(name = "locationId"),
                        @ColumnResult(name = "revenue")
                }))
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "REVENUE")
@SuperBuilder
public class Revenue extends BaseEntity {
    @ManyToOne
    @JoinColumn
    private Location location;
    private LocalDate date;
    private BigDecimal sum;
}