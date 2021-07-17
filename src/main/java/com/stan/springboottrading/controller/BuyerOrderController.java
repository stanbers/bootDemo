package com.stan.springboottrading.controller;

import com.stan.springboottrading.VO.ResultVO;
import com.stan.springboottrading.convertor.OrderForm2OrderDTOConvertor;
import com.stan.springboottrading.dto.OrderDTO;
import com.stan.springboottrading.enums.ResultEnum;
import com.stan.springboottrading.exception.SellException;
import com.stan.springboottrading.form.OrderForm;
import com.stan.springboottrading.service.BuyerService;
import com.stan.springboottrading.service.OrderService;
import com.stan.springboottrading.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Resource
    private BuyerService buyerService;

    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("order creation, parameters are incorrect, orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConvertor.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("cart is empty");
            throw new SellException(ResultEnum.CART_IS_EMPTY);
        }
         OrderDTO orderDTO1 = orderService.create(orderDTO);
        Map<String,String> map =  new HashMap<>();
        map.put("orderId",orderDTO1.getOrderId());

        return ResultVoUtil.success(map);
    }

    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value="size",defaultValue = "10") Integer size){
        if(!StringUtils.hasLength(openid)){
            log.error("open id cannot be empty");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,pageable);
        return ResultVoUtil.success(orderDTOPage.getContent());
    }

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        //TODO not security
//        OrderDTO orderDTO = orderService.findOne(orderId);
//        return ResultVoUtil.success(orderDTO);
        OrderDTO orderDTO = buyerService.findOneOrder(openid,orderId);
        return ResultVoUtil.success(orderDTO);
    }

    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        //TODO not security
//        OrderDTO orderDTO = orderService.findOne(orderId);
//        orderService.cancel(orderDTO);
        OrderDTO orderDTO = buyerService.cancelOrder(openid,orderId);
        return ResultVoUtil.success();
    }
}
