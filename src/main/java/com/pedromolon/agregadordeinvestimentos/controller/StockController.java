package com.pedromolon.agregadordeinvestimentos.controller;

import com.pedromolon.agregadordeinvestimentos.dto.request.StockRequest;
import com.pedromolon.agregadordeinvestimentos.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody @Valid StockRequest request) {
        stockService.createStock(request);
        return ResponseEntity.ok().build();
    }

}
