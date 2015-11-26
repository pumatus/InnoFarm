package com.innofarm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.innofarm.R;
import com.innofarm.adapter.MainPagerAdapter;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.LazyViewPager;
import com.innofarm.external.MyViewPager;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.CattleFileEventInfo;
import com.innofarm.model.CattleFileInfo;
import com.innofarm.model.EventAlertModel;
import com.innofarm.pager.BasePager;
import com.innofarm.pager.InfoEventPager;
import com.innofarm.pager.InfoPredictPager;
import com.innofarm.protocol.CattleFileResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 牛只档案
 */
public class CattleFileActivity extends BaseActivity {


    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;
    @ViewInject(R.id.btn_right)
    Button tv_right;


    @ViewInject(R.id.tv_cattlefile_cattleNo)
    TextView cattleNo;
    @ViewInject(R.id.tv_cattlefile_cattleBir)
    TextView cattleBir;
    @ViewInject(R.id.tv_cattlefile_cattleBreed)
    TextView cattleBreed;
    @ViewInject(R.id.tv_cattlefile_cattleFa)
    TextView cattleFa;
    @ViewInject(R.id.tv_cattlefile_cattleMa)
    TextView cattleMa;
    @ViewInject(R.id.tv_cattlefile_cattleSex)
    TextView cattleSex;
    @ViewInject(R.id.tv_cattlefile_cattleSource)
    TextView cattleSource;

    @ViewInject(R.id.tv_cattlefile_Cowshed)
    TextView cowshed;
    @ViewInject(R.id.tv_cattlefile_birthOrder)
    TextView birthOrder;
    @ViewInject(R.id.tv_cattlefile_fanyuzhuangtai)
    TextView fanyuzhuangtai;
    @ViewInject(R.id.tv_cattlefile_MonthofAge)
    TextView MonthOfAge;
    @ViewInject(R.id.tv_cattlefile_numberOfBreed)
    TextView numberOfbreed;

    @ViewInject(R.id.radio_cattlefile)
    RadioGroup info_radio;
    @ViewInject(R.id.radio_cattlefile_event)
    RadioButton radio_event;
    @ViewInject(R.id.radio_cattlefile_predict)
    RadioButton radio_predict;
    @ViewInject(R.id.cattlefile_pager)
    MyViewPager info_pager;

    @ViewInject(R.id.cattleinfo_img1)
    ImageView img1;
    @ViewInject(R.id.cattleinfo_img2)
    ImageView img2;


    ArrayList<BasePager> pagerList;

    ArrayList<CattleFileEventInfo> eventList;

    ArrayList<EventAlertModel> predictList;

