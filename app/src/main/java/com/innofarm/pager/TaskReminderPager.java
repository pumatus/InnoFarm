package com.innofarm.pager;

import android.content.Context;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innofarm.R;
import com.innofarm.activity.RemindActivity;
import com.innofarm.activity.RemindContentActivity;
import com.innofarm.activity.TransitActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * User: syc
 * Date: 2015/9/9
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 主页--任务
 */
public class TaskReminderPager extends BasePager implements View.OnClickListener {



    @ViewInject(R.id.rl_mianwork_event)
    RelativeLayout rl_event;
    @ViewInject(R.id.rl_reminder_personal)
    RelativeLayout rl_personal;
   /* @ViewInject(R.id.rl_reminder_service)
    RelativeLayout rl_service;*/
    @ViewInject(R.id.rl_reminder_sys)
    RelativeLayout rl_sys;


    public TaskReminderPager(Context context) {
        super(context);
    }


    @Override
    public View initView() {
        view = View.inflate(context,R.layout.pager_taskremind,null);
        ViewUtils.inject(this,view);

        initTitleBar("任务");

        initData();

        return view;
    }

    @Override
    public void initData() {
        rl_event.setOnClickListener(this);
        rl_personal.setOnClickListener(this);
        //rl_service.setOnClickListener(this);
        rl_sys.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())) {
            case R.id.rl_mianwork_event:
                Intent intent = new Intent(context, RemindActivity.class);
                // intent.putExtra("tag","个人提醒");
                context.startActivity(intent);

                break;
            case R.id.rl_reminder_personal:
                Intent intent1 = new Intent(context, RemindContentActivity.class);
                 intent1.putExtra("name","个人提醒");
                context.startActivity(intent1);


                break;
           // case R.id.rl_reminder_service:

               // break;
            case R.id.rl_reminder_sys:

                break;


        }

    }
}
