package ro.msg.learning.shop.repository.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.RevenueForLocation;
import ro.msg.learning.shop.model.entities.Order;
import ro.msg.learning.shop.model.entities.OrderDetail;
import ro.msg.learning.shop.repository.aggregation.CustomProjectAggregationOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static ro.msg.learning.shop.config.DateTimeFormatConfiguration.DATE_FORMATTER;

@Repository
@RequiredArgsConstructor
public class RevenueRepositoryImpl implements RevenueCustomRepository {
    private static final String PRODUCT_COLLECTION = "product";
    private static final String ORDER_COLLECTION = "order";
    private final MongoTemplate mongoTemplate;

    @Override
    public List<RevenueForLocation> findAllRevenuesPerLocation(LocalDate date) {
        var orderIds = findOrderIdsCreatedAt(date);
        var query = """
                {
                    $lookup: {
                        from: "order",
                        localField: "order",
                        foreignField: "_id",
                        pipeline: [{
                            $match: {
                                $expr: { $in: [ "$_id", [%s] ] }
                            }
                        }],
                        "as": "order"
                    }
                }
                """.formatted(convertStringListToObjectIds(orderIds));
        var aggregation = newAggregation(
                new CustomProjectAggregationOperation(query),
                unwind(ORDER_COLLECTION),
                lookup(PRODUCT_COLLECTION, PRODUCT_COLLECTION, "_id", PRODUCT_COLLECTION),
                unwind(PRODUCT_COLLECTION),
                group("shippedFrom").sum(ArithmeticOperators.Multiply.valueOf("quantity").multiplyBy("product.price")).as("revenue"),
                addFields().addField("locationId").withValue("$_id").build(),
                project().andExclude("_id").and("")
        );
        return mongoTemplate.aggregate(aggregation, OrderDetail.class, RevenueForLocation.class).getMappedResults();
    }

    private String convertStringListToObjectIds(List<OrderIdModel> orderIds) {
        return orderIds
                .stream()
                .map(model -> "ObjectId('%s')".formatted(model.id))
                .collect(Collectors.joining(","));
    }

    private List<OrderIdModel> findOrderIdsCreatedAt(LocalDate date) {
        var aggregation = newAggregation(
                project().and("createdAt").dateAsFormattedString("%Y-%m-%d").as("createdAtDate"),
                match(Criteria.where("createdAtDate").is(date.format(DATE_FORMATTER))),
                project().andExclude("createdAt")
        );
        return mongoTemplate.aggregate(aggregation, Order.class, OrderIdModel.class).getMappedResults();
    }

    private static class OrderIdModel {
        private String id;
    }
}
