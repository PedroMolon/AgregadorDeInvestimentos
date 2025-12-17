package com.pedromolon.agregadordeinvestimentos.repository;

import com.pedromolon.agregadordeinvestimentos.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
