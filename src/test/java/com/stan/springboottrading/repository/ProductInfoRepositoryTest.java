package com.stan.springboottrading.repository;

import com.stan.springboottrading.dataobject.ProductInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1234");
        productInfo.setProductName("apple");
        productInfo.setProductPrice(new BigDecimal(5000.1));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("run smoothly !");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result = productInfoRepository.save(productInfo);
        Assertions.assertNotNull(result);
    }

    @Test
    void findByProductStatus() {
        List<ProductInfo> result =  productInfoRepository.findByProductStatus(0);
        Assertions.assertNotNull(result);
    }
}