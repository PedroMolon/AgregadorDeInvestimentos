package com.pedromolon.agregadordeinvestimentos.repository;

import com.pedromolon.agregadordeinvestimentos.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
}
