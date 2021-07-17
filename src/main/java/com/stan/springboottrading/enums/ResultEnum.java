package com.stan.springboottrading.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODDUCT_NOT_EXSIT(10,"product does not exist"),
    PRODUCT_INVENTORY_ERROR(11,"inventory shortage"),
    ORDER_NOT_EXIST(12,"order does not exist"),
    ORDER_DETAIL_NOT_NOT_EXIST(13,"order detail does not exist"),
    ORDER_STATUS_INCORRECT(14,"order status is not correct"),
    ORDER_UPDATE_FAIL(15,"update order status failed"),
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
