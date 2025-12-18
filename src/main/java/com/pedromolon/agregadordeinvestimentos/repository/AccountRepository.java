package com.pedromolon.agregadordeinvestimentos.repository;

import com.pedromolon.agregadordeinvestimentos.entity.Account;
import com.pedromolon.agregadordeinvestimentos.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Page<Account> findAllByUser(User user, Pageable pageable);
}
