package com.stan.springboottrading.repository;

import com.stan.springboottrading.dataobject.OrderMaster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMasterResposityTest {

    @Autowired
    private OrderMasterResposity orderMasterResposity;

    private final String OPENID = "123321";
    @Test
    void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234");
        orderMaster.setBuyerName("Stan");
        orderMaster.setBuyerPhone("12332322232");
        orderMaster.setBuyerAddress("Xi'an");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(20.2));

        OrderMaster orderMaster1 = orderMasterResposity.save(orderMaster);
        Assertions.assertNotNull(orderMaster1);
    }


    @Test
    void findByBuyerOpenid() {
        PageRequest pageRequest = PageRequest.of(0,3);
        Page<OrderMaster> result = orderMasterResposity.findByBuyerOpenid(OPENID,pageRequest);
        System.out.println(result.getTotalElements());
        Assertions.assertEquals(2,result.getContent().size());
    }
}