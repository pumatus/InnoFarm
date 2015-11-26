package com.innofarm.pager;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.innofarm.R;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.ViewHolder;
import com.innofarm.model.NiuQunSurveyInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by dell on 2015/11/10.
 */
public class SurveyNiuQunPager extends BasePager {

    private ArrayList<NiuQunSurveyInfo> infolist;

    public SurveyNiuQunPager(Context context, ArrayList<NiuQunSurveyInfo> list) {
        super(context);
        this.infolist = list;

        initData();

    }


    @ViewInject(R.id.Common_lv)
    ListView lv;
    @ViewInject(R.id.Common_tv1)
    TextView tv1;
    @ViewInject(R.id.Common_tv2)
    TextView tv2;
    @ViewInject(R.id.Common_tv3)
    TextView tv3;


    @Override
    public View initView() {


        View view = View.inflate(context, R.layout.common_info_listview, null);
        ViewUtils.inject(this, view);

        return view;
    }


    @Override
    public void initData() {


        tv1.setText("阶段");
        //  tv1.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
        tv2.setText("数量");
        //  tv2.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
        tv3.setText("比例");
        // tv3.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));

        Adapter adapter = new Adapter<NiuQunSurveyInfo>(context, infolist, R.layout.adapter_survey_lv) {


            @Override
            public void convert(ViewHolder helper, NiuQunSurveyInfo item) {

                 /* helper.getView(R.id.tv_survey1).setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
                   helper.getView(R.id.tv_calve_date).setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
                   helper.getView(R.id.tv_calve_calveno).setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));*/

                helper.setText(R.id.tv_survey1, item.cattleType);
                helper.setText(R.id.tv_survey2, item.count);
                helper.setText(R.id.tv_survey3, item.present);
                RadioButton radio = helper.getView(R.id.rb_calve);
                radio.setVisibility(View.INVISIBLE);

            }
        };

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}



