package com.scnu.cloudvolunteer.dao.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderService implements Serializable {
    private Integer orderServiceId;

    private Integer userId;

    private String subject;

    private Integer section;

    private Integer servicetime;

    private String remark;

    private Integer serviceStatus;

    private Date updateDate;

    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Integer getOrderServiceId() {
        return orderServiceId;
    }

    public void setOrderServiceId(Integer orderServiceId) {
        this.orderServiceId = orderServiceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getServicetime() {
        return servicetime;
    }

    public void setServicetime(Integer servicetime) {
        this.servicetime = servicetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
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
        sb.append(", orderServiceId=").append(orderServiceId);
        sb.append(", userId=").append(userId);
        sb.append(", subject=").append(subject);
        sb.append(", section=").append(section);
        sb.append(", servicetime=").append(servicetime);
        sb.append(", remark=").append(remark);
        sb.append(", serviceStatus=").append(serviceStatus);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", createDate=").append(createDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}