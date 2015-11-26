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
import com.innofarm.model.CattleFileEventInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by dell on 2015/11/10.
 */
public class SurveyQinRuNiuPager extends BasePager {

    private ArrayList infolist;

    public SurveyQinRuNiuPager(Context context, ArrayList list) {
        super(context);
        this.infolist = list;
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
        //initData();
        return view;
    }

    @Override
    public void initData() {

        tv1.setText("阶段");
        tv1.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
        tv2.setText("数量");
        tv2.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
        tv3.setText("比例");
        tv3.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));

        Adapter adapter = new Adapter<CattleFileEventInfo>(context, infolist, R.layout.adapter_calve_lv) {


            @Override
            public void convert(ViewHolder helper, CattleFileEventInfo item) {

                helper.getView(R.id.tv_calve_cattlenum).setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
                helper.getView(R.id.tv_calve_date).setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));
                helper.getView(R.id.tv_calve_calveno).setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1));

                helper.setText(R.id.tv_calve_cattlenum, item.getEventTime());
                helper.setText(R.id.tv_calve_date, item.getEventName());
                helper.setText(R.id.tv_calve_calveno, item.getEventIns());
                RadioButton radio = helper.getView(R.id.rb_calve);
                radio.setVisibility(View.INVISIBLE);

            }
        };

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}




