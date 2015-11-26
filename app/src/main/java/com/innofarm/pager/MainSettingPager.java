package com.innofarm.pager;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.innofarm.MainActivity;
import com.innofarm.R;
import com.innofarm.activity.VersionUpdateActivity;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.ViewHolder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


/**
 * User: syc
 * Date: 2015/9/6
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 主页--设置
 */
public class MainSettingPager extends BasePager {
    public MainSettingPager(Context context) {
        super(context);
    }



    @ViewInject(R.id.rl_about_setting)
    RelativeLayout about;


    @Override
    public View initView() {
        view  = View.inflate(context, R.layout.pager_mainsetting,null);
        ViewUtils.inject(this,view);


        initTitleBar("设置");

        return view;
    }




    @Override
    public void initData() {

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inten = new Intent(context, VersionUpdateActivity.class);
                context.startActivity(inten);

            }
        });















    }
}
