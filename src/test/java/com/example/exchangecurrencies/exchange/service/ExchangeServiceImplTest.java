package com.example.exchangecurrencies.exchange.service;

import com.example.exchangecurrencies.exchange.domain.Exchange;
import com.example.exchangecurrencies.exchange.dto.ExchangeRequest;
import com.example.exchangecurrencies.exchange.dto.ExchangeResponse;
import com.example.exchangecurrencies.exchange.exception.CurrencyNotFoundException;
import com.example.exchangecurrencies.exchange.exception.NegativeAmountException;
import com.example.exchangecurrencies.exchange.repository.ExchangeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceImplTest {

    @Mock
    private ExchangeRepository exchangeRepository;

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    private Exchange eurToUsd() {
        return Exchange.builder()
                .sourceCurrency("EUR")
                .targetCurrency("USD")
                .rate(new BigDecimal("1.160630"))
                .build();
    }

    @Test
    void shouldConvertFromEuroToTargetCurrency() {
        Exchange eurToUsd = eurToUsd();

        when(exchangeRepository.findAll()).thenReturn(java.util.List.of(eurToUsd));
        when(exchangeRepository.findBySourceCurrencyAndTargetCurrency("EUR", "USD"))
                .thenReturn(eurToUsd);

        ExchangeResponse response = exchangeService.exchangeCurrency(
                new ExchangeRequest("EUR", "USD", new BigDecimal("100")));

        assertThat(response.convertedAmount()).isEqualByComparingTo("116.06");
    }

    @Test
    void shouldRejectUnknownCurrency() {
        Exchange eurToUsd = eurToUsd();

        when(exchangeRepository.findAll()).thenReturn(java.util.List.of(eurToUsd));

        assertThatThrownBy(() -> exchangeService.exchangeCurrency(
                new ExchangeRequest("EUR", "XXX", new BigDecimal("100"))))
                .isInstanceOf(CurrencyNotFoundException.class)
                .hasMessage("Currency not found");
    }

    @Test
    void shouldRejectNegativeAmount() {
        Exchange eurToUsd = eurToUsd();

        when(exchangeRepository.findAll()).thenReturn(java.util.List.of(eurToUsd));

        assertThatThrownBy(() -> exchangeService.exchangeCurrency(
                new ExchangeRequest("EUR", "USD", new BigDecimal("-100"))
        )).isInstanceOf(NegativeAmountException.class)
                .hasMessage("Negative amount");
    }
}
