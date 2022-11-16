package ro.msg.learning.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Revenue;
import ro.msg.learning.shop.repository.custom.RevenueCustomRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevenueRepository extends RevenueCustomRepository, MongoRepository<Revenue, String> {
    List<Revenue> findAllByDate(LocalDate date);
}