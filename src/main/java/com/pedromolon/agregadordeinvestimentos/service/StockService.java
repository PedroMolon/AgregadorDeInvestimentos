package com.pedromolon.agregadordeinvestimentos.service;

import com.pedromolon.agregadordeinvestimentos.dto.request.StockRequest;
import com.pedromolon.agregadordeinvestimentos.mapper.StockMapper;
import com.pedromolon.agregadordeinvestimentos.repository.StockRepository;
import com.pedromolon.agregadordeinvestimentos.dto.response.AccountStockResponse;
import com.pedromolon.agregadordeinvestimentos.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public StockService(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    public void createStock(StockRequest request) {
        var stock = stockMapper.toEntity(request);
        stockRepository.save(stock);
    }

    public Page<Stock> getAllStocks(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

}
