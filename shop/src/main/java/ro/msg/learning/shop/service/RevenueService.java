package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.RevenueDto;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.repository.RevenueRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RevenueService {
    private final RevenueRepository revenueRepository;
    private final ModelMapper modelMapper;

    public List<RevenueDto> getAllRevenues(LocalDate date) {
        return revenueRepository.findAllByDate(date).stream().map(this::convertToDto).toList();
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

    private RevenueDto convertToDto(Revenue revenue) {
        return modelMapper.map(revenue, RevenueDto.class);
    }
}
