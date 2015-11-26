package com.innofarm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: syc
 * Date: 2015/9/8
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: fragment基类
 * */
public abstract class BaseFragment extends Fragment {
    protected View view;
    public Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ((Context)getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = initView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    //view操作
    public abstract View initView();
    //填充界面数据的方法
    public abstract void initData() ;
}
