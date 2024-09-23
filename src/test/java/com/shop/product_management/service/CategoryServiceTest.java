package com.shop.product_management.service;

import com.shop.product_management.dto.CategoryRequestDTO;
import com.shop.product_management.dto.CategoryResponseDTO;
import com.shop.product_management.entity.Category;
import com.shop.product_management.exceptionhandler.ResourceNotFoundException;
import com.shop.product_management.mapper.CommonMapper;
import com.shop.product_management.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CommonMapper commonMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryRequestDTO categoryRequestDTO;
    private CategoryResponseDTO categoryResponseDTO;

    @BeforeEach
    public void setup() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("Electronics");

        categoryResponseDTO = CategoryResponseDTO.builder()
                .id(1L)
                .name("Electronics")
                .products(new ArrayList<>())
                .subCategories(new ArrayList<>())
                .build();
    }

    @Test
    public void testCreateCategory() {
        when(commonMapper.toCategory(any(CategoryRequestDTO.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(commonMapper.fromCategory(any(Category.class))).thenReturn(categoryResponseDTO);

        CategoryResponseDTO result = categoryService.createCategory(categoryRequestDTO);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testGetAllCategories() {
        Category subCategory = new Category();
        subCategory.setId(2L);
        subCategory.setName("Smartphones");

        List<Category> categories = List.of(category);
        category.setSubCategories(List.of(subCategory));

        when(categoryRepository.findByParentCategoryIsNull()).thenReturn(categories);
        //when(commonMapper.fromCategory(category)).thenReturn(categoryResponseDTO);

        List<CategoryResponseDTO> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(categoryRepository, times(1)).findByParentCategoryIsNull();
    }

    @Test
    public void testUpdateCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(commonMapper.fromCategory(any(Category.class))).thenReturn(categoryResponseDTO);

        CategoryResponseDTO result = categoryService.updateCategory(1L, categoryRequestDTO);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteCategory(1L);

       verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(commonMapper.fromCategory(any(Category.class))).thenReturn(categoryResponseDTO);

        CategoryResponseDTO result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCategoryByIdThrowsException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
        verify(categoryRepository, times(1)).findById(1L);
    }
}
