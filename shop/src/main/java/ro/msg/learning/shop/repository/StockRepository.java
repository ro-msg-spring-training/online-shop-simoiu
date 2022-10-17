package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Stock;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>, JpaSpecificationExecutor<Stock>, StockCustomRepository {

    Stock findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(Integer productId, int quantity);

    List<Stock> findAllByLocationId(Integer locationId);

    Stock findByProductIdAndLocationId(Integer productId, Integer locationId);
}