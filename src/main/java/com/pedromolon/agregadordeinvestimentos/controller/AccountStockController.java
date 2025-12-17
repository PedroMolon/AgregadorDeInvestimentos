package com.pedromolon.agregadordeinvestimentos.controller;

import com.pedromolon.agregadordeinvestimentos.dto.request.AddStockToAccountRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.AccountStockResponse;
import com.pedromolon.agregadordeinvestimentos.security.JWTUserData;
import com.pedromolon.agregadordeinvestimentos.service.AccountStockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/accounts")
public class AccountStockController {

    private final AccountStockService accountStockService;

    public AccountStockController(AccountStockService accountStockService) {
        this.accountStockService = accountStockService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<AccountStockResponse> addStock(
            @AuthenticationPrincipal JWTUserData user,
            @PathVariable Long accountId,
            @RequestBody @Valid AddStockToAccountRequest request
            ) {
        var response = accountStockService.addStockToAccount(user.userId(), accountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponse>> getAllStocks(
            @AuthenticationPrincipal JWTUserData user,
            @PathVariable Long accountId
    ) {
        Long userId = user.userId();
        return ResponseEntity.status(HttpStatus.OK).body(accountStockService.getAllStocks(userId, accountId));
    }

}
