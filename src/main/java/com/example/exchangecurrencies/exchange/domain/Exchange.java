package com.example.exchangecurrencies.exchange.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "exchange")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_currency", nullable = false, length = 5)
    private String sourceCurrency;

    @Column(name = "target_currency", nullable = false, length = 5)
    private String targetCurrency;

    @Column(nullable = false, precision = 12, scale = 6)
    private BigDecimal rate;
}
