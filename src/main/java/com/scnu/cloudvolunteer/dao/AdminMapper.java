package com.scnu.cloudvolunteer.dao;

import com.scnu.cloudvolunteer.dao.pojo.Admin;
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

    int updateByPrimaryKey(Admin record);

    int updateBySelected(Admin record);
}