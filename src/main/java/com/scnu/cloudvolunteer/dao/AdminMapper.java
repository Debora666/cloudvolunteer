package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.Admin;
import com.scnu.cloudvolunteer.dao.pojo.Admin2Count;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer adminId);

    Admin selectByAccount(String account);

    List<Admin> selectAll();

    List<Admin> selectAllAdmin2();

    int updateBySelected(Admin record);

    List<Admin2Count> selectAllAdmin2Count();
}