package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.OrderService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderServiceMapper {
    int deleteByPrimaryKey(Integer orderServiceId);

    int insert(OrderService record);

    OrderService selectByPrimaryKey(Integer orderServiceId);

    List<OrderService> selectAll();

    List<OrderService> selectByServiceStatus(Integer status);

    int updateBySelected(OrderService record);
}