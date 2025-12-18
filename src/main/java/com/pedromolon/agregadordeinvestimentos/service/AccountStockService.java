package com.pedromolon.agregadordeinvestimentos.service;

import com.pedromolon.agregadordeinvestimentos.client.BrapiClient;
import com.pedromolon.agregadordeinvestimentos.client.dto.response.BrapiResponse;
import com.pedromolon.agregadordeinvestimentos.dto.request.AddStockToAccountRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.AccountStockResponse;
import com.pedromolon.agregadordeinvestimentos.entity.Account;
import com.pedromolon.agregadordeinvestimentos.entity.AccountStock;
import com.pedromolon.agregadordeinvestimentos.entity.AccountStockId;
import com.pedromolon.agregadordeinvestimentos.entity.Stock;
import com.pedromolon.agregadordeinvestimentos.exceptions.AccountNotFoundException;
import com.pedromolon.agregadordeinvestimentos.exceptions.StockNotFoundException;
import com.pedromolon.agregadordeinvestimentos.repository.AccountRepository;
import com.pedromolon.agregadordeinvestimentos.repository.AccountStockRepository;
import com.pedromolon.agregadordeinvestimentos.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import com.pedromolon.agregadordeinvestimentos.client.dto.response.StockResponse;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountStockService {

    @Value("${brapi.token}")
    private String token;

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;

    public AccountStockService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }

    public AccountStockResponse addStockToAccount(Long userId, Long accountId, AddStockToAccountRequest request) {
        Account account = accountRepository.findById(accountId)
                .filter(a -> a.getUser().getId().equals(userId))
                .orElseThrow(() -> new AccountNotFoundException("Account not found for this user"));

        Stock stock = stockRepository.findById(request.stockId())
                .orElseThrow(() -> new StockNotFoundException("Stock not found with id: " + request.stockId()));

        AccountStockId id = new AccountStockId(account.getAccountId(), stock.getStockId());
        AccountStock accountStock = accountStockRepository.findById(id)
                .orElseGet(() -> {
                    AccountStock novo = new AccountStock();
                    novo.setId(id);
                    novo.setAccount(account);
                    novo.setStock(stock);
                    return novo;
                });

        accountStock.setQuantity(request.quantity());
        accountStockRepository.save(accountStock);

        return getAccountStockResponse(stock, accountStock);
    }

    private AccountStockResponse getAccountStockResponse(Stock stock, AccountStock accountStock) {
        try {
            var brapiResponse = getQuote(stock.getStockId());
            var total = brapiResponse.results().getFirst().regularMarketPrice() * accountStock.getQuantity();
            return new AccountStockResponse(stock.getStockId(), stock.getDescription(), accountStock.getQuantity(), total);
        } catch (Exception e) {
            return new AccountStockResponse(stock.getStockId(), stock.getDescription(), accountStock.getQuantity(), 0.0);
        }
    }

    @Cacheable(value = "stockQuote", key = "#stockId")
    public BrapiResponse getQuote(String stockId) {
        return brapiClient.getQuote(token, stockId);
    }

    public Page<AccountStockResponse> getAllStocks(Long userId, Long accountId, Pageable pageable) {
        Account account = accountRepository.findById(accountId)
                .filter(a -> a.getUser().getId().equals(userId))
                .orElseThrow(() -> new AccountNotFoundException("Account not found for this user"));

        Page<AccountStock> accountStocks = accountStockRepository.findAllByAccount(account, pageable);

        if (accountStocks.isEmpty()) {
            return Page.empty();
        }

        String tickers = accountStocks.stream()
                .map(as -> as.getStock().getStockId())
                .collect(Collectors.joining(","));

        Map<String, Double> prices = Map.of();
        try {
            BrapiResponse brapiResponse = getQuote(tickers);
            prices = brapiResponse.results().stream()
                    .collect(Collectors.toMap(StockResponse::symbol, StockResponse::regularMarketPrice, (v1, v2) -> v1));
        } catch (Exception e) {
            // Log error if needed
        }

        Map<String, Double> finalPrices = prices;
        return accountStocks.map(as -> {
            Double price = finalPrices.getOrDefault(as.getStock().getStockId(), 0.0);
            double total = price * as.getQuantity();
            return new AccountStockResponse(
                    as.getStock().getStockId(),
                    as.getStock().getDescription(),
                    as.getQuantity(),
                    total
            );
        });
    }

}
