package com.shop.product_management.validation;

import com.shop.product_management.dto.ProductRequestDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class BusinessValidationService {

    public void validateProductInfo(ProductRequestDTO productRequestDTO) {
        if (productRequestDTO!=null && productRequestDTO.getCategoryId() == null) {
            throw new ValidationException("Category ID is mandatory.");
        }
    }
}
