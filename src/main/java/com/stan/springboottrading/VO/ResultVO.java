package com.stan.springboottrading.VO;

import lombok.Data;

/**
 * the outermost object returned by the http request
 * @param <T>
 */
@Data
public class ResultVO<T> {

    //error code
    private Integer code;

    //error or warning message
    private String msg;

    //response data
    private T data;
}
