package ro.msg.learning.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Product;
import ro.msg.learning.shop.repository.custom.ProductCustomRepository;

@Repository
public interface ProductRepository extends ProductCustomRepository<String>, MongoRepository<Product, String> {
}