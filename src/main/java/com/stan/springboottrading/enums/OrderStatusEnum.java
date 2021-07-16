package com.stan.springboottrading.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0,"new order"),
    FINISHED(1,"completed"),
    CANCEL(2,"cancelled"),
    ;
    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
