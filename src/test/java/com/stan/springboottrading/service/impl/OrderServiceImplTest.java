package com.stan.springboottrading.service.impl;

import com.stan.springboottrading.dataobject.OrderDetail;
import com.stan.springboottrading.dto.OrderDTO;
import com.stan.springboottrading.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "1234321";
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
        orderService.create(orderDTO);

    }

    @Test
    void findOne() {
    }

    @Test
    void findList() {
    }

    @Test
    void cancel() {
    }

    @Test
    void finish() {
    }

    @Test
    void paid() {
    }
}