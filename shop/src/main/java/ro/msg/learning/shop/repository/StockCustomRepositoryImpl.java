package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.model.Stock;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockCustomRepositoryImpl implements StockCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Integer> findLocationsHavingRequiredProducts(List<OrderDetailDto> orderDetailList) {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Integer.class);
        var stock = query.from(Stock.class);
        Path<Integer> productID = stock.get("product").get("id");
        Path<Integer> quantity = stock.get("quantity");
        Path<Integer> locationID = stock.get("location").get("id");
        var predicates = new ArrayList<Predicate>();

        orderDetailList.forEach(orderDetailDto -> predicates.add(
                cb.and(
                        cb.equal(productID, orderDetailDto.getProductId()),
                        cb.greaterThanOrEqualTo(quantity, orderDetailDto.getQuantity())
                )
        ));

        query.select(locationID)
                .where(cb.or(predicates.toArray(new Predicate[0])))
                .groupBy(locationID)
                .having(cb.equal(cb.count(productID), orderDetailList.size()));

        return entityManager.createQuery(query).getResultList();
    }

    public static Specification<Stock> hasLocationAndProducts(Integer locationId, List<OrderDetailDto> orderDetailList) {
        return (stock, cq, cb) -> {
            Path<Integer> locationIdPath = stock.get("location").get("id");
            Path<Integer> productIdPath = stock.get("product").get("id");
            var predicates = new ArrayList<Predicate>();

            orderDetailList.forEach(orderDetailDto -> predicates.add(
                    cb.and(
                            cb.equal(locationIdPath, locationId),
                            cb.equal(productIdPath, orderDetailDto.getProductId())
                    )
            ));
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}
