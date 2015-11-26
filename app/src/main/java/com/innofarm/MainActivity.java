package com.innofarm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.fragment.MainFragment;
import com.innofarm.manager.InnoFormDB;
import com.innofarm.manager.RequestService;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.CalveAddInfoModel;
import com.innofarm.model.CalveModel;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.E_C_RESP;
import com.innofarm.model.EventDef;
import com.innofarm.model.EventModel;
import com.innofarm.model.NumMappingModel;
import com.innofarm.protocol.FetchAllResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;

import java.util.List;


public class MainActivity extends FragmentActivity {


    ProgressDialog pdl;
    SharedPreferences sp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    pdl.setMessage("解析中...");
                    break;
                case 2:
                    pdl.setMessage("数据存储中...");
                    break;
                case 3:
                    pdl.setMessage("数据存储中...");
                    break;
                case 4:
                    pdl.setMessage("数据存储中...");
                    break;
                case 5:
                    pdl.setMessage("数据存储中...");
                    break;


            }


            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.innofarm.R.layout.activity_main);


        //创建SharedPreferences

        sp = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        //判断是否第一次登录
        boolean user_first = sp.getBoolean("first", true);//取得相应的值，如果没有该值，说明还未写入，用true作为默认值

        if (user_first) {
            //如果不是第一次登录，直接跳转到下一个界面
            pdl = new ProgressDialog(this);
            pdl.setMessage("数据同步中...");

            pdl.setCancelable(false);
            pdl.show();
            // pdl.setMessage("解析中");
            //加载所有数据
            ServiceManager.FetchAll(successCallBack(), failureCallBack());
        }
        sp.edit().putBoolean("first", false).commit();


        //开启同步服务
        Intent intent = new Intent(MainActivity.this, RequestService.class);
        startService(intent);


        //判断
        if (getIntent().getStringExtra("boss").equals("boss")) {
            RelativeLayout maintop = (RelativeLayout) findViewById(R.id.main_top);
            maintop.setVisibility(View.VISIBLE);
            TextView text = (TextView) findViewById(R.id.txt_title);
            text.setText("Boss");
            ImageButton left = (ImageButton) findViewById(R.id.imgbtn_left);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        } else if (getIntent().getStringExtra("boss").equals("")) {
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment, "main").commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.innofarm.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == com.innofarm.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void sendMsg(int flag) {
        Message msg = new Message();
        msg.what = flag;
        handler.sendMessage(msg);
    }

    /**
     * fetchAll请求成功
     */
    private SuccessRequestCallBack<String> successCallBack() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(final String responseInfo) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        // pdl1.setMessage("解析中...");

                       // sendMsg(1);
                        FetchAllResult all = UIUtils.json2str(responseInfo, FetchAllResult.class);
                        String staus = all.getReturn_sts();
                        InnoFarmConstant.FetchTime = all.getDate();

                        //InnoFormDB.dropTable();

                        if (staus.equals("0")) {
                            InnoFormDB.dropTable();
/*                            for (EventModel e1 : all.getLogList()) {
                                sendMsg(2);
                              //  pdl.setMessage("存储事件...");
                                InnoFormDB.save(e1);
                            }
                           for (EventDef e2 : all.getEdList()) {
                                InnoFormDB.save(e2);
                            }*/
                            for (E_C_RESP e3 : all.getEcrList()) {
//                                sendMsg(3);
                                // pdl.setMessage("存储牛只事件关系...");
                                InnoFormDB.save(e3);
                            }
                            for (CattleModel cattle : all.getCiList()) {
//                                sendMsg(4);
                                // pdl.setMessage("存储牛只...");
                                InnoFormDB.save(cattle);

                            }
                            for (CattleAddInfoModel info : all.getCiaList()) {
//                                sendMsg(5);
                                // pdl.setMessage("存储牛只事件关系...");
                                InnoFormDB.save(info);

                            }
                            for (CalveModel calve : all.getCirList()) {
                                // sendMsg(5);
                                // pdl.setMessage("存储牛只事件关系...");
                                InnoFormDB.save(calve);

                            }
                            for (CalveAddInfoModel info : all.getCiraList()) {
                                // sendMsg(5);
                                // pdl.setMessage("存储牛只事件关系...");
                                InnoFormDB.save(info);
                            }
                            for (NumMappingModel map : all.getcList()) {
                                // sendMsg(5);
                                // pdl.setMessage("存储牛只事件关系...");
                                InnoFormDB.save(map);
                            }

                        } else {

                        }

                        pdl.dismiss();

                    }
                }).start();

                // pdl.dismiss();
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
                // Toast.makeText(MainActivity.this, "错误" + msg, Toast.LENGTH_SHORT).show();
                pdl.dismiss();
                sp.edit().putBoolean("first", true).commit();

            }
        };

    }


}
