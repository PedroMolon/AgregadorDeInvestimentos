package com.pedromolon.agregadordeinvestimentos.client;

import com.pedromolon.agregadordeinvestimentos.exceptions.BrapiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class BrapiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new BrapiException("Requisição inválida para a Brapi.");
            case 401 -> new BrapiException("Token da Brapi inválido ou expirado.");
            case 404 -> new BrapiException("Ação não encontrada na base da Brapi.");
            case 500 -> new BrapiException("Erro interno no servidor da Brapi.");
            default -> new BrapiException("Erro inesperado ao consultar a Brapi: " + response.reason());
        };
    }
}
