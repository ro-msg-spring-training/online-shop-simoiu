package ro.msg.learning.shop.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

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