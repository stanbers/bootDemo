package com.stan.springboottrading.service.impl;

import com.stan.springboottrading.dataobject.ProductInfo;
import com.stan.springboottrading.dto.CartDTO;
import com.stan.springboottrading.enums.ProductStatusEnum;
import com.stan.springboottrading.enums.ResultEnum;
import com.stan.springboottrading.exception.SellException;
import com.stan.springboottrading.repository.ProductInfoRepository;
import com.stan.springboottrading.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public Optional<ProductInfo> findById(String productId) {
        return productInfoRepository.findById(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDto : cartDTOList) {
            Optional<ProductInfo> productInfo = productInfoRepository.findById(cartDto.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODDUCT_NOT_EXSIT);
            }
            Integer result = productInfo.get().getProductStock() - cartDto.getProductQuantity();
            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_INVENTORY_ERROR);
            }
            productInfo.get().setProductStock(result);
            productInfoRepository.save(productInfo.get());
        }
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

    }
}
