package ro.msg.learning.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Stock;
import ro.msg.learning.shop.repository.custom.StockCustomRepository;

import java.util.List;

@Repository
public interface StockRepository extends MongoRepository<Stock, String>, StockCustomRepository {

    Stock findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(String productId, int quantity);

    Stock findByProductIdAndLocationId(String productId, String locationId);

    List<Stock> findAllByLocationId(String locationId);
}