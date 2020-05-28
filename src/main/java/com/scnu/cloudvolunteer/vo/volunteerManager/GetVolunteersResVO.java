package com.scnu.cloudvolunteer.vo.volunteerManager;

import com.scnu.cloudvolunteer.dao.pojo.Volunteer;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author ：ben liang
 * @date ：2020/5/27
 * 查看志愿者响应vo
 */
@Data
public class GetVolunteersResVO implements Serializable {
    private ArrayList<Volunteer> volunteers;
}
