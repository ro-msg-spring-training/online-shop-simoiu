package ro.msg.learning.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByUsername(String username);
}