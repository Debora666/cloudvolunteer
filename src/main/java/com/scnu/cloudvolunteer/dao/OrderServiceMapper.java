package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.OrderService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    int updateStatusById(@Param("status") Integer status);
}