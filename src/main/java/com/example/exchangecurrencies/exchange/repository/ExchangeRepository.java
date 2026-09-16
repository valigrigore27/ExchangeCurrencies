package com.example.exchangecurrencies.exchange.repository;

import com.example.exchangecurrencies.exchange.domain.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    boolean existsBySourceCurrency(String sourceCurrency);
    boolean existsByTargetCurrency(String targetCurrency);

    Exchange findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency);
}
