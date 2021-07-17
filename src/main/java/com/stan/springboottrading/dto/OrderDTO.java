package com.stan.springboottrading.dto;

import com.stan.springboottrading.dataobject.OrderDetail;
import com.stan.springboottrading.enums.OrderStatusEnum;
import com.stan.springboottrading.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String OrderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Integer payStatus;
    private Date createTime;
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
