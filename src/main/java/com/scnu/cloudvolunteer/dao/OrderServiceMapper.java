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

    int updateBySelected(OrderService record);

    /**
     * 更改服务订单状态
     * @param status
     * @return
     */
    int updateStatusById(Integer status);
}