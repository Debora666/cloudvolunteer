package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerMapper {
    int deleteByPrimaryKey(Integer volunteerId);

    int insert(Volunteer record);

    Volunteer selectByPrimaryKey(Integer volunteerId);

    Volunteer selectByOpenid(String openId);

    /**
     * 根据时间段，科目，学段 查询志愿者
     * @param volunteer
     * @return
     */
    List<Volunteer> selectMatchVolunteer(Volunteer volunteer);

    List<Volunteer> selectAll();

    /**
     * 根据归属组织查询志愿者
      */
    List<Volunteer> selectByOrganization(Integer organization);

    /**
     * 查询志愿者人数
      */
    int selectCount();

    /**
     * 增加服务时间
     * @return
     */
    int addWorkTimeByVolunteerId(@Param("addWorkTime") Integer addWorkTime);

    /**
     * 更新志愿者审核状态
     * @param checkStatus
     * @return
     */
    int updateStatusById(@Param("checkStatus") Integer checkStatus);

    int updateBySelected(Volunteer record);

}