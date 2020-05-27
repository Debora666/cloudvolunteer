package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByOpenid(String openId);

    List<User> selectAll();

    /**
     * 查询用户数
      */
    int selectCount();

    int updateBySelected(User record);
}