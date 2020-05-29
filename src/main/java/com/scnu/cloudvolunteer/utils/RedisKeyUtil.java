package com.scnu.cloudvolunteer.utils;

/**
 * @author ：zzheng
 * @date ：2020/5/29
 * 生成redis的key
 */
public class RedisKeyUtil {
    private static final String CHANGE_ORGANIZATION = "change_organization-";

    /**
     * 生成志愿者更改组织时，redis的key
     * @param volunteerId
     * @return
     */
    public static String getChangeOrganizationKey(Integer volunteerId){
        return CHANGE_ORGANIZATION + volunteerId;
    }
}
