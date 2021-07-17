package com.stan.springboottrading.convertor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stan.springboottrading.dataobject.OrderDetail;
import com.stan.springboottrading.dto.OrderDTO;
import com.stan.springboottrading.enums.ResultEnum;
import com.stan.springboottrading.exception.SellException;
import com.stan.springboottrading.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConvertor {

    public static OrderDTO convert(OrderForm orderForm){

        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //convert String to List
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("object convert failed, string={}",orderForm.getItems());
            throw new SellException(ResultEnum.OBJECT_CONVERT_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
