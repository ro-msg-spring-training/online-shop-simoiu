package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.RevenueDto;
import ro.msg.learning.shop.service.RevenueService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/revenues", produces = MediaType.APPLICATION_JSON_VALUE)
public class RevenueController {
    private final RevenueService revenueService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RevenueDto> getRevenuesForDate(@RequestParam LocalDate date) {
        return revenueService.getAllRevenues(date);
    }
}
