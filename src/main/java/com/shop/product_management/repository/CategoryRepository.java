package com.shop.product_management.repository;


import com.shop.product_management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
    List<Category> findByParentCategoryIsNull(); // Fetch top-level categories


}
