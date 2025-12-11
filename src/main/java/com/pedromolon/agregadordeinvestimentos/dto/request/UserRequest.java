package com.pedromolon.agregadordeinvestimentos.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotNull(message = "User cannot be null")
        String username,

        @NotNull(message = "Email cannot be null")
        @Email(message = "Email must be valid")
        String email,

        @NotNull(message = "Password cannot be null")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {}
