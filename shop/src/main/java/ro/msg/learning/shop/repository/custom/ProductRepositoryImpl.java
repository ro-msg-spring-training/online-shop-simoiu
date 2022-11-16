package ro.msg.learning.shop.repository.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Product;
import ro.msg.learning.shop.model.entities.Stock;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository<String> {
    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteById(String id) {
        deleteStocksContainingProductId(id);
        mongoTemplate.remove(query(where("_id").is(id)), Product.class);
    }

    private void deleteStocksContainingProductId(String productId) {
        mongoTemplate.findAllAndRemove(
                query(where("product.id").is(productId)),
                Stock.class
        );
    }
}
