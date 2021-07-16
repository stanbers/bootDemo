package com.stan.springboottrading.service.impl;

import com.stan.springboottrading.dataobject.ProductCategory;
import com.stan.springboottrading.repository.ProductCategoryRepository;
import com.stan.springboottrading.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public Optional<ProductCategory> findById(Integer categoryId) {
        return repository.findById(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> cateGoryTypeList) {
        return repository.findByCategoryTypeIn(cateGoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
