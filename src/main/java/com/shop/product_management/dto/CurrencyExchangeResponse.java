package com.shop.product_management.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyExchangeResponse {
    private String base;
    private Map<String, BigDecimal> rates;
}
