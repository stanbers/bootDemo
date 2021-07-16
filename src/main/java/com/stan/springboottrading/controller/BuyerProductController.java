package com.stan.springboottrading.controller;

import com.stan.springboottrading.VO.ProductInfoVO;
import com.stan.springboottrading.VO.ProductVO;
import com.stan.springboottrading.VO.ResultVO;
import com.stan.springboottrading.dataobject.ProductCategory;
import com.stan.springboottrading.dataobject.ProductInfo;
import com.stan.springboottrading.service.CategoryService;
import com.stan.springboottrading.service.ProductService;
import com.stan.springboottrading.utils.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){

        //query products
        List<ProductInfo> productInfoList = productService.findUpAll();

        //query product categories, it should be came from product info
        List<Integer> categoryTypeList = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //data splicing
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO =  new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

//        ResultVO resultVO = new ResultVO();
//        resultVO.setCode(0);
//        resultVO.setMsg("success");

//        ProductVO productVO = new ProductVO();
//        ProductInfoVO productInfoVO =  new ProductInfoVO();
//        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
//        resultVO.setData(productVOList);

        return ResultVoUtil.success(productVOList);
    }
}
