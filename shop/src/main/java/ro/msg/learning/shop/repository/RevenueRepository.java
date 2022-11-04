package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Revenue;
import ro.msg.learning.shop.model.interfaces.RevenueForLocation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    @Query(value = """
            select OD.SHIPPED_FROM_ID as locationId, SUM(OD.QUANTITY * P.PRICE) as revenue
            from (select Id
                  from "ORDER"
                  where cast(CREATED_AT as date) = :date) OID
                     join ORDER_DETAIL OD on OD.ORDER_ID = OID.ID
                     join PRODUCT P on OD.PRODUCT_ID = P.ID
            group by OD.SHIPPED_FROM_ID
              """, nativeQuery = true)
    List<RevenueForLocation> findAllRevenuesPerLocation(LocalDate date);

    List<Revenue> findAllByDate(LocalDate date);
}