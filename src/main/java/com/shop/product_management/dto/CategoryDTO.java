package com.shop.product_management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
public class CategoryDTO {

    private Long id;
    private String name;
    private CategoryDTO parentCategory;
}
