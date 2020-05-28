package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.ServiceRecord;
import com.scnu.cloudvolunteer.dto.MatchResultDTO;
import com.scnu.cloudvolunteer.dto.ServiceMatchDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRecordMapper {
    int deleteByPrimaryKey(Integer serviceId);

    int insert(ServiceRecord record);

    ServiceRecord selectByPrimaryKey(Integer serviceId);

    /**
     * 找出学段，科目，服务时间以及服务对象小于最大服务对象的志愿者
     * @param serviceMatchDTO
     * @return
     */
    List<MatchResultDTO> selectMatchVolunteer(ServiceMatchDTO serviceMatchDTO);

    List<ServiceRecord> selectAll();

    int updateBySelected(ServiceRecord record);

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