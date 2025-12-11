package com.pedromolon.agregadordeinvestimentos.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "Email cannot be empty")
        String email,

        @NotEmpty(message = "Password cannot be empty")
        String password
) {
}
