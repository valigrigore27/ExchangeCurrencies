package com.example.exchangecurrencies.exchange.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 400
    @ExceptionHandler(NegativeAmountException.class)
    public ResponseEntity<ApiError> handleNotFound(
            NegativeAmountException ex,
            HttpServletRequest req) {
        logWarn(ex, req, HttpStatus.BAD_REQUEST);
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    // 400
    @ExceptionHandler(AmountEqualToZeroException.class)
    public ResponseEntity<ApiError> handleNotFound(
            AmountEqualToZeroException ex,
            HttpServletRequest req) {
        logWarn(ex, req, HttpStatus.BAD_REQUEST);
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleNotFound(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {
        logWarn(ex, req, HttpStatus.BAD_REQUEST);
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    // 404
    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            CurrencyNotFoundException ex,
            HttpServletRequest req) {
        logWarn(ex, req, HttpStatus.NOT_FOUND);
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message, String path) {
        ApiError body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path);
        return ResponseEntity.status(status).body(body);
    }

    private void logWarn(Exception ex, HttpServletRequest req, HttpStatus status) {
        log.warn("Request failed: status={}, path={}, message={}", status, req.getRequestURI(), ex.getMessage());
    }
}
