package ro.msg.learning.shop.repository.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.OrderDetail;
import ro.msg.learning.shop.model.entities.Stock;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class StockCustomRepositoryImpl implements StockCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<String> findLocationsHavingRequiredProducts(List<OrderDetail> orderDetailList) {
        var orExpression = new ArrayList<Criteria>();
        orderDetailList.forEach(orderDetailDto -> orExpression.add(
                where("product.id").is(orderDetailDto.getProduct().getId())
                        .and("quantity").gte(orderDetailDto.getQuantity())
        ));
        var criteriaDefinition = new Criteria().orOperator(orExpression.toArray(new Criteria[0]));
        var filterStocksWithQuantity = match(criteriaDefinition);
        var groupByLocation = group("location").count().as("nolocations");
        var havingAllLocations = match(new Criteria("nolocations").is(orderDetailList.size()));
        var sortById = sort(Sort.by("_id"));
        var selectLocation = project("location");

        var aggregation = newAggregation(filterStocksWithQuantity, groupByLocation, havingAllLocations, sortById, selectLocation);
        var result = mongoTemplate.aggregate(aggregation, Stock.class, LocationIdModel.class);
        return result.getMappedResults().stream().map(locationIdModel -> locationIdModel.id).toList();
    }

    public List<Stock> findByLocationAndProducts(String locationId, List<OrderDetail> orderDetailList) {
        var orExpression = new ArrayList<Criteria>();
        orderDetailList.forEach(orderDetailDto -> orExpression.add(
                where("product.id").is(orderDetailDto.getProduct().getId())
                        .and("location.id").is(locationId)
        ));
        var query = new Query().addCriteria(new Criteria().orOperator(orExpression.toArray(new Criteria[0])));
        return mongoTemplate.find(query, Stock.class);
    }

    private static class LocationIdModel {
        private String id;
    }
}
