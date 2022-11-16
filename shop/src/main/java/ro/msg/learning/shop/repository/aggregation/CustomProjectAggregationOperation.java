package ro.msg.learning.shop.repository.aggregation;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.util.Collections;
import java.util.List;

public class CustomProjectAggregationOperation implements AggregationOperation {

    private final List<Document> documents;
    private final String json;

    private static final String DUMMY_KEY = "dummy";

    public CustomProjectAggregationOperation(String json) {
        documents = parseJson(json);
        this.json = json;
    }

    @Override
    public Document toDocument(AggregationOperationContext context) {
        return Document.parse(json);
    }

    @Override
    public List<Document> toPipelineStages(AggregationOperationContext context) {
        return documents;
    }

    @Override
    public String getOperator() {
        return documents.iterator().next().keySet().iterator().next();
    }

    private static List<Document> parseJson(String json) {
        return (json.startsWith("["))
                ? Document.parse("{\"" + DUMMY_KEY + "\": " + json + "}").getList(DUMMY_KEY, Document.class)
                : Collections.singletonList(Document.parse(json));
    }
}
