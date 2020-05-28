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
import com.scnu.cloudvolunteer.dao.pojo.Admin2Count;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.vo.admin2manager.GetAllAdmin2ReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author ：ben liang
 * @date ：2020/5/28
 * @description：查看所有二级管理员
 * @modified By：
 */
@Service(SvcConstant.GET_ALL_ADMIN2)
public class GetAllAdmin2 implements BaseService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public ResponseVO service(String request) throws BaseException {

        GetAllAdmin2ReqVO reqVO = JsonUtil.string2Obj(request, GetAllAdmin2ReqVO.class);

        validation(reqVO);

        ArrayList<Admin2Count> resList = new ArrayList<>();

        try{
            resList = (ArrayList<Admin2Count>) adminMapper.selectAllAdmin2Count();
        }catch(Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }

        ResponseVO<ArrayList<Admin2Count>> responseVO = new ResponseVO<>();
        responseVO.setData(resList);
        return responseVO;
    }

    /**
     * 检查合法性
     * @param reqVO 请求VO
     * @throws BaseException 异常
     */
    private void validation(GetAllAdmin2ReqVO reqVO) throws BaseException{
        if(reqVO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getAdminId() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        Admin admin = adminMapper.selectByPrimaryKey(reqVO.getAdminId());
        if(admin == null){
            throw new BaseException(UserEnum.USER_NOT_EXIST);
        }
        privilegeValidation(admin);
    }

    /**
     * 检查权限
     * @param admin 管理员实体
     * @throws BaseException 异常
     */
    private void privilegeValidation(Admin admin) throws BaseException{
        if(admin.getRole() != RoleConstant.ADMIN){
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
    }
}
