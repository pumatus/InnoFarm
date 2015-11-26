package com.innofarm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.innofarm.R;
import com.innofarm.fragment.CalvesFragment;
import com.innofarm.fragment.RemarksFragment;

import com.innofarm.fragment.SelectNameFragment;
import com.innofarm.utils.FragmentUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * User: syc
 * Date: 2015/9/18
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: fram容器，中转fragment
 */
public class TransitActivity extends BaseActivity {


    String tag;

    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_transit, null);
        ViewUtils.inject(this, view);
        setContentView(view);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();

        tag = intent.getStringExtra("tag");
        String str = getIntent().getStringExtra("arg");

        Bundle bundle = new Bundle();
        bundle.putString("Arg", str);


        switch (tag) {

            case "beizhu":
                
                FragmentUtils.addFragment(this, RemarksFragment.instance(), true,bundle);
                break;
            case "selectname":

                FragmentUtils.addFragment(this, SelectNameFragment.instance(), true, bundle);
                break;

            case "calve":

                FragmentUtils.addFragment(this, CalvesFragment.instance(), true);
                break;

            case "个人提醒":

               // FragmentUtils.addFragment(this, RemindFragment.instance(), true);
                break;


        }


    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