    ProgressDialog pdl;
    PopupWindow popupWindow;


    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_cattlefile, null);
        ViewUtils.inject(this, view);
        setContentView(view);

    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        String cattleId = intent.getStringExtra("cattleId");
        String name = intent.getStringExtra("comefrom");


        //final String calveID = getIntent().getStringExtra("calveId");
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        final View popupWindow_view = getLayoutInflater().inflate(R.layout.popuwindow_cattlefile, null, false);
        Button chanDu = (Button) popupWindow_view.findViewById(R.id.operaion_chandu);
        Button faQing = (Button) popupWindow_view.findViewById(R.id.operaion_faqing);
        Button ganNai = (Button) popupWindow_view.findViewById(R.id.operaion_gannai);
        Button peizhong = (Button) popupWindow_view.findViewById(R.id.operaion_peizhong);
        Button renJian = (Button) popupWindow_view.findViewById(R.id.operaion_renjian);
        Button taoTai = (Button) popupWindow_view.findViewById(R.id.operaion_taotai);
        Button zhuanYi = (Button) popupWindow_view.findViewById(R.id.operaion_zhuanyi);


        chanDu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CattleFileActivity.this, OperationActivity.class);
                intent.putExtra("comefrom", "产犊");
                //intent.putExtra("calveId", calveID);
                startActivity(intent);
                finish();
            }
        });
        faQing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(CattleFileActivity.this, OperationActivity.class);
                intent2.putExtra("comefrom", "发情");
                //intent2.putExtra("calveId", calveID);
                startActivity(intent2);
                finish();
            }
        });
        peizhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CattleFileActivity.this, OperationActivity.class);
                intent.putExtra("comefrom", "配种");
                // intent.putExtra("calveId", calveID);
                startActivity(intent);
                finish();
            }
        });
        renJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(CattleFileActivity.this, OperationActivity.class);
                intent2.putExtra("comefrom", "妊检");
                //intent2.putExtra("calveId", calveID);
                startActivity(intent2);
                finish();
            }
        });
        ganNai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CattleFileActivity.this, OperationActivity.class);
                intent.putExtra("comefrom", "干奶");
                //intent.putExtra("calveId", calveID);
                startActivity(intent);
                finish();
            }
        });
        zhuanYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(CattleFileActivity.this, OperationActivity.class);
                intent2.putExtra("comefrom", "牛只转移");
                //intent2.putExtra("calveId", calveID);
                startActivity(intent2);
                finish();
            }
        });
        taoTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CattleFileActivity.this, OperationActivity.class);
                intent.putExtra("comefrom", "牛只淘汰");
                //intent.putExtra("calveId", calveID);
                startActivity(intent);
                finish();
            }
        });

        chanDu.setVisibility(View.GONE);
        faQing.setVisibility(View.GONE);
        peizhong.setVisibility(View.GONE);
        renJian.setVisibility(View.GONE);
        ganNai.setVisibility(View.GONE);
        zhuanYi.setVisibility(View.GONE);
        taoTai.setVisibility(View.GONE);


        if (name.contains("发情")) {
            faQing.setVisibility(View.VISIBLE);


        } else if (name.contains("配种")) {
            peizhong.setVisibility(View.VISIBLE);


        } else if (name.contains("产犊")) {
            chanDu.setVisibility(View.VISIBLE);


        } else if (name.contains("妊检")) {
            renJian.setVisibility(View.VISIBLE);


        } else if (name.contains("干奶")) {
            ganNai.setVisibility(View.VISIBLE);


        } else if (name.contains("围产")) {
            zhuanYi.setVisibility(View.VISIBLE);


        } else {
            chanDu.setVisibility(View.VISIBLE);
            faQing.setVisibility(View.VISIBLE);
            peizhong.setVisibility(View.VISIBLE);
            renJian.setVisibility(View.VISIBLE);
            ganNai.setVisibility(View.VISIBLE);
            zhuanYi.setVisibility(View.VISIBLE);
            taoTai.setVisibility(View.VISIBLE);
        }

        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("操作");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popuwindow
                getPopupWindow(popupWindow_view);
                popupWindow.showAsDropDown(tv_right);
            }
        });


        //请求网络 获取信息

        pdl = new ProgressDialog(this);
        pdl.setMessage("加载中");
        pdl.setCancelable(false);
        pdl.show();

        ServiceManager.GetCattleFile(cattleId, getCattleFilesuccess(), failrequest());


        txt_title.setText("牛只档案");
        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //  initPager();


        info_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {

                switch (id) {
                    case R.id.radio_cattlefile_event:
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.INVISIBLE);
                        info_pager.setCurrentItem(0);


                        break;
                    case R.id.radio_cattlefile_predict:
                        img1.setVisibility(View.INVISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        info_pager.setCurrentItem(1);

                        break;

                }

            }
        });
        info_pager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
                                               @Override
                                               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                               }

                                               @Override
                                               public void onPageSelected(int position) {
                                                   pagerList.get(position).initData();

                                               }

                                               @Override
                                               public void onPageScrollStateChanged(int state) {

                                               }
                                           }

        );
        info_radio.check(R.id.radio_cattlefile_event);

    }

    private void initPager() {


        pagerList = new ArrayList<BasePager>();
        //pagerList.add(new InfoEventPager(this, eventList));
        pagerList.add(new InfoEventPager(this, eventList));
        pagerList.add(new InfoPredictPager(this, predictList));
        info_pager.setAdapter(new MainPagerAdapter(this, pagerList));

    }


    /**
     * 请求发情事件提醒
     */
    private SuccessRequestCallBack<String> getCattleFilesuccess() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(String responseInfo) {


                CattleFileResult result = UIUtils.json2str(responseInfo, CattleFileResult.class);

                eventList = result.getcList();
                predictList = result.getEventAlertList();
                ArrayList<CattleFileInfo> cattleList = result.getCattleInfo();
                CattleFileInfo info = cattleList.get(0);

                cattleNo.setText(info.getCattleNo());
                cattleBir.setText("出生日期：" + info.getCattlebrt());
                cattleBreed.setText("品种：" + info.getVarieties());
                cattleFa.setText("父系：" + info.getFather());
                cattleMa.setText("母系：" + info.getMother());
                cattleSex.setText("性别：：" + info.getCattleSex());
                cattleSource.setText("来源：" + info.getSource());

                cowshed.setText("牛舍：" + info.getBarnName());
                birthOrder.setText("胎次: " + info.getCalvingNo());
                fanyuzhuangtai.setText("繁育状态: " + info.getBreedSt());
                MonthOfAge.setText("月龄: " + info.getCattleAge());
                //numberOfbreed.setText("配次"+info.);


                //填充pager
                initPager();
                pdl.dismiss();


            }
        };
    }


    private FailureRequestCallBack failrequest() {
        return new FailureRequestCallBack() {
            @Override
            public void onFailure(HttpException error, String msg) {
                String str = msg;
                Toast.makeText(CattleFileActivity.this,"请求信息失败，请重试",Toast.LENGTH_SHORT).show();
                finish();
                pdl.dismiss();

            }
        };
    }


    /**
     * 初始化popupwindw
     */

    private void initPopupWindow(View view) {


        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(view, 300, RadioGroup.LayoutParams.WRAP_CONTENT, true);
        // 点击其他地方消失
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    /**
     * 获取PopupWindow实例
     */
    private void getPopupWindow(View view) {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopupWindow(view);
        }
    }


}
