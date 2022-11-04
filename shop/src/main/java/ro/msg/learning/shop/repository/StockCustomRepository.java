package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.model.entities.OrderDetail;

import java.util.List;

public interface StockCustomRepository {
    List<Integer> findLocationsHavingRequiredProducts(List<OrderDetail> orderDetailList);
}
