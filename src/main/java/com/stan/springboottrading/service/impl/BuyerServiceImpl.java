package com.stan.springboottrading.service.impl;

import com.stan.springboottrading.dto.OrderDTO;
import com.stan.springboottrading.enums.ResultEnum;
import com.stan.springboottrading.exception.SellException;
import com.stan.springboottrading.service.BuyerService;
import com.stan.springboottrading.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOneOrder(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid,orderId);
        if (orderDTO == null){
            log.error("order does not exist");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("you're not the order owner");
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
