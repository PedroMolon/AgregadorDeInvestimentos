package com.pedromolon.agregadordeinvestimentos.client.dto.response;

import java.util.List;

public record BrapiResponse(
        List<StockResponse> results
) {}
