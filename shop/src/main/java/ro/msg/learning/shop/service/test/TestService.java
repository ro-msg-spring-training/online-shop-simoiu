package ro.msg.learning.shop.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.entities.BaseEntity;
import ro.msg.learning.shop.repository.*;

import static ro.msg.learning.shop.helper.test.TestDataHelper.*;

@Service
@RequiredArgsConstructor
@Transactional
@Profile("test")
public class TestService {
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public void populateData() {
        saveAndUpdateEntityId(laptop, productCategoryRepository);
        saveAndUpdateEntityId(pcComponent, productCategoryRepository);
        saveAndUpdateEntityId(mobile, productCategoryRepository);
        saveAndUpdateEntityId(asus, supplierRepository);
        saveAndUpdateEntityId(gigabyte, supplierRepository);
        saveAndUpdateEntityId(samsung, supplierRepository);
        saveAndUpdateEntityId(pcGarage, locationRepository);
        saveAndUpdateEntityId(amazon, locationRepository);
        saveAndUpdateEntityId(rogZephyrus, productRepository);
        saveAndUpdateEntityId(rtx3080, productRepository);
        saveAndUpdateEntityId(rx6900, productRepository);
        saveAndUpdateEntityId(samsungS22Ultra, productRepository);
        saveAndUpdateEntityId(admin, customerRepository);
        stockRepository.saveAll(stocks);
    }

    public void clearData() {
        orderDetailRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        locationRepository.deleteAllInBatch();
        supplierRepository.deleteAllInBatch();
        productCategoryRepository.deleteAllInBatch();
    }

    public static <T extends BaseEntity> void saveAndUpdateEntityId(T entity, JpaRepository<T, Integer> repository) {
        var newEntity = repository.save(entity);
        entity.setId(newEntity.getId());
    }
}
