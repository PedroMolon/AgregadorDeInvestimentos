package com.pedromolon.agregadordeinvestimentos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AccountRequest(
        @NotBlank(message = "Description cannot be blank")
        @Size(max = 255, message = "Description cannot be longer than 255 characters")
        String description,

        @NotBlank(message = "Street cannot be blank")
        @Size(max = 255, message = "Street cannot be longer than 255 characters")
        String street,

        @NotNull(message = "Number cannot be null")
        @Positive(message = "Number must be positive")
        Integer number
) {}
