package com.pedromolon.agregadordeinvestimentos.dto.request;

public record AccountRequest(

        String description,

        String street,

        Integer number
) {}
