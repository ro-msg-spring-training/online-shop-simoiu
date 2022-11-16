package ro.msg.learning.shop.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import ro.msg.learning.shop.repository.CustomerRepository;

import static ro.msg.learning.shop.helper.test.TestDataHelper.users;

@ChangeUnit(id = "populate-users", order = "1", author = "mongock")
public class UsersChangeUnit {
    @Execution
    public void populateUsers(CustomerRepository customerRepository) {
        customerRepository.saveAll(users);
    }

    @RollbackExecution
    public void rollbackExecution() {
        // Our change is backward-compatible; we don't need to implement a rollback mechanism.
    }
}
