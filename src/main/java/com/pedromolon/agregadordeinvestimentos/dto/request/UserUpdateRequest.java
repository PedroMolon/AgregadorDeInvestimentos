package com.pedromolon.agregadordeinvestimentos.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 3, message = "Username must be at least 3 characters")
        String username,

        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email cannot be longer than 255 characters")
        String email,

        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {}
