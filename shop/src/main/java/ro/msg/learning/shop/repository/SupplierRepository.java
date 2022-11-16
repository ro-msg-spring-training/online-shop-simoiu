package ro.msg.learning.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Supplier;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {
}
