package ro.msg.learning.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.OrderDetail;

@Repository
public interface OrderDetailRepository extends MongoRepository<OrderDetail, String> {
}