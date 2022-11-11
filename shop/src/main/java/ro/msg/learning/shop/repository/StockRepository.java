package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Stock;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>, JpaSpecificationExecutor<Stock>, StockCustomRepository {

    Stock findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(Integer productId, int quantity);

    Stock findByProductIdAndLocationId(Integer productId, Integer locationId);

    List<Stock> findAllByLocationId(Integer locationId);
    List<Stock> findAllByProductId(Integer productId);
}