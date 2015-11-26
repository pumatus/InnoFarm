package com.innofarm.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.innofarm.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2015/11/3.
 */
public class BreedReportActivity extends BaseActivity {


    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;


    @Override
    protected void init() {
        View view =  View.inflate(this, R.layout.activity_breedreport,null);
        ViewUtils.inject(this,view);
        setContentView(view);

    }

    @Override
    protected void initView() {

        txt_title.setText("繁育报告");
        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
