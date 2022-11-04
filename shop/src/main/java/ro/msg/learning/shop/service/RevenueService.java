package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.entities.Location;
import ro.msg.learning.shop.model.entities.Revenue;
import ro.msg.learning.shop.repository.RevenueRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RevenueService {
    private final RevenueRepository revenueRepository;

    public List<Revenue> getAllRevenues(LocalDate date) {
        return revenueRepository.findAllByDate(date);
    }

    @Transactional
    public void saveAllRevenuesForToday() {
        var today = LocalDate.now();
        var revenues = revenueRepository
                .findAllRevenuesPerLocation(today)
                .stream()
                .map(revenueForLocation ->
                        Revenue.builder()
                                .location(Location.builder().id(revenueForLocation.getLocationId()).build())
                                .sum(revenueForLocation.getRevenue())
                                .date(today)
                                .build()
                )
                .toList();
        if (!revenues.isEmpty()) {
            revenueRepository.saveAll(revenues);
        }
    }
}
