package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {
    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;

    public List<StockDto> getAllStocks() {
        return stockRepository.findAll().stream().map(this::convertToDto).toList();
    }

    public List<StockDto> getStocksByLocationId(Integer locationId) {
        return stockRepository.findAllByLocationId(locationId).stream().map(this::convertToDto).toList();
    }

    @Transactional
    public void updateStock(Integer productId, Integer locationId, @PositiveOrZero int quantity) {
        var stock = stockRepository.findByProductIdAndLocationId(productId, locationId);
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }

    private StockDto convertToDto(Stock product) {
        return modelMapper.map(product, StockDto.class);
    }
}