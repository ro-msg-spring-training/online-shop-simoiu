package ro.msg.learning.shop.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document
@SuperBuilder
public class Supplier extends BaseEntity {
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