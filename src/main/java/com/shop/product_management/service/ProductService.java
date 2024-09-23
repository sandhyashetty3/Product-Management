package com.shop.product_management.service;

import com.shop.product_management.dto.ProductRequestDTO;
import com.shop.product_management.dto.ProductResponseDTO;
import com.shop.product_management.entity.Category;
import com.shop.product_management.entity.Product;
import com.shop.product_management.exceptionhandler.ResourceNotFoundException;
import com.shop.product_management.mapper.CommonMapper;
import com.shop.product_management.repository.CategoryRepository;
import com.shop.product_management.repository.ProductRepository;
import com.shop.product_management.validation.BusinessValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    public static final String EUR = "EUR";
    private final ProductRepository productRepository;
    private final CurrencyService currencyService;
    private final CategoryRepository categoryRepository;
    private final CommonMapper commonMapper;
    private final BusinessValidationService businessValidationService;

    @Autowired
    public ProductService(ProductRepository productRepository, CurrencyService currencyService,
                          CategoryRepository categoryRepository,
                          CommonMapper commonMapper,BusinessValidationService businessValidationService) {
        this.productRepository = productRepository;
        this.currencyService = currencyService;
        this.categoryRepository = categoryRepository;
        this.commonMapper = commonMapper;
        this.businessValidationService = businessValidationService;
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products =  productRepository.findAll();
        return products.stream().map(commonMapper::fromProduct).toList();
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return commonMapper.fromProduct(product);
    }

    public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO) {
        Product product = commonMapper.toProduct(productRequestDTO);

        businessValidationService.validateProductInfo(productRequestDTO);
        product.setCategory(categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found")));

        boolean isCurrencyNotEUR = IsCurrencyNotEuro(productRequestDTO);

        if (isCurrencyNotEUR) {
            BigDecimal priceInEur = currencyService.convertCurrency(productRequestDTO.getPrice(), productRequestDTO.getCurrency(), EUR);
            product.setPriceEur(priceInEur);
        }
        return commonMapper.fromProduct(productRepository.save(product));
    }

    private static Boolean IsCurrencyNotEuro(ProductRequestDTO productRequestDTO) {
        return Optional.ofNullable(productRequestDTO).map(ProductRequestDTO::getCurrency)
                .map(currency -> !EUR.equals(currency))
                .orElse(false);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public BigDecimal convertPrice(BigDecimal price, String fromCurrency, String toCurrency) {
        return currencyService.convertCurrency(price, fromCurrency, toCurrency);
    }

    public ProductResponseDTO updateProduct(Long id,ProductRequestDTO productRequestDTO) {

        businessValidationService.validateProductInfo(productRequestDTO);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Optional.ofNullable(productRequestDTO.getName())
                .filter(name -> !name.equals(product.getName()))
                .ifPresent(product::setName);
        Optional.ofNullable(productRequestDTO.getDescription())
                 .filter(desc -> !desc.equals(product.getDescription()))
                         .ifPresent(product::setDescription);

        boolean isCategoryIdDifferent = Optional.ofNullable(product.getCategory())
                .map(Category::getId)
                .map(catId -> !catId.equals(productRequestDTO.getCategoryId()))
                .orElse(true);

        if(isCategoryIdDifferent) {
            product.setCategory(categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        }

        Optional.ofNullable(productRequestDTO.getPrice())
                .filter(price -> !price.equals(product.getPriceEur()))
                .ifPresent(price->{
                    boolean isCurrencyNotEUR = IsCurrencyNotEuro(productRequestDTO);
                    BigDecimal priceInEur = null;
                    if(isCurrencyNotEUR){
                        priceInEur = currencyService.convertCurrency(price, productRequestDTO.getCurrency(), EUR);
                    }
                    product.setPriceEur(Optional.ofNullable(priceInEur).orElse(productRequestDTO.getPrice()));
                 });
       return commonMapper.fromProduct(productRepository.save(product));
    }
}
