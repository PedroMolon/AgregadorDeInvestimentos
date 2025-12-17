package com.pedromolon.agregadordeinvestimentos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AddStockToAccountRequest(
        @NotBlank(message = "Stock ID cannot be blank")
        @Size(max = 32, message = "Stock ID cannot be longer than 32 characters")
        String stockId,

        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be a positive integer")
        Integer quantity
) {}
