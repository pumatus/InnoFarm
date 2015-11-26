package com.innofarm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: syc
 * Date: 2015/9/6
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: activty 的基类
 */
public abstract class BaseActivity extends FragmentActivity {
    //  管理所有打开的activity
    //  static  和对象没有关系  和类有关系
    static List<BaseActivity> mActivities=new LinkedList<BaseActivity>();
    public static BaseActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("BaseActivity");
        synchronized (mActivities) {
            mActivities.add(this);
        }
        init();
        initView();

    }
    /**
     * 退出程序
     */
    public void killAll(){
        List<BaseActivity> clone;// 复制了集合
        synchronized (mActivities) {
            clone = new ArrayList<BaseActivity>(mActivities);
        }

        for(BaseActivity activity:clone){
            activity.finish();
        }

    }







    @Override
    protected void onResume() {
        super.onResume();
        activity=this;
    }
    @Override
    protected void onPause() {
        super.onPause();
        activity=null;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            // 当activity退出的时候 让它在集合中 移出
            mActivities.remove(this);
        }
    }
    /**
     * 初始化
     */
    protected abstract void init();
    /**
     * 初始化view界面
     */
    protected abstract  void initView() ;





}
