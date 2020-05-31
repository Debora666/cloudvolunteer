package com.scnu.cloudvolunteer.base.constant;

import java.util.HashMap;
import java.util.Map;


/**
 * @author ：ben liang
 * @date ：2020/5/31
 * @description：RFC2397标准的头部常量
 * @modified By：
 */
public class Rfc2397HeadersConstant {
    //GIF文件
    public final static String GIF = "data:image/gif;base64,";
    //PNG文件
    public final static String PNG = "data:image/png;base64,";
    //JPEG文件
    public final static String JPEG = "data:image/jpeg;base64,";
    //ICON文件
    public final static String ICON = "data:image/x-icon;base64,";
    //jpg文件
    public final static String JPG = "data:image/jpg;base64,";

//    /**
//     * 输出支持的所有文件类型
//     * @return 输出支持的所有文件的arraylist
//     */
//    public final static ArrayList<String> toArrayList(){
//        return new ArrayList<String>(Arrays.asList(gif,png,jpeg,icon,jpg));
//    }

    public final static Map<String,String> toHashMap(){
        return new HashMap<String,String>(){{
            put("gif",GIF);
            put("png",PNG);
            put("jpeg",JPEG);
            put("icon",ICON);
            put("jpg",JPG);
        }};
    }
}
