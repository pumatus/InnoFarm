package com.innofarm.pager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.innofarm.InnoFarmConstant;
import com.innofarm.MainActivity;
import com.innofarm.R;
import com.innofarm.activity.OperationActivity;
import com.innofarm.activity.TransitActivity;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.MyRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.external.ViewHolder;
import com.innofarm.manager.InnoFormDB;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.CalveModel;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.E_C_RESP;
import com.innofarm.model.EventDef;
import com.innofarm.model.EventModel;
import com.innofarm.protocol.FetchAllResult;
import com.innofarm.protocol.LoginInfo;
import com.innofarm.protocol.LoginResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: syc
 * Date: 2015/9/6
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:主页--操作
 */
public class MainOperationPager extends BasePager {
    public MainOperationPager(Context context) {
        super(context);
    }


    @ViewInject(R.id.mainwork_grid1)
    private GridView grid1;
    @ViewInject(R.id.mainwork_grid2)
    private GridView grid2;

    List<Temp<String, Integer>> list1 = new ArrayList<Temp<String, Integer>>();
    List<Temp<String, Integer>> list2 = new ArrayList<Temp<String, Integer>>();

    ProgressDialog pdl;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.pager_mainoperation, null);
        ViewUtils.inject(this, view);


        return view;
    }

    @Override
    public void initData() {


        pdl = new ProgressDialog(context);
        pdl.setMessage("加载中...");
        pdl.setCancelable(false);
        // pdl.show();

        //Fetch All请求
        // ServiceManager.FetchAll(successCallBack(), failureCallBack());

        //String time = "2014-10-29 14:58:17";
        ServiceManager.UpdateDB(InnoFarmConstant.FetchTime, updateDBSuccess(), failureCallBack());

        initTitleBar("操作面板");


        list1.clear();
        list2.clear();
        String[] str1 = {"发情", "配种", "妊检", "干奶", "产犊",};
        Integer[] in1 = {R.drawable.faqing, R.drawable.peizhong, R.drawable.renjian, R.drawable.gannai, R.drawable.chandu,};
        list1 = getTemplist(str1, in1, list1);


        String[] str2 = {"牛只建档", "牛只转移", "牛只淘汰", "个人提醒", "犊牛操作",};
        Integer[] in2 = {R.drawable.jiandang, R.drawable.zhuanyi, R.drawable.taotai, R.drawable.gerentixing, R.drawable.duniucaozuo,};
        list2 = getTemplist(str2, in2, list2);


        grid1.setAdapter(new Adapter<Temp<String, Integer>>(context, list1, R.layout.adapter_workgrid) {
            @Override
            public void convert(ViewHolder helper, Temp<String, Integer> item) {
                helper.setText(R.id.adapter_workgrid_item, item.t);
                helper.setImageResource(R.id.ItemImage, item.e);
            }
        });
        grid2.setAdapter(new Adapter<Temp<String, Integer>>(context, list2, R.layout.adapter_workgrid) {
            @Override
            public void convert(ViewHolder helper, Temp<String, Integer> item) {
                helper.setText(R.id.adapter_workgrid_item, item.t);
                helper.setImageResource(R.id.ItemImage, item.e);
            }
        });

        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ViewHolder holder = ViewHolder.get(context, view, adapterView, R.layout.adapter_workgrid, position);

                Intent intent = new Intent(context, OperationActivity.class);
                intent.putExtra("comefrom", list1.get(position).t);
                context.startActivity(intent);

            }
        });

        grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ViewHolder holder = ViewHolder.get(context, view, adapterView, R.layout.adapter_workgrid, position);

                if (list2.get(position).t.equals("犊牛操作")) {
                    Intent intent = new Intent(context, TransitActivity.class);
                    intent.putExtra("tag", "calve");
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, OperationActivity.class);
                    intent.putExtra("comefrom", list2.get(position).t);
                    context.startActivity(intent);
                }
            }
        });


        // pdl.dismiss();

    }


    /**
     * 临时内部类，填充listView所需资源
     */
    class Temp<T, E> {
        public T t;
        public E e;
    }

    /**
     * 获取填充listview的Templist
     *
     * @param s 操作名称
     * @param i 图标
     */

    public List getTemplist(String[] s, Integer[] i, List list) {
        for (int x = 0; x < s.length; x++) {
            Temp<String, Integer> temp = new Temp<String, Integer>();
            temp.t = s[x];
            temp.e = i[x];
            list.add(temp);
        }
        return list;
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

                        pdl.dismiss();

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
                pdl.dismiss();

            }
        };

    }


    /**
     * 将JSon转换成字符串
     */
    public static <T> T json2str(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("exception", e.toString());

        }
        return t;
    }

}
