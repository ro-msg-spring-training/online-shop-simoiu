package ro.msg.learning.shop.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.entities.*;

import static ro.msg.learning.shop.helper.test.TestDataHelper.*;

@Service
@RequiredArgsConstructor
@Transactional
@Profile("test")
public class TestService {
    private final MongoTemplate mongoTemplate;

    public void populateData() {
        mongoTemplate.insertAll(categories);
        mongoTemplate.insertAll(suppliers);
        mongoTemplate.insertAll(locations);
        mongoTemplate.insertAll(products);
        mongoTemplate.insertAll(stocks);
    }

    public void clearData() {
        mongoTemplate.remove(new Query(), ProductCategory.class);
        mongoTemplate.remove(new Query(), Supplier.class);
        mongoTemplate.remove(new Query(), Location.class);
        mongoTemplate.remove(new Query(), Product.class);
        mongoTemplate.remove(new Query(), Stock.class);
    }
}
