package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.entities.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {
    private final StockRepository stockRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public List<Stock> getStocksByLocationId(Integer locationId) {
        return stockRepository.findAllByLocationId(locationId);
    }

    @Transactional
    public void updateStock(Integer productId, Integer locationId, @PositiveOrZero int quantity) {
        var stock = stockRepository.findByProductIdAndLocationId(productId, locationId);
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }
}