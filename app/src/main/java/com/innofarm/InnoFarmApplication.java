package com.innofarm;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.innofarm.manager.InnoFormDB;
import com.innofarm.utils.UnitUtils;

/**
 * User: syc
 * Date: 2015/9/6
 * Time: 10:06
 * Email: ycshi@isoftstone.com
 * Dest: application类。继承android Application类。初始化程序中使用到的所有全局变量
 */
public class InnoFarmApplication extends Application {
    private static InnoFarmApplication application;
    private static int MainId;
    private static Handler handler;
    public static float sWidth;
    public static float sHeight;
    public static int viewWidth;
    public static int viewHeight;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        //创建数据库
        InnoFormDB  inno = new InnoFormDB(context);
        inno.createTable();





        sWidth = getResources().getDisplayMetrics().widthPixels;
        sHeight = getResources().getDisplayMetrics().heightPixels;


        application = this;
        MainId = android.os.Process.myTid();
        System.out.println("BaseApplication:" + MainId);
        handler = new Handler();
        // 在主线程的Handler
        // 设置捕获异常的处理器
        //  当程序上线的时候 在添加捕获异常的处理器
        //Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }

    public static Handler getHandler() {
        return handler;
    }

    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        //  当发生异常没有捕获   调用该方法
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            System.out.println("程序发生异常了...");
            ex.printStackTrace();// 把日志输出到控制台
            //ex.printStackTrace(err)  通过流写到文件中 ,然后可以通过网络传输到服务器中
            //停止当前线程
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    public static int getMainId() {
        return MainId;
    }

    public static InnoFarmApplication getApplication() {
        return application;
    }



    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getAppContext() {
        return context;
    }

    /**
     * 设置view制作页面的高度
     *
     * @param height
     */
    public static void setView(int height) {

        viewWidth = (int) ((sHeight - UnitUtils.dip2px(50)) / 1.3 / 1.7);
        viewHeight = (int) ((sHeight - UnitUtils.dip2px(50)) / 1.3);

    }


}




