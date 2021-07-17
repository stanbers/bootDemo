package com.stan.springboottrading.service;

import com.stan.springboottrading.dto.OrderDTO;

public interface BuyerService {

    //query a order
    OrderDTO findOneOrder(String openid, String orderId);

    //cancel order
    OrderDTO cancelOrder(String openid, String orderId);
}
