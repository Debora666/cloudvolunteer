package com.scnu.cloudvolunteer.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：文件内容DTO
 * @modified By：
 */
@Data
public class FileContentDTO implements Serializable {
    //文件读取的内容，以base64存储
    public String fileContent;
    //文件类型
    public String fileType;

    private static final long serialVersionUID = 1L;
}
