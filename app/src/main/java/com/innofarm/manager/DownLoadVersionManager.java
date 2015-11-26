package com.innofarm.manager;

/**
 * Created by dell on 2015/11/11.
 */


import java.io.BufferedInputStream;

import java.io.File;

import java.io.FileOutputStream;

import java.io.InputStream;

import java.net.HttpURLConnection;

import java.net.URL;

import android.app.ProgressDialog;

import android.os.Environment;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class DownLoadVersionManager {

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception{

        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的

       /* if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){*/

            URL url = new URL(path);

            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);

            //获取到文件的大小

            pd.setMax(conn.getContentLength());

            InputStream is = conn.getInputStream();

            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");

            FileOutputStream fos = new FileOutputStream(file);

            BufferedInputStream bis = new BufferedInputStream(is);

            byte[] buffer = new byte[1024];

            int len ;

            int total=0;

            while((len =bis.read(buffer))!=-1){

                fos.write(buffer, 0, len);

                total+= len;

                //获取当前下载量
                pd.setProgress(total);

            }

            fos.close();

            bis.close();

            is.close();

            return file;
/*
        }

        else{

            return null;

        }*/

    }
    
    
    
  /*
    public void getUpdate(String url){

        HttpUtils http;
        http = new HttpUtils();
        http.configDefaultHttpCacheExpiry(1000*15);

        http.download(HttpRequest.HttpMethod.POST,url,,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        })




    }*/




}
