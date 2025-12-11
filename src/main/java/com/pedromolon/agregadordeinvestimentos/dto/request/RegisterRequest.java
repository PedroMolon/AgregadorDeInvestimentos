package com.pedromolon.agregadordeinvestimentos.dto.request;

import com.pedromolon.agregadordeinvestimentos.entity.enums.Role;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @NotEmpty(message = "Name is required")
        String username,

        @NotEmpty(message = "Email is required")
        String email,

        @NotEmpty(message = "Password is required")
        String password,

        Role role
) {
}
