package com.innofarm.fragment;

import android.view.View;
import android.widget.RadioGroup;

import com.innofarm.R;
import com.innofarm.adapter.MainPagerAdapter;
import com.innofarm.external.LazyViewPager;
import com.innofarm.external.MyViewPager;
import com.innofarm.pager.BasePager;
import com.innofarm.pager.MainInfoPager;
import com.innofarm.pager.MainOperationPager;
import com.innofarm.pager.MainSettingPager;
import com.innofarm.pager.TaskReminderPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * User: syc
 * Date: 2015/9/8
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 主页
 */
public class MainFragment extends BaseFragment {
    public static final String tag = "mainFragment";

    private ArrayList<BasePager> list;

    @ViewInject(R.id.main_pager)
    private MyViewPager main_pager;
    @ViewInject(R.id.main_radio)
    private RadioGroup main_radio;


    @Override
    public View initView() {
        view = View.inflate(context, R.layout.frag_main, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {

       /* if (getActivity().getIntent()!=null)
            return;
*/
        initPager();

        main_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_work:
                        //让viewpager根据索引去指定内部的view
                        main_pager.setCurrentItem(0);
                        break;
                    case R.id.rb_operation:
                        main_pager.setCurrentItem(1);
                        break;
                    case R.id.rb_info:
                        main_pager.setCurrentItem(2);
                        break;
                    case R.id.rb_setting:
                        main_pager.setCurrentItem(3);
                        break;

                }
            }
        });


        main_pager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                list.get(position).initData();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        main_radio.check(R.id.rb_work);

    }

    private void initPager() {


        list = new ArrayList<BasePager>();


        list.add(new TaskReminderPager(getActivity()));
        list.add(new MainOperationPager(getActivity()));
        list.add(new MainInfoPager(getActivity()));
        list.add(new MainSettingPager(getActivity()));


        main_pager.setAdapter(new MainPagerAdapter(getActivity(), list));






    }


}