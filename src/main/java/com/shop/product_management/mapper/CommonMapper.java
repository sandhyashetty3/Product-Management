package com.shop.product_management.mapper;

import com.shop.product_management.dto.CategoryRequestDTO;
import com.shop.product_management.dto.CategoryResponseDTO;
import com.shop.product_management.dto.ProductRequestDTO;
import com.shop.product_management.dto.ProductResponseDTO;
import com.shop.product_management.entity.Category;
import com.shop.product_management.entity.Product;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CommonMapper {

     @Mapping(source = "price", target = "priceEur")
     Product toProduct(ProductRequestDTO productRequestDTO);

     @Mapping(source = "priceEur", target = "price")
     ProductResponseDTO fromProduct(Product product);

     Category toCategory(CategoryRequestDTO categoryRequestDTO);
     CategoryResponseDTO fromCategory(Category category);
}
