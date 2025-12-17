package com.pedromolon.agregadordeinvestimentos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StockRequest(
        @NotBlank(message = "Stock ID cannot be blank")
        @Size(max = 32, message = "Stock ID cannot be longer than 32 characters")
        @Pattern(regexp = "^[A-Z]{4}\\d{1,2}$", message = "Stock ID must only contain letters and numbers")
        String stockId,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 255, message = "Description cannot be longer than 255 characters")
        String description
) {}
