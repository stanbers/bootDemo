package com.stan.springboottrading.repository;

import com.stan.springboottrading.dataobject.OrderDetail;
import com.stan.springboottrading.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRespository extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findByOrderId(String orderId);
}
