package com.innofarm.pager;

import android.content.Context;

import android.view.View;

import com.innofarm.R;
import com.lidroid.xutils.ViewUtils;


/**
 * User: syc
 * Date: 2015/9/9
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 主页--工作
 */
public class MainWorkPager extends BasePager {






    public MainWorkPager(Context context) {
        super(context);
    }



    @Override
    public View initView() {
        view = View.inflate(context,R.layout.pager_mainwork,null);
        ViewUtils.inject(context,view);

        initTitleBar("信息查询");

        return view;
    }

    @Override
    public void initData() {















    }
}
