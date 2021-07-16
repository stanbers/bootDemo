package com.stan.springboottrading.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {
    WAIT(0,"waiting for pay"),
    SUCCESS(1,"pay successfully"),
    ;

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
