package com.anta.shop.repository;

import com.anta.shop.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    /**
     * Search by value
     * @param value Product value
     * @return Currency
     */
    Currency findByValue(String value);
}
