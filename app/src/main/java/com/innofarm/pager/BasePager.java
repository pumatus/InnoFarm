package com.innofarm.pager;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.innofarm.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
/**
 * User: syc
 * Date: 2015/9/9
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 所有pager的基类
 */
public abstract class BasePager {
    public View view;
    public Context context;


    public BasePager(Context context) {

        this.context = context;
        view = initView();
    }

    public View getRootView(){
        return view;
    }


    /**
     * 初始化view
     * */
    public abstract View initView() ;

    /**
     * 填充数据
     * */
    public abstract void initData();


    public void initTitleBar(String title){


        ImageButton imgbtn_left = (ImageButton) view.findViewById(R.id.imgbtn_left);
        imgbtn_left.setVisibility(View.GONE);

        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(title);

    }


}