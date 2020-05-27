package com.scnu.cloudvolunteer.base.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author zzheng
 * @date 2020/05/25 23:16:20
 * @description：
 *  http请求父类
 */
public class BaseHttp {

    /**
     * get请求
     * @param requestUrl
     * @param params
     * @return
     */
    protected static String doGet(String requestUrl, Map params) {
        String response = null;
        try {
            URL url = new URL(requestUrl + "?" + urlEncode(params));
            HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            InputStream inputStream = httpUrlConn.getInputStream();
            response = getResponse(inputStream);
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * post请求
     * @param requestUrl
     * @param params
     * @return
     */
    protected static String doPost(String requestUrl, String params) {
        String response = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.connect();
            PrintWriter out = new PrintWriter(httpUrlConn.getOutputStream());
            out.print(params);
            out.flush();
            InputStream inputStream = httpUrlConn.getInputStream();
            response = getResponse(inputStream);
            inputStream.close();
            out.close();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 处理请求参数
     * 将map的请求参数改为xx=xx&yy=yy
     * @param data
     * @return
     */
    private static String urlEncode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        if (data != null) {
            for (Map.Entry i : data.entrySet()) {
                try {
                    sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 处理响应参数
     * @param inputStream
     * @return
     */
    private static String getResponse(InputStream inputStream){
        StringBuilder buffer = new StringBuilder();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            return buffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
