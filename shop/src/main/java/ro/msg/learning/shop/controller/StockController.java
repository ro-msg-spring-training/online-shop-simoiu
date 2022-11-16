package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.mapper.StockMapper;
import ro.msg.learning.shop.service.StockService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/stocks", produces = "text/csv")
public class StockController {
    private final StockService stockService;
    private final StockMapper stockMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockDto> getAllStocks(HttpServletResponse response) {
        // by default, the browser will download this as stocks.csv
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=all_stocks.csv");
        return stockMapper.mapAllToDto(stockService.getAllStocks());
    }

    @GetMapping(value = "/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<StockDto> getStocksByLocationId(@PathVariable String locationId) {
        return stockMapper.mapAllToDto(stockService.getStocksByLocationId(locationId));
    }
}
