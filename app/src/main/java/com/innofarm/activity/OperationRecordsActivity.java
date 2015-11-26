package com.innofarm.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.innofarm.R;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.external.ViewHolder;
import com.innofarm.external.XListView.XListView;
import com.innofarm.manager.InnoFormDB;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.CattleModel;
import com.innofarm.model.OperationRecordsInfo;
import com.innofarm.model.PersonalRemindInfo;
import com.innofarm.protocol.FetchAllResult;
import com.innofarm.protocol.OperationRecordsResult;
import com.innofarm.protocol.PersonalReminderResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wwj.datetimepicker.DateTimePickDialogUtil;

import java.util.ArrayList;
import java.util.List;


import java.util.logging.LogRecord;

/**
 * Created by dell on 2015/11/3.
 */
public class OperationRecordsActivity extends BaseActivity implements XListView.IXListViewListener {


    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;
    @ViewInject(R.id.lv_timeselect)
    ListView timeselect;
    @ViewInject(R.id.et_operationrecord)
    EditText et;
    @ViewInject(R.id.btn_operationrecord)
    Button btn;

    @ViewInject(R.id.lv_operationrecods)
    XListView recordslv;


    List templist = new ArrayList();//Temp 集合
    private String initDateTime = "2012年9月3日 14:44";//初始日期时间值 在点击事件中使用
    Adapter myadapter;

    ProgressDialog pdl;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            OperationRecordsResult result = (OperationRecordsResult) msg.obj;


            ArrayList<OperationRecordsInfo> list = result.getFinalList();


            recordslv.setPullLoadEnable(true);
            recordslv.setPullRefreshEnable(false);
            recordslv.setXListViewListener(OperationRecordsActivity.this, 0);
            recordslv.setRefreshTime();

            recordslv.setAdapter(new Adapter<OperationRecordsInfo>(getApplicationContext(), list, R.layout.item_list_app) {
                @Override
                public void convert(ViewHolder helper, OperationRecordsInfo item) {
                    TextView view1 = helper.getView(R.id.reminder_service);
                    TextView view2 = helper.getView(R.id.service_time);
                    TextView view3 = helper.getView(R.id.service_details);
                    view1.setTextColor(Color.BLACK);
                    view2.setTextColor(Color.BLACK);
                    view3.setTextColor(Color.BLACK);

                    helper.setText(R.id.reminder_service, item.cattleNo);
                    helper.setText(R.id.service_time, item.eventTime);
                    helper.setText(R.id.service_details, item.eventDetail);


                }
            });
            super.handleMessage(msg);
        }
    };


    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_operationrecords, null);
        ViewUtils.inject(this, view);
        setContentView(view);

    }

    @Override
    protected void initView() {

        txt_title.setText("操作记录");
        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //填充listView
        fillListView();


        timeselect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int position, long l) {

                ViewHolder holder = ViewHolder.get(OperationRecordsActivity.this, view, adapterView, R.layout.adapter_operation_lv, position);
                Temp<String, Integer> item = (Temp) templist.get(position);
                String eventname = item.t;


                if (eventname.contains("开始时间") | eventname.contains("结束时间")) {
                    TextView time_tv_content = holder.getView(R.id.operation_lv_item_tv_content);
                    DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(OperationRecordsActivity.this, initDateTime);
                    dateTimePicKDialog.dateTimePicKDialog(time_tv_content);
                    time_tv_content.setVisibility(View.VISIBLE);
                    myadapter.notifyDataSetChanged();
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operator = et.getText().toString();
                TextView tvstart = (TextView) timeselect.getChildAt(0).findViewById(R.id.operation_lv_item_tv_content);
                TextView tvend = (TextView) timeselect.getChildAt(1).findViewById(R.id.operation_lv_item_tv_content);

                String start = tvstart.getText().toString();
                String end = tvend.getText().toString();


                pdl = new ProgressDialog(OperationRecordsActivity.this);
                pdl.setMessage("加载中");
                pdl.setCancelable(false);
                pdl.show();




                ServiceManager.GetOperationRecords(operator, end, start, Success(), failureCallBack());
            }
        });


    }


    private void fillListView() {

        //  lv.removeAllViewsInLayout();

        String[] s = {"开始时间", "结束时间"};
        Integer[] i = {R.drawable.image1};
        templist = getTemplist(s, i, templist);

        myadapter = new Adapter<Temp<String, Integer>>(this, templist, R.layout.adapter_operation_lv) {
            @Override
            public void convert(ViewHolder helper, Temp<String, Integer> item) {
                helper.setText(R.id.operation_lv_item_tv, item.t);
                helper.setImageResource(R.id.operation_lv_item_iv, item.e);
            }
        };

        timeselect.setAdapter(myadapter);

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
            temp.e = i[0];
            list.add(temp);
        }
        return list;
    }



    //listView刷新
    @Override
    public void onRefresh(int id) {

    }

    //listView加载
    @Override
    public void onLoadMore(int id) {

    }



    /**
     * 临时内部类，填充listView所需资源
     */
    class Temp<T, E> {
        public T t;
        public E e;
    }


    /**
     * updateDB请求成功
     */
    private SuccessRequestCallBack<String> Success() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(final String responseInfo) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String str = responseInfo;
                        OperationRecordsResult result = UIUtils.json2str(str, OperationRecordsResult.class);
                        String status = result.getReturn_sts();

                        if(status.equals("0")||result.finalList.size()!=0){
                        Message msg = new Message();
                        msg.obj = result;
                        handler.sendMessage(msg);}


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
                pdl.dismiss();
                //Toast.makeText(OperationRecordsActivity.this, "错误" + msg, Toast.LENGTH_SHORT).show();
                Toast.makeText(OperationRecordsActivity.this, "请求未响应", Toast.LENGTH_SHORT).show();

            }
        };
    }


}
