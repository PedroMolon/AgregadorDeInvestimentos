package com.pedromolon.agregadordeinvestimentos.controller;

import com.pedromolon.agregadordeinvestimentos.dto.request.StockRequest;
import com.pedromolon.agregadordeinvestimentos.service.StockService;
import jakarta.validation.Valid;
import com.pedromolon.agregadordeinvestimentos.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createStock(@RequestBody @Valid StockRequest request) {
        stockService.createStock(request);
        return ResponseEntity.ok().build();
    }

}
