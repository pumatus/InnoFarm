package com.innofarm.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.innofarm.pager.BasePager;

import java.util.ArrayList;

/**
 * User: syc
 * Date: 2015/9/9
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:  主页viewpager的适配器
 */
public class MainPagerAdapter extends PagerAdapter {

    private ArrayList<BasePager> pagerList;
    private Context context;

    public MainPagerAdapter(Context context,ArrayList<BasePager> list){

        this.pagerList = list;
        this.context = context;

    }


    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(pagerList.get(position).getRootView());
        return pagerList.get(position).getRootView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);
    }
}
