package ro.msg.learning.shop.repository.custom;

import ro.msg.learning.shop.model.entities.OrderDetail;
import ro.msg.learning.shop.model.entities.Stock;

import java.util.List;

public interface StockCustomRepository {
    List<String> findLocationsHavingRequiredProducts(List<OrderDetail> orderDetailList);
    List<Stock> findByLocationAndProducts(String locationId, List<OrderDetail> orderDetailList);
}
