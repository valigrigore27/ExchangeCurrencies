package com.example.exchangecurrencies.exchange.controller;

import com.example.exchangecurrencies.exchange.domain.Exchange;
import com.example.exchangecurrencies.exchange.dto.ExchangeRequest;
import com.example.exchangecurrencies.exchange.dto.ExchangeResponse;
import com.example.exchangecurrencies.exchange.service.ExchangeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private final ExchangeServiceImpl exchangeServiceImpl;

    public ExchangeController(ExchangeServiceImpl exchangeServiceImpl) {
        this.exchangeServiceImpl = exchangeServiceImpl;
    }

    @PostMapping("/currencies")
    public ExchangeResponse exchangeCurrency(@Valid @RequestBody ExchangeRequest exchangeRequest) {
        return exchangeServiceImpl.exchangeCurrency(exchangeRequest);
    }

}
