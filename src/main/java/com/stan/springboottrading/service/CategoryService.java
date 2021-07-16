package com.stan.springboottrading.service;

import com.stan.springboottrading.dataobject.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface CategoryService  {
    Optional<ProductCategory> findById(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> cateGoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
