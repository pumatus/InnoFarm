package com.innofarm.utils;



import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.innofarm.InnoFarmApplication;
import com.innofarm.activity.BaseActivity;

/**
 *
 * User: syc
 * Date: 2015/9/6
 * Time: 10:06
 * Email: ycshi@isoftstone.com
 * Dest: 工具类
 */

public class UIUtils {

    private static Toast mToast = null;
    /**
     * 获取字符串数组
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getResource().getStringArray(id);
    }

    public static Resources getResource() {
        return getContext().getResources();
    }


    /**
     * 获取上下文
     * @return
     */
    public static Context getContext() {
        return InnoFarmApplication.getApplication();
    }
    /**
     * 获取资源目录的颜色
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return getResource().getColor(id);
    }


    /**
     * 填充
     * @param resource
     * @return
     */

    public static View inflate(int resource) {
        return View.inflate(getContext(), resource, null);
    }


    /**
     * 线程运行
     * @param runnable
     * @return
     */

    public static void runOnUiThread(Runnable runnable) {
        //  当前线程的id
        int myTid = android.os.Process.myTid();
        System.out.println("UIUtils:"+myTid);
        // 判断是否在主线程中
        if(myTid==InnoFarmApplication.getMainId()){
            runnable.run();
        }else{
            //获取Handler
            Handler handler = InnoFarmApplication.getHandler();
            handler.post(runnable);
        }
    }
    /**
     * 开启activity
     * @param intent
     */
    public static void startActivity(Intent intent) {
        if(BaseActivity.activity!=null){
            BaseActivity.activity.startActivity(intent);
        }else{
            //  在服务或者广播中调用  原因除了Activity 默认没有任务栈
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }





    /**
     * 功能：手机号码验证正则表达式
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 功能：提示信息
     * @param context 全局变量
     * @param message 全局变量
     */
    public static void showToast(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message,Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }



    /**
     * 将JSon转换成字符串
     */
    public static <T> T json2str(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("records",e.toString());
        }
        return t;
    }


}
