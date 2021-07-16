package com.stan.springboottrading.utils;

import com.stan.springboottrading.VO.ResultVO;

public class ResultVoUtil {

    public static ResultVO success(Object obj){
        ResultVO resultVO = new ResultVO();

        resultVO.setCode(0);
        resultVO.setMsg("success !");
        resultVO.setData(obj);
        return  resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO fail(Integer erroCode, String errorMsg){
        ResultVO resultVO = new ResultVO();

        resultVO.setCode(erroCode);
        resultVO.setMsg(errorMsg);
        return  resultVO;
    }
}
