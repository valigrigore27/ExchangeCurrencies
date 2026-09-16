package com.example.exchangecurrencies.exchange.service;

import com.example.exchangecurrencies.exchange.domain.Exchange;
import com.example.exchangecurrencies.exchange.dto.ExchangeMapper;
import com.example.exchangecurrencies.exchange.dto.ExchangeRequest;
import com.example.exchangecurrencies.exchange.dto.ExchangeResponse;
import com.example.exchangecurrencies.exchange.exception.AmountEqualToZeroException;
import com.example.exchangecurrencies.exchange.exception.CurrencyNotFoundException;
import com.example.exchangecurrencies.exchange.exception.NegativeAmountException;
import com.example.exchangecurrencies.exchange.repository.ExchangeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService{

    private final ExchangeRepository exchangeRepository;

    public ExchangeServiceImpl(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    public ExchangeResponse exchangeCurrency(ExchangeRequest exchangeRequest) {

        final List<String> realCurrencies = new java.util.ArrayList<>(exchangeRepository.findAll().stream()
                .map(Exchange::getTargetCurrency)
                .toList());
        realCurrencies.add("EUR");

        if(!realCurrencies.contains(exchangeRequest.sourceCurrency()) || !realCurrencies.contains(exchangeRequest.targetCurrency())){
            throw new CurrencyNotFoundException("Currency not found");
        }

        if(exchangeRequest.amount().compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountException("Negative amount");
        }

        if(exchangeRequest.amount().compareTo(BigDecimal.ZERO) == 0){
            throw new AmountEqualToZeroException("Amount equal to 0");
        }

        if(exchangeRequest.sourceCurrency().equals("EUR")) {
        Exchange exchange = exchangeRepository.findBySourceCurrencyAndTargetCurrency(exchangeRequest.sourceCurrency(), exchangeRequest.targetCurrency());
        BigDecimal rate = exchange.getRate();
        BigDecimal result = rate.multiply(exchangeRequest.amount());
        return ExchangeMapper.toExchangeResponse(result.setScale(2, RoundingMode.HALF_UP));
        }

        else if(exchangeRequest.targetCurrency().equals("EUR")) {
            Exchange exchange = exchangeRepository.findBySourceCurrencyAndTargetCurrency(exchangeRequest.targetCurrency(), exchangeRequest.sourceCurrency());
            BigDecimal rate = exchange.getRate();
            BigDecimal result = exchangeRequest.amount().divide(rate, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
            return ExchangeMapper.toExchangeResponse(result);
        }
        else {
            Exchange exchangeSourceNotEuro = exchangeRepository.findBySourceCurrencyAndTargetCurrency("EUR", exchangeRequest.sourceCurrency());
            Exchange exchangeTargetNotEuro = exchangeRepository.findBySourceCurrencyAndTargetCurrency("EUR", exchangeRequest.targetCurrency());

            BigDecimal rateEuroToSource = exchangeSourceNotEuro.getRate();
            BigDecimal rateEuroToTarget = exchangeTargetNotEuro.getRate();

            return ExchangeMapper.toExchangeResponse(rateEuroToTarget.divide(rateEuroToSource, RoundingMode.HALF_UP).multiply(exchangeRequest.amount()).setScale(2, RoundingMode.HALF_UP));
        }
    }
}
