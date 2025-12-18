package com.pedromolon.agregadordeinvestimentos.service;

import com.pedromolon.agregadordeinvestimentos.dto.request.AccountRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.AccountResponse;
import com.pedromolon.agregadordeinvestimentos.entity.Account;
import com.pedromolon.agregadordeinvestimentos.entity.BillingAddress;
import com.pedromolon.agregadordeinvestimentos.entity.User;
import com.pedromolon.agregadordeinvestimentos.exceptions.UserNotFoundException;
import com.pedromolon.agregadordeinvestimentos.mapper.AccountMapper;
import com.pedromolon.agregadordeinvestimentos.repository.AccountRepository;
import com.pedromolon.agregadordeinvestimentos.repository.BillingAddressRepository;
import com.pedromolon.agregadordeinvestimentos.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BillingAddressRepository billingAddressRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, BillingAddressRepository billingAddressRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.accountMapper = accountMapper;
    }

    @Transactional
    public AccountResponse createAccount(Long userId, AccountRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Account account = accountMapper.toEntity(request);
        account.setUser(user);
        accountRepository.save(account);

        BillingAddress address = new BillingAddress();
        address.setAccount(account);
        address.setStreet(request.street());
        address.setNumber(request.number());
        billingAddressRepository.save(address);

        account.setBillingAddress(address);

        user.getAccounts().add(account);

        return accountMapper.toResponse(account);
    }

    @Transactional
    public Page<AccountResponse> getAllAccounts(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return accountRepository.findAllByUser(user, pageable)
                .map(accountMapper::toResponse);
    }

}
