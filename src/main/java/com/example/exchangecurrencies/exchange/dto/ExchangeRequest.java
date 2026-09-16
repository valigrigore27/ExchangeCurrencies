package com.example.exchangecurrencies.exchange.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExchangeRequest(
        @NotBlank
        String sourceCurrency,
        @NotBlank
        String targetCurrency,
        @NotNull
        BigDecimal amount
) {
}
