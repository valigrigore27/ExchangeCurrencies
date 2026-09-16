package com.example.exchangecurrencies.exchange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ExchangeIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("exchange_currencies")
            .withUsername("exchange")
            .withPassword("exchange");

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldConvertUsingHttpEndpointAndRealDatabase() throws Exception {
        mockMvc.perform(post("/exchange/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sourceCurrency": "EUR",
                                  "targetCurrency": "USD",
                                  "amount": 100
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.convertedAmount").value(116.06));
    }

    @Test
    void shouldReturnBadRequestForMissingRequiredAmount() throws Exception {
        mockMvc.perform(post("/exchange/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sourceCurrency": "EUR",
                                  "targetCurrency": "USD"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
