package ro.msg.learning.shop.repository.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Order;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomRepository<Order> {
    private final MongoTemplate mongoTemplate;

    @Override
    public Order save(Order order) {
        var savedOrder = mongoTemplate.insert(order);
        order.getOrderedProducts().forEach(od -> od.setOrder(savedOrder));
        mongoTemplate.insertAll(order.getOrderedProducts());
        return savedOrder;
    }
}
