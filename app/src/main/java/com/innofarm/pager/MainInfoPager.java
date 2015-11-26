package com.innofarm.pager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.innofarm.InnoFarmConstant;
import com.innofarm.R;
import com.innofarm.activity.BreedReportActivity;
import com.innofarm.activity.FarmSurvey;
import com.innofarm.activity.FileQueryActivity;
import com.innofarm.activity.OperationRecordsActivity;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.external.ViewHolder;
import com.innofarm.manager.InnoFormDB;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.protocol.FetchAllResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * User: syc
 * Date: 2015/9/9
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:  主页--信息
 */
public class MainInfoPager extends BasePager{
    public MainInfoPager(Context context) {
        super(context);
    }

    @ViewInject(R.id.lv_maininfo)
    ListView lv ;

    @Override
    public View initView() {
        view = View.inflate(context,R.layout.pager_maininfo,null);
        ViewUtils.inject(this,view);

        initTitleBar("信息");

        initData();
        return view;
    }

    @Override
    public void initData() {

        ServiceManager.UpdateDB(InnoFarmConstant.FetchTime, updateDBSuccess(), failureCallBack());

        ArrayList<String> list = new ArrayList<>();
        list.add("档案查询");
        list.add("牧场概况");
        list.add("繁育报告");
        list.add("操作记录");

        lv.setAdapter(new Adapter<String>(context, list, R.layout.adapter_maininfo_lv) {
            @Override
            public void convert(ViewHolder helper, String item) {

                helper.setText(R.id.tv_maininfo_lvitem, item);

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(context, FileQueryActivity.class);
                        context.startActivity(intent);

                        break;
                    case 1:

                        Intent intent1 = new Intent(context, FarmSurvey.class);
                        context.startActivity(intent1);

                        break;
                    case 2:

                        Intent intent2 = new Intent(context, BreedReportActivity.class);
                        context.startActivity(intent2);
                        break;
                    case 3:

                        Intent intent3 = new Intent(context, OperationRecordsActivity.class);
                        context.startActivity(intent3);
                        break;

                }

            }
        });

    }





    /**
     * updateDB请求成功
     */
    private SuccessRequestCallBack<String> updateDBSuccess() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(final String responseInfo) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FetchAllResult all = UIUtils.json2str(responseInfo, FetchAllResult.class);
                        String staus = all.getReturn_sts();
                        InnoFarmConstant.FetchTime = all.getDate();

                        if (staus.equals("0")) {

                        /*    for (EventModel e1 : all.getLogList()) {
                                InnoFormDB.save(e1);
                            }
                            for (EventDef e2 : all.getEdList()) {
                                InnoFormDB.save(e2);
                            }
                            for (E_C_RESP e3 : all.getEcrList()) {
                                InnoFormDB.save(e3);
                            }*/
                            for (CattleModel cattle : all.getCiList()) {
                                InnoFormDB.save(cattle);
                            }
                            for (CattleAddInfoModel info : all.getCiaList()) {
                                InnoFormDB.save(info);
                            }

                        } else {

                        }

                    }
                }).start();

            }
        };
    }


    /**
     * 请求失败
     */

    private FailureRequestCallBack failureCallBack() {
        return new FailureRequestCallBack() {

            @Override
            public void onFailure(HttpException error, String msg) {
                // Toast.makeText(context, "错误" + msg, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
            }
        };

    }
}
