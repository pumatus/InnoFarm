package com.innofarm.manager;


import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HttpHelper {
    private static HttpUtils httpUtils;
//    public static final String SERVER_URL="http://10.16.132.46:8080/innofarmandroid/";//http://10.16.132.46:8080/innofarmandroid/
    public static final String SERVER_URL="http://123.57.63.75:8080/phone/";
    public static String sendGet(String url){
        if(httpUtils==null){
            httpUtils=new HttpUtils();
        }
        try {
            ResponseStream sendSync = httpUtils.sendSync(HttpMethod.GET, url);
            return sendSync.readString();  // 默认去掉了换行符
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

