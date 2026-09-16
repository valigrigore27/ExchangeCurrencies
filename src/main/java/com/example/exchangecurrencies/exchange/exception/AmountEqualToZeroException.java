package com.example.exchangecurrencies.exchange.exception;

public class AmountEqualToZeroException extends RuntimeException {
    public AmountEqualToZeroException(String message) {
        super(message);
    }
}
