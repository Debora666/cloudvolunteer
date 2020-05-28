package com.scnu.cloudvolunteer.service.admin2Manager;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.AdminMapper;
import com.scnu.cloudvolunteer.dao.pojo.Admin;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.admin2manager.CreateAdmin2ReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;


/**
 * @author ：ben liang
 * @date ：2020/5/28
 * @description：创建二级管理员服务
 * @modified By：
 */
@Service(SvcConstant.CREATE_ADMIN2)
public class CreateAdmin2 implements BaseService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {
        //获取当前时间
        Timestamp currTimestamp = new Timestamp(System.currentTimeMillis());
        CreateAdmin2ReqVO reqVO = JsonUtil.string2Obj(request, CreateAdmin2ReqVO.class);
        validation(reqVO);
        try {
            Admin newAdmin = new Admin();
            newAdmin.setAccount(reqVO.getAccount());
            newAdmin.setPassword(reqVO.getPassword());
            newAdmin.setOrganization(reqVO.getOrganization());
            newAdmin.setRole(RoleConstant.ADMIN2);
            newAdmin.setCreateDate(currTimestamp);
            newAdmin.setUpdateDate(currTimestamp);
            //插入数据
            adminMapper.insert(newAdmin);
        }catch (Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        ResponseVO<Integer> resVO = new ResponseVO<>();
        return resVO;
    }

    /**
     * 入参检查
     * @param reqVO
     * @throws BaseException
     */
    public void validation(CreateAdmin2ReqVO reqVO) throws BaseException{
        if(reqVO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getAdminId() == null
                || StringUtils.hasText(reqVO.getAccount())
                || StringUtils.hasText(reqVO.getAccount())
                || StringUtils.hasText(reqVO.getPassword())
                || reqVO.getOrganization() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        Admin admin = adminMapper.selectByPrimaryKey(reqVO.getAdminId());
        if(admin == null){
            throw new BaseException(UserEnum.USER_NOT_EXIST);
        }
        privilegeValidation(admin);
    }

    /**
     * 管理员权限检查
     * @param admin
     * @throws BaseException
     */
    public void privilegeValidation(Admin admin) throws BaseException{
        if (admin.getRole() != RoleConstant.ADMIN){
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
    }
}
