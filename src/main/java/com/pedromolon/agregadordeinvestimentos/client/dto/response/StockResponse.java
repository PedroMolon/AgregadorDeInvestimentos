package com.pedromolon.agregadordeinvestimentos.client.dto.response;

public record StockResponse(
        String symbol,
        double regularMarketPrice
) {}
