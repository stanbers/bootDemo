package com.stan.springboottrading.service.impl;

import com.stan.springboottrading.dataobject.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void findById() {
        Optional<ProductCategory> productCategory = categoryService.findById(2);
        Assertions.assertEquals(new Integer(2),productCategory.get().getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> productCategoryList =categoryService.findAll();
        Assertions.assertNotNull(productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(2,3,4));
        Assertions.assertNotNull(productCategoryList.size());
    }

    @Test
    void save() {
        ProductCategory productCategory = new ProductCategory("大家都爱", 7);
        ProductCategory productCategory1 = categoryService.save(productCategory);
        Assertions.assertNotNull(productCategory1);

    }
}