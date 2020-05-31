package com.scnu.cloudvolunteer.service.serviceRecord;

import com.scnu.cloudvolunteer.base.constant.RoleConstant;
import com.scnu.cloudvolunteer.base.constant.SvcConstant;
import com.scnu.cloudvolunteer.base.enums.ServiceEnum;
import com.scnu.cloudvolunteer.base.enums.UserEnum;
import com.scnu.cloudvolunteer.base.exception.BaseException;
import com.scnu.cloudvolunteer.base.service.BaseService;
import com.scnu.cloudvolunteer.base.vo.ResponseVO;
import com.scnu.cloudvolunteer.dao.AdminMapper;
import com.scnu.cloudvolunteer.dao.VolunteerMapper;
import com.scnu.cloudvolunteer.dao.pojo.Admin;
import com.scnu.cloudvolunteer.dto.NocheckServiceDTO;
import com.scnu.cloudvolunteer.utils.JsonUtil;
import com.scnu.cloudvolunteer.utils.RedisKeyUtil;
import com.scnu.cloudvolunteer.utils.RedisUtil;
import com.scnu.cloudvolunteer.vo.serviceRecord.GetNoCheckServiceRecordReqVO;

import java.util.*;

import com.scnu.cloudvolunteer.vo.serviceRecord.NocheckServiceRecordItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：查看所有待审核的服务信息的服务
 * @modified By：
 * 如管理员为二级管理员，将无视请求中的organization参数，并将范围限定在二级管理员所在的组织
 */
@Service(SvcConstant.GET_NO_CHECK_SERVICE_RECORD)
public class GetNoCheckServiceRecord implements BaseService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    VolunteerMapper volunteerMapper;


    @Override
    public ResponseVO service(String request) throws BaseException {
        GetNoCheckServiceRecordReqVO reqVO = new GetNoCheckServiceRecordReqVO();
        reqVO = (GetNoCheckServiceRecordReqVO) JsonUtil.string2Obj(request,GetNoCheckServiceRecordReqVO.class);

        //检查入参合法性
        validation(reqVO);

        Map<String, Object> nocheckServiceRecordMap = new HashMap<>();
        Admin admin = null;
        try{
            //获取未审核服务map
            nocheckServiceRecordMap = redisUtil.getHash(RedisKeyUtil.getUncheckedServiceRecordMapKey());
            //获取管理员信息，用于接下来判断组织
            admin = adminMapper.selectByPrimaryKey(reqVO.getAdminId());
        }catch(Exception e){
            throw new BaseException(ServiceEnum.DATABASE_ERROR);
        }
        if(admin == null){
            throw new BaseException(UserEnum.USER_NOT_EXIST);
        }
        //获得需要查询的组织id
        Integer selectedOrganization = getSelectedOrganization(admin,reqVO);
        //查询结果列表
        ArrayList<NocheckServiceRecordItemVO> resultArrayList = new ArrayList<>();

        //开始遍历未审核服务，并进行筛选
        Iterator<Map.Entry<String, Object>> entries = nocheckServiceRecordMap.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, Object> entry = entries.next();
            String serviceOrderId = entry.getKey();
            NocheckServiceDTO nocheckServiceDTO = (NocheckServiceDTO) entry.getValue();

            if (selectedOrganization != null){
                //不符合的跳过
                if(nocheckServiceDTO.getOrganization() != selectedOrganization){
                    continue;
                }
            }
            //单条service
            NocheckServiceRecordItemVO nocheckServiceRecordItem = new NocheckServiceRecordItemVO(serviceOrderId,nocheckServiceDTO);
            //插入结果列表
            resultArrayList.add(nocheckServiceRecordItem);
        }
        //返回VO
        ResponseVO<ArrayList<NocheckServiceRecordItemVO>> responseVO = new ResponseVO<>();
        responseVO.setData(resultArrayList);
        return responseVO;
    }

    /**
     * 入参验证
     * @param reqVO
     */
    private void validation(GetNoCheckServiceRecordReqVO reqVO) throws BaseException{
        if(reqVO == null){
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
        if(reqVO.getAdminId() == null
                || reqVO.getRole() == null) {
            throw new BaseException(UserEnum.REQUEST_PARAM_NULL);
        }
    }


    /**
     * 返回真正需要查询的组织号
     * @param admin reqVO对应的admin
     * @param reqVO 请求VO
     * @return null-查询所有组织  其他的Integer-查询对应的组织
     */
    private Integer getSelectedOrganization(Admin admin, GetNoCheckServiceRecordReqVO reqVO){
        Integer selectedOrganization = null;

        if(admin.getRole() == RoleConstant.ADMIN2){
            //二级管理员只能查看自己所在的组织
            selectedOrganization = admin.getOrganization();
        }else if(admin.getRole() == RoleConstant.ADMIN){
            //一级管理员能够能求请求的组织来查看
            selectedOrganization = reqVO.getOrganization();
        }
        return selectedOrganization;
    }
}
