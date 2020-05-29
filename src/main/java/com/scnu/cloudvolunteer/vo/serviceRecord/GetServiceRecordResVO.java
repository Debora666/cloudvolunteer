package com.scnu.cloudvolunteer.vo.serviceRecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scnu.cloudvolunteer.dao.pojo.User;
import com.scnu.cloudvolunteer.dao.pojo.UserServiceRecord;
import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import com.scnu.cloudvolunteer.dao.pojo.VolunteerServiceRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author ：ben liang
 * @date ：2020/5/29
 * @description：查看用户所有服务记录响应VO
 * @modified By：
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetServiceRecordResVO implements Serializable {

    ArrayList<UserServiceRecord> userServiceRecords;

    ArrayList<VolunteerServiceRecord> volunteerServiceRecords;
}