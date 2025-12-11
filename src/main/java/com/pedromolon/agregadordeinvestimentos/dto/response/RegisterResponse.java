package com.pedromolon.agregadordeinvestimentos.dto.response;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String username,
        String email
) {}
