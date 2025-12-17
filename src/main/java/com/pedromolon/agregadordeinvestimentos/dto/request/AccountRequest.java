package com.pedromolon.agregadordeinvestimentos.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record AccountRequest(
        @NotEmpty(message = "Description cannot be null")
        String description
) {}
