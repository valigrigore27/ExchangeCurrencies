package com.example.exchangecurrencies.exchange.dto;

import com.example.exchangecurrencies.exchange.domain.Exchange;

import java.math.BigDecimal;

public class ExchangeMapper {

    public static ExchangeResponse toExchangeResponse(BigDecimal result){
        return new ExchangeResponse(result);
    }
}
