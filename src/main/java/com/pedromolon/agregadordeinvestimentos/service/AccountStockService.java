package com.pedromolon.agregadordeinvestimentos.service;

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
import com.pedromolon.agregadordeinvestimentos.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AccountStockService {

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final UserRepository userRepository;

    public AccountStockService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.userRepository = userRepository;
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

        return new AccountStockResponse(stock.getStockId(), stock.getDescription(), accountStock.getQuantity());
    }

    public List<AccountStockResponse> getAllStocks(Long userId, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .filter(a -> a.getUser().getId().equals(userId))
                .orElseThrow(() -> new AccountNotFoundException("Account not found for this user"));

        return accountStockRepository.findAll()
                .stream()
                .map(accountStock -> {
                    Stock stock = accountStock.getStock();
                    return new AccountStockResponse(stock.getStockId(), stock.getDescription(), accountStock.getQuantity());
                })
                .toList();
    }

}
