package com.scnu.cloudvolunteer.utils;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 生成redis的key
 */
public class RedisKeyUtil {
    //志愿者更改组织的redis key
    private static final String CHANGE_ORGANIZATION = "change_organization-";
    //未审核的志愿者服务信息的Map的redis key
    private static final String UNCHECK_SERVICE_RECORD_MAP = "uncheck_service_record_map";

    /**
     * 生成志愿者更改组织时，redis的key
     * @param volunteerId
     * @return
     */
    public static String getChangeOrganizationKey(Integer volunteerId){
        return CHANGE_ORGANIZATION + volunteerId;
    }


    /**
     *
     * @return
     */
    public static String getUncheckedServiceRecordMapKey(){return UNCHECK_SERVICE_RECORD_MAP;};

}
