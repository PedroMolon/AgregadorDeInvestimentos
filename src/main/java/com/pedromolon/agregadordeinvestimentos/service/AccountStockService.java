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
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<AccountStockResponse> getAllStocks(Long userId, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .filter(a -> a.getUser().getId().equals(userId))
                .orElseThrow(() -> new AccountNotFoundException("Account not found for this user"));

        return accountStockRepository.findAll()
                .stream()
                .map(accountStock -> getAccountStockResponse(accountStock.getStock(), accountStock))
                .toList();
    }

}
