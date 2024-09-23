package com.shop.product_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private String currency;

    @NotNull
    private Long categoryId;

}
