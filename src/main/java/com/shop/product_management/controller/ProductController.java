package com.shop.product_management.controller;

import com.shop.product_management.dto.ProductRequestDTO;
import com.shop.product_management.dto.ProductResponseDTO;
import com.shop.product_management.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.saveProduct(productRequestDTO);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.updateProduct(id,productRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/convert")
    public BigDecimal convertPrice(@RequestParam @NotNull BigDecimal price,
                                   @RequestParam @NotBlank String fromCurrency,
                                   @RequestParam @NotBlank String toCurrency) {
        return productService.convertPrice(price, fromCurrency, toCurrency);
    }
}
