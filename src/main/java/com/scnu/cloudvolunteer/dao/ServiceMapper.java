package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.Service;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceMapper {
    int deleteByPrimaryKey(Integer serviceId);

    int insert(Service record);

    Service selectByPrimaryKey(Integer serviceId);

    List<Service> selectAll();

    int updateBySelected(Service record);

    /**
     * 增加服务时间
     * @param addWorkTime
     * @return
     */
    int addWorkTimeByServiceId(@Param("addWorkTime") Integer addWorkTime);
}