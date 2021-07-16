package com.stan.springboottrading.repository;

import com.stan.springboottrading.dataobject.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest(){
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional
    public void saveTest(){
//        Optional<ProductCategory> productCategory = productCategoryRepository.findById(1);
//        ProductCategory productCategory1 = productCategory.get();
//        productCategory1.setCategoryType(2);
        ProductCategory productCategory = new ProductCategory("女生最爱的",6);
        ProductCategory result = productCategoryRepository.save(productCategory);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeIn(){
        List<Integer> list = Arrays.asList(2,3,4,5);
        List<ProductCategory> reulst = productCategoryRepository.findByCategoryTypeIn(list);
        Assertions.assertNotEquals(0,reulst.size());
    }
}