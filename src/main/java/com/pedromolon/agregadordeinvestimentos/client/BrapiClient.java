package com.pedromolon.agregadordeinvestimentos.client;

import com.pedromolon.agregadordeinvestimentos.client.dto.response.BrapiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "BrapiClient",
        url = "https://brapi.dev"
)
public interface BrapiClient {

    @GetMapping("/api/quote/{stockId}")
    public BrapiResponse getQuote(
            @RequestParam("token") String token,
            @PathVariable String stockId
    );

}
