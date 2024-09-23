package com.shop.product_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private List<ProductResponseDTO> products;
    private List<CategoryResponseDTO> subCategories;

}
