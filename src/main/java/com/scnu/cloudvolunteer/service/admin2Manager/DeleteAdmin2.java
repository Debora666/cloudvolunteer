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
import com.scnu.cloudvolunteer.vo.admin2manager.DeleteAdmin2ReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：ben liang
 * @date ：2020/5/28
 * @description：删除二级管理员账号服务
 * @modified By：
 */
@Service(SvcConstant.DELETE_ADMIN2)
public class DeleteAdmin2 implements BaseService {

    @Autowired
    private AdminMapper adminMapper;

    private static final Logger logger = LoggerFactory.getLogger(DeleteAdmin2.class);

    @Override
    public ResponseVO service(String request) throws BaseException {
        DeleteAdmin2ReqVO reqVO = new DeleteAdmin2ReqVO();
        reqVO = JsonUtil.string2Obj(request,DeleteAdmin2ReqVO.class);
        validation(reqVO);
        Admin admin2 = adminMapper.selectByPrimaryKey(reqVO.getAdmin2Id());
        try{
            adminMapper.deleteByPrimaryKey(reqVO.getAdmin2Id());
        }catch (Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }

        logger.warn("一级管理员[{}]删除了二级管理员[{}],被删除的管理员信息为[{}]"
                ,reqVO.getAdminId()
                ,reqVO.getAdmin2Id()
                ,admin2.toString());

        ResponseVO<Integer> responseVO = new ResponseVO<>();
        return responseVO;
    }

    /**
     * 检查合法性
     * @param reqVO 请求VO
     * @throws BaseException 异常
     */
    private void validation(DeleteAdmin2ReqVO reqVO) throws BaseException{
        if (reqVO == null) {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if (reqVO.getAdminId() == null && reqVO.getAdmin2Id() == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        Admin admin = adminMapper.selectByPrimaryKey(reqVO.getAdminId()) ;
        Admin admin2 = adminMapper.selectByPrimaryKey(reqVO.getAdmin2Id());
        if (admin == null || admin2 == null){
            throw new BaseException(UserEnum.USER_NOT_EXIST);
        }
        privilegeValidation(admin, admin2);
    }

    /**
     * 检查权限是否相符, admin为一级管理员，admin2为二级管理员
     * @param admin 一级管理员实体
     * @param admin2 二级管理员实体
     * @throws BaseException 异常
     */
    private void privilegeValidation(Admin admin, Admin admin2) throws  BaseException{
        if(admin.getRole() != RoleConstant.ADMIN && admin2.getRole() != RoleConstant.ADMIN2){
            throw new BaseException(UserEnum.REQUEST_PARAM_RANGE_ERROE);
        }
    }
}
