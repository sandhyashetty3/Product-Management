package com.shop.product_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDTO {

    @NotBlank
    private String name;
    private Long parentCategoryId;

}
