package com.scnu.cloudvolunteer.dao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
// 忽略updateDate和createDate（序列化和反序列化）
@JsonIgnoreProperties(value = {"updateDate", "createDate"})
public class Admin implements Serializable {
    private Integer adminId;

    private String account;

    // 只能写（反序列化，string->pojo），不能读（序列化，pojo->string）
    // 效果就是前端传password可以接收，返回给前端则不传password
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Integer organization;

    private Integer role;

    private Date updateDate;

    private Date createDate;

    private static final long serialVersionUID = 1L;
}