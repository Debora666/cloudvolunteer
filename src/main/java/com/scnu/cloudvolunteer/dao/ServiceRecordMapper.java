package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.ServiceRecord;
import com.scnu.cloudvolunteer.dto.MatchResultDTO;
import com.scnu.cloudvolunteer.dto.ServiceMatchDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRecordMapper {
    int deleteByPrimaryKey(Integer serviceId);

    int insert(ServiceRecord record);

    /**
     * 批量插入
     * @param records
     * @return
     */
    int insertBatch(List<ServiceRecord> records);

    ServiceRecord selectByPrimaryKey(Integer serviceId);

    /**
     * 找出 服务时间 和 服务对象小于最大服务对象的志愿者
     * 并初步根据 学段 和 科目 筛选结果->值小于用户的值的不可能匹配，过滤
     * @param serviceMatchDTO
     * @return
     */
    List<MatchResultDTO> selectMatchVolunteer(ServiceMatchDTO serviceMatchDTO);

    List<ServiceRecord> selectAll();

    int updateBySelected(ServiceRecord record);

    /**
     * 增加服务时间
     * @param addWorkTime
     * @return
     */
    int addWorkTimeByServiceId(Integer serviceRecordId, Integer addWorkTime);
}