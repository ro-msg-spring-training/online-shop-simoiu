package ro.msg.learning.shop.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "SUPPLIER")
@SuperBuilder
public class Supplier extends BaseEntity {
    @NaturalId
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier supplier)) return false;
        return name != null && name.equals(supplier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}