package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceMapper {
    int deleteByPrimaryKey(Integer serviceId);

    int insert(Service record);

    Service selectByPrimaryKey(Integer serviceId);

    List<Service> selectAll();

    int updateBySelected(Service record);

    /**
     * 更新服务状态
     * @param status
     * @return
     */
    int updateStatusById(Integer status);

    /**
     * 增加服务时间
     * @param addWorkTime
     * @return
     */
    int addWorkTimeByServiceId(@Param("addWorkTime") Integer addWorkTime);
}