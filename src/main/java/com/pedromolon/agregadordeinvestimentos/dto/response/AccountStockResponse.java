package com.pedromolon.agregadordeinvestimentos.dto.response;

import lombok.Builder;

@Builder
public record AccountStockResponse(
        String stockId,
        String description,
        Integer quantity,
        Double total
) {}
