package ro.msg.learning.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Order;
import ro.msg.learning.shop.repository.custom.OrderCustomRepository;

@Repository
public interface OrderRepository extends OrderCustomRepository<Order>, MongoRepository<Order, String> {
}