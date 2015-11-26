package com.innofarm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innofarm.R;
import com.innofarm.activity.OperationActivity;
import com.innofarm.activity.TransitActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * User: syc
 * Date: 2015/9/18
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 备注
 */
public class RemarksFragment extends BaseFragment implements View.OnClickListener {
    @ViewInject(R.id.et_remark)
    EditText remark;
    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;
    @ViewInject(R.id.ll_Weight)
    LinearLayout ll_Weight;
    @ViewInject(R.id.et_Weight)
    EditText et_Weight;

    @ViewInject(R.id.imgbtn_right)
    ImageButton imgbtn_right;

    String content;

    public static Fragment instance() {
        return new RemarksFragment();
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_remark, null);
        ViewUtils.inject(this, view);


        return view;
    }

    @Override
    public void initData() {

        imgbtn_left.setOnClickListener(this);
        imgbtn_right.setVisibility(View.VISIBLE);
        imgbtn_right.setOnClickListener(this);


        Bundle bundle = getArguments();

        content = bundle.getString("Arg");

        if (content.equals("体重")) {
            txt_title.setText("体重");
            ll_Weight.setVisibility(View.VISIBLE);
            remark.setVisibility(View.GONE);

        } else if (content.equals("流产原因")) {
            txt_title.setText("流产原因");
            remark.setHint("添加原因");


        } else {
            remark.setText(content);
            txt_title.setText("备注");

        }


    }


    public void set(String str1, String str) {

        OperationActivity.setTv_Content(str1, str);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbtn_left:
                if (content.equals("体重")) {
                    String weight = et_Weight.getText().toString() + "kg";
                    set(content, weight);
                } else if (content.contains("原因") || content.equals("描述")) {
                    String str = remark.getText().toString();
                    set(content, str);
                } else {
                    String str = remark.getText().toString();
                    set("备注", str);
                }
                TransitActivity finish = (TransitActivity) getActivity();
                finish.finish();

                break;

            case R.id.imgbtn_right:
                if (content.equals("体重")) {
                    String weight = et_Weight.getText().toString() + "kg";
                    set(content, weight);
                } else if (content.contains("原因") || content.equals("描述")) {
                    String str = remark.getText().toString();
                    set(content, str);
                } else {
                    String str = remark.getText().toString();
                    set("备注", str);
                }
                TransitActivity finish1 = (TransitActivity) getActivity();
                finish1.finish();

        }

    }
}
