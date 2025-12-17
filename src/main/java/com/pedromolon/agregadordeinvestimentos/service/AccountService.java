package com.pedromolon.agregadordeinvestimentos.service;

import com.pedromolon.agregadordeinvestimentos.dto.request.AccountRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.AccountResponse;
import com.pedromolon.agregadordeinvestimentos.entity.Account;
import com.pedromolon.agregadordeinvestimentos.entity.User;
import com.pedromolon.agregadordeinvestimentos.exceptions.UserNotFoundException;
import com.pedromolon.agregadordeinvestimentos.mapper.AccountMapper;
import com.pedromolon.agregadordeinvestimentos.repository.AccountRepository;
import com.pedromolon.agregadordeinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
    }

    public List<AccountResponse> getAllAccountByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return user.getAccounts()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    public AccountResponse createAccount(Long userId, AccountRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Account account = accountMapper.toEntity(request);
        account.setUser(user);

        user.getAccounts().add(account);
        userRepository.save(user);

        Account saved = accountRepository.save(account);

        return accountMapper.toResponse(saved);
    }

}
