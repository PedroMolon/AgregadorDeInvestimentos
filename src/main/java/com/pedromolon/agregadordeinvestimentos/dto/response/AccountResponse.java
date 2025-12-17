package com.pedromolon.agregadordeinvestimentos.dto.response;

import lombok.Builder;

@Builder
public record AccountResponse(
        Long accountId,
        String description,
        String username,
        String street,
        Integer number
) {}
