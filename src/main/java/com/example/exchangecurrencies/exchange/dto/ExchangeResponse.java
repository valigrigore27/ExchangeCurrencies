package com.example.exchangecurrencies.exchange.dto;

import java.math.BigDecimal;

public record ExchangeResponse(
        BigDecimal convertedAmount
){
}
