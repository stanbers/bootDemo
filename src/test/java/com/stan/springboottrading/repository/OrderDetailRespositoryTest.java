package com.stan.springboottrading.repository;

import com.stan.springboottrading.dataobject.OrderDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDetailRespositoryTest {

    @Autowired
    private OrderDetailRespository orderDetailRespository;

    @Test
    void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("134563");
        orderDetail.setOrderId("111333");
        orderDetail.setProductIcon("Http:xxxx.jpg");
        orderDetail.setProductId("12121");
        orderDetail.setProductName("Mac Air");
        orderDetail.setProductPrice(new BigDecimal(9000.9));
        orderDetail.setProductQuantity(1000);

        OrderDetail orderDetail1 = orderDetailRespository.save(orderDetail);
        Assertions.assertNotNull(orderDetail1);
    }

    @Test
    void findByOrderId() {
        List<OrderDetail> result =  orderDetailRespository.findByOrderId("13456");
        Assertions.assertNotNull(result.size());
    }
}