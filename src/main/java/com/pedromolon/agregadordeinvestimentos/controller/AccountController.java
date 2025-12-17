package com.pedromolon.agregadordeinvestimentos.controller;

import com.pedromolon.agregadordeinvestimentos.dto.request.AccountRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.AccountResponse;
import com.pedromolon.agregadordeinvestimentos.security.JWTUserData;
import com.pedromolon.agregadordeinvestimentos.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AccountResponse>> getAllAccountFromUser(
            @AuthenticationPrincipal JWTUserData user
    ) {
        Long userId = user.userId();

        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccountByUser(userId));
    }

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccountResponse> createAccount(
            @AuthenticationPrincipal JWTUserData user,
            @RequestBody @Valid AccountRequest request
    ) {
        Long userId = user.userId();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                accountService.createAccount(userId, request)
        );
    }

}
