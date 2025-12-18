package com.pedromolon.agregadordeinvestimentos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customopenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Agregador de Investimentos")
                        .description("API para gerenciamento de investimentos")
                        .version("1.0"));
    }

}
