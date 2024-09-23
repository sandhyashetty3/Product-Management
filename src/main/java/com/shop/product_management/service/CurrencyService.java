package com.shop.product_management.service;
import com.shop.product_management.dto.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate;

    @Value("${fixer.api.url}")
    private String fixerApiUrl;

    @Value("${fixer.api.key}")
    private String fixerApiKey;

    @Autowired
    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        String url = String.format("%s?access_key=%s", fixerApiUrl, fixerApiKey);
        CurrencyExchangeResponse response = restTemplate.getForObject(url, CurrencyExchangeResponse.class);

        if (response != null && response.getRates().containsKey(fromCurrency)) {
            BigDecimal rate = response.getRates().get(fromCurrency);
            return amount.divide(rate,2, RoundingMode.UP);
        }
        return amount;
    }
}
