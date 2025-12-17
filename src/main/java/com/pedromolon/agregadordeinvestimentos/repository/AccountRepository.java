package com.pedromolon.agregadordeinvestimentos.repository;

import com.pedromolon.agregadordeinvestimentos.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
