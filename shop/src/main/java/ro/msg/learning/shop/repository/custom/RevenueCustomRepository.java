package ro.msg.learning.shop.repository.custom;

import ro.msg.learning.shop.model.RevenueForLocation;

import java.time.LocalDate;
import java.util.List;

public interface RevenueCustomRepository {
    List<RevenueForLocation> findAllRevenuesPerLocation(LocalDate date);
}
