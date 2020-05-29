package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerMapper {
    int deleteByPrimaryKey(Integer volunteerId);

    int insert(Volunteer record);

    Volunteer selectByPrimaryKey(Integer volunteerId);

    Volunteer selectByOpenid(String openId);

    List<Volunteer> selectAll();

    /**
     * 根据归属组织查询志愿者
      */
    List<Volunteer> selectByOrganization(Integer organization);

    /**
     * 查询志愿者人数
     * status 大于0
      */
    int selectVolunteerCount();

    /**
     * 增加服务时间
     * @return
     */
    int addWorkTimeByVolunteerId(Integer volunteerId, Integer addWorkTime);

    int updateBySelected(Volunteer record);

}