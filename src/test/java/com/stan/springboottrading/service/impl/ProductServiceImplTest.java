package com.stan.springboottrading.service.impl;

import com.stan.springboottrading.dataobject.ProductInfo;
import com.stan.springboottrading.enums.ProductStatusEnum;
import com.stan.springboottrading.repository.ProductInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Test
    void findById() {
        Optional<ProductInfo> productInfo = productServiceImpl.findById("1234");

        Assertions.assertEquals("1234",productInfo.get().getProductId());
    }

    @Test
    void findUpAll() {
        List<ProductInfo> productInfoList = productServiceImpl.findUpAll();
        Assertions.assertNotNull(productInfoList.size());
    }

    @Test
    void findAll() {

        PageRequest pageRequest = PageRequest.of(0,1);
        Page<ProductInfo> productInfoPage = productServiceImpl.findAll(pageRequest);
        System.out.println(productInfoPage.getTotalElements());
        Assertions.assertNotNull(productInfoPage);
    }

    @Test
    void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12345");
        productInfo.setProductName("huawei");
        productInfo.setProductPrice(new BigDecimal(6000.1));
        productInfo.setProductStock(1000);
        productInfo.setProductDescription("proud of HuaWei !");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result = productServiceImpl.save(productInfo);
        Assertions.assertNotNull(result);
    }
}