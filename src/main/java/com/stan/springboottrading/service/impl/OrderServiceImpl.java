package com.stan.springboottrading.service.impl;

import com.stan.springboottrading.convertor.OrderMaster2OrderDTOConvertor;
import com.stan.springboottrading.dataobject.OrderDetail;
import com.stan.springboottrading.dataobject.OrderMaster;
import com.stan.springboottrading.dataobject.ProductInfo;
import com.stan.springboottrading.dto.CartDTO;
import com.stan.springboottrading.dto.OrderDTO;
import com.stan.springboottrading.enums.OrderStatusEnum;
import com.stan.springboottrading.enums.PayStatusEnum;
import com.stan.springboottrading.enums.ResultEnum;
import com.stan.springboottrading.exception.SellException;
import com.stan.springboottrading.repository.OrderDetailRespository;
import com.stan.springboottrading.repository.OrderMasterResposity;
import com.stan.springboottrading.service.OrderService;
import com.stan.springboottrading.service.ProductService;
import com.stan.springboottrading.utils.KeyUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRespository orderDetailRespository;

    @Autowired
    private OrderMasterResposity orderMasterResposity;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        //init order amount
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        String orderId = KeyUtil.genUniqueKey();

        //query product that you buy:
       for(OrderDetail orderDetail: orderDTO.getOrderDetailList()){
           Optional<ProductInfo> productInfo = productService.findById(orderDetail.getProductId());
           if(productInfo == null){
                throw new SellException(ResultEnum.PRODDUCT_NOT_EXSIT);
           }
           //calculate total price
           orderAmount = productInfo.get().getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

           orderDetail.setOrderId(orderId);
           orderDetail.setDetailId(KeyUtil.genUniqueKey() );
           BeanUtils.copyProperties(productInfo.get(),orderDetail);

           //save to order detail table
           orderDetailRespository.save(orderDetail);
       }
        //save to order master table
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
           orderMasterResposity.save(orderMaster);

        //decrease inventory
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> orderMaster = orderMasterResposity.findById(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRespository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_NOT_EXIST);
        }

        OrderDTO orderDTO =  new OrderDTO();
        BeanUtils.copyProperties(orderMaster.get(),orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasters = orderMasterResposity.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConvertor.convert(orderMasters.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasters.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //query order status
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("order status is not correct, orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_INCORRECT);
        }

        //update order status
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMaster1 = orderMasterResposity.save(orderMaster);
        if (orderMaster1 == null){
            log.error("update order status failed, orderMaster={}",orderMaster1);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //increase order inventory
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("no order detail in this order, orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                e -> new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //refund if paid
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        //query order status
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("order status is not correct, orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_INCORRECT);
        }

        //update order status
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMaster1 = orderMasterResposity.save(orderMaster);
        if (orderMaster1 == null){
            log.error("finish order status failed, orderMaster={}",orderMaster1);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        //query order status
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("order status is not correct, orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_INCORRECT);
        }

        //query pay status
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("pay status is not correct, orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.PAY_STATUS_INCORRECT);
        }

        //update pay status
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMaster1 = orderMasterResposity.save(orderMaster);
        if (orderMaster1 == null){
            log.error("update order pay status failed, orderMaster={}",orderMaster1);
            throw new SellException(ResultEnum.PAY_STATUS_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
