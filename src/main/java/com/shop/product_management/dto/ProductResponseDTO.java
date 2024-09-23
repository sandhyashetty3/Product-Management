package com.shop.product_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.product_management.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@Builder
public class ProductResponseDTO {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private CategoryDTO category;

}
