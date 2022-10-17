package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.dto.RevenueForLocation;
import ro.msg.learning.shop.model.Revenue;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    @Query(nativeQuery = true)
    List<RevenueForLocation> findAllRevenuesPerLocation(LocalDate date);
    List<Revenue> findAllByDate(LocalDate date);
}