package com.innofarm.pager;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.innofarm.R;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.ViewHolder;
import com.innofarm.model.EventAlertModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dell on 2015/11/3.
 */
public class InfoPredictPager extends BasePager {

    private ArrayList<EventAlertModel> predictList;
    public InfoPredictPager(Context context,ArrayList list) {
        super(context);
        this.predictList = list;
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


        tv1.setText("时间");
        tv2.setText("事件");
        tv3.setText("原因");

        Adapter adapter = new Adapter<EventAlertModel>(context, predictList, R.layout.adapter_calve_lv) {


            @Override
            public void convert(ViewHolder helper, final EventAlertModel item) {

                helper.setText(R.id.tv_calve_cattlenum, item.alertDate);
                helper.setText(R.id.tv_calve_date, item.alertType);
                helper.setText(R.id.tv_calve_calveno, item.alertReason);
                 RadioButton radio = helper.getView(R.id.rb_calve);
                radio.setVisibility(View.INVISIBLE);

            }
        };

           lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}
