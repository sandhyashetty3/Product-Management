package com.shop.product_management.service;

import com.shop.product_management.dto.CategoryResponseDTO;
import com.shop.product_management.dto.CategoryRequestDTO;
import com.shop.product_management.dto.ProductResponseDTO;
import com.shop.product_management.entity.Category;
import com.shop.product_management.exceptionhandler.ResourceNotFoundException;
import com.shop.product_management.mapper.CommonMapper;
import com.shop.product_management.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CommonMapper commonMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           CommonMapper commonMapper) {
        this.categoryRepository = categoryRepository;
        this.commonMapper = commonMapper;
   }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = commonMapper.toCategory(categoryRequestDTO);
        if(categoryRequestDTO.getParentCategoryId()!=null){
            Category parentCategory = categoryRepository.findById(categoryRequestDTO.getParentCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            category.setParentCategory(parentCategory);
        }
        return commonMapper.fromCategory(categoryRepository.save(category));

    }

    public List<CategoryResponseDTO> getAllCategories() {

        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNull();
        return rootCategories.stream().map(this::convertToCategoryDTO).collect(Collectors.toList());
    }

    private CategoryResponseDTO convertToCategoryDTO(Category category) {
            CategoryResponseDTO categoryDTO = CategoryResponseDTO.builder().id(category.getId())
                                     .name(category.getName()).build();

            // Convert products to ProductResponseDTOs

            if (Optional.of(category).map(Category::getProducts).isPresent()) {
                List<ProductResponseDTO> productResponseDTOS = category.getProducts().stream()
                        .map(commonMapper::fromProduct)
                        .collect(Collectors.toList());
                categoryDTO.setProducts(productResponseDTOS);
            }

            // Convert subcategories to CategoryResponseDTOs recursively
            if (Optional.of(category).map(Category::getSubCategories).isPresent()) {
                List<CategoryResponseDTO> subcategoryDTOs = category.getSubCategories().stream()
                        .map(this::convertToCategoryDTO)
                        .collect(Collectors.toList());
                categoryDTO.setSubCategories(subcategoryDTOs);
            }

            return categoryDTO;
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryUpdateDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(categoryUpdateDTO.getName());

        if (categoryUpdateDTO.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryUpdateDTO.getParentCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            category.setParentCategory(parentCategory);
        } else {
            // this is based on the requirement.
            category.setParentCategory(null);
        }
        return commonMapper.fromCategory(categoryRepository.save(category));

    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category =  categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return commonMapper.fromCategory(category);
    }
}
