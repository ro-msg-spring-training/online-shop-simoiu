package ro.msg.learning.shop.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

import static ro.msg.learning.shop.helper.test.TestDataHelper.*;

@ChangeUnit(id = "populate-data", order = "2", author = "mongock")
public class PopulateDbChangeUnit {
    @Execution
    public void populateData(MongoTemplate mongoTemplate) {
        mongoTemplate.insertAll(categories);
        mongoTemplate.insertAll(suppliers);
        mongoTemplate.insertAll(locations);
        mongoTemplate.insertAll(products);
        mongoTemplate.insertAll(stocks);
    }

    @RollbackExecution
    public void rollbackExecution() {
        // Our change is backward-compatible; we don't need to implement a rollback mechanism.
    }

}
