package com.innofarm.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.innofarm.R;
import com.lidroid.xutils.ViewUtils;

/**
 * Created by dell on 2015/10/20.
 *
 * 提醒
 */
public class RemindFragment  extends BaseFragment{

    public static Fragment instance() {
        return  new RemindFragment();
    }
    @Override
    public View initView() {
        View view  = View.inflate(context, R.layout.frag_remind,null);
        ViewUtils.inject(this,view);

        return view;
    }

    @Override
    public void initData() {



    }




}
