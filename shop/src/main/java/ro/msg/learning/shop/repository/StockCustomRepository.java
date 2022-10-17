package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.dto.OrderDetailDto;

import java.util.List;

public interface StockCustomRepository {
    List<Integer> findLocationsHavingRequiredProducts(List<OrderDetailDto> orderDetailList);
}
