package com.scnu.cloudvolunteer.dao.pojo;

import java.io.Serializable;
import java.util.Date;

public class Service implements Serializable {
    private Integer serviceId;

    private Integer orderServiceId;

    private Integer volunteerId;

    private Integer userId;

    private Integer workTime;

    private Date updateDate;

    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getOrderServiceId() {
        return orderServiceId;
    }

    public void setOrderServiceId(Integer orderServiceId) {
        this.orderServiceId = orderServiceId;
    }

    public Integer getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serviceId=").append(serviceId);
        sb.append(", orderServiceId=").append(orderServiceId);
        sb.append(", volunteerId=").append(volunteerId);
        sb.append(", userId=").append(userId);
        sb.append(", workTime=").append(workTime);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", createDate=").append(createDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}