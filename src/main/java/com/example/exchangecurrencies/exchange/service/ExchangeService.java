package com.example.exchangecurrencies.exchange.service;

import com.example.exchangecurrencies.exchange.dto.ExchangeRequest;
import com.example.exchangecurrencies.exchange.dto.ExchangeResponse;

public interface ExchangeService {

    ExchangeResponse exchangeCurrency(ExchangeRequest exchangeRequest);
}
