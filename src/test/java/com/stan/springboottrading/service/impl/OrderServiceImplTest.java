package com.stan.springboottrading.service.impl;

import com.stan.springboottrading.dataobject.OrderDetail;
import com.stan.springboottrading.dto.OrderDTO;
import com.stan.springboottrading.enums.OrderStatusEnum;
import com.stan.springboottrading.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.AssertionInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "1234321";

    private final String ORDER_ID = "1626456301055888582";

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("Xi'an");
        orderDTO.setBuyerName("Stan");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerPhone("12343211234");
        //cart
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("12345");
        orderDetail.setProductQuantity(2);
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("create order result={}",result);
        Assertions.assertNotNull(result);
    }

    @Test
    void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("query order : result={}",orderDTO);
        Assertions.assertEquals(ORDER_ID,orderDTO.getOrderId());
    }

    @Test
    void findList() {
        Pageable pageable = PageRequest.of(0,1);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,pageable);
        log.info("order list result={}",orderDTOPage.getContent());
        Assertions.assertEquals(2,orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO orderDTO1 = orderService.cancel(orderDTO);
        Assertions.assertEquals(OrderStatusEnum.CANCEL.getCode(),orderDTO1.getOrderStatus());
    }

    @Test
    void finish() {
    }

    @Test
    void paid() {
    }
}