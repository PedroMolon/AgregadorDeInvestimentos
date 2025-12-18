package com.pedromolon.agregadordeinvestimentos.repository;

import com.pedromolon.agregadordeinvestimentos.entity.Account;
import com.pedromolon.agregadordeinvestimentos.entity.AccountStock;
import com.pedromolon.agregadordeinvestimentos.entity.AccountStockId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
    List<AccountStock> findAllByAccount(Account account);
    Page<AccountStock> findAllByAccount(Account account, Pageable pageable);
}
