package com.innofarm.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.innofarm.InnoFarmConstant;
import com.innofarm.R;
import com.innofarm.adapter.MainPagerAdapter;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.LazyViewPager;
import com.innofarm.external.MyViewPager;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.NiuQunSurveyInfo;
import com.innofarm.pager.BasePager;
import com.innofarm.pager.SurveyNiuQunPager;
import com.innofarm.protocol.FarmSurxeyResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by dell on 2015/11/8.
 */
public class FarmSurvey extends BaseActivity {

    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;

    @ViewInject(R.id.rb_niuqun_gaikuang)
    RadioButton rb_nqgk;
    @ViewInject(R.id.rb_qinruniu_tongji)
    RadioButton rb_qrntj;

    @ViewInject(R.id.iv_niuqun_gaikuang)
    ImageView iv_nqgk;
    @ViewInject(R.id.iv_qinruniu_tongji)
    ImageView iv_qrntj;

    @ViewInject(R.id.viewpager_cattlesurvey)
    MyViewPager viewPager;
    @ViewInject(R.id.radiogroup_cattlesurvey)
    RadioGroup radiogroup;


    ArrayList tongjiList;
    ArrayList<NiuQunSurveyInfo> gaikuangList;
    ArrayList<BasePager> pagerList;

    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_farmsurvey, null);
        ViewUtils.inject(this, view);
        setContentView(view);

    }

    @Override
    protected void initView() {

        txt_title.setText("牧场概况");

        //请求牧场概况
        ServiceManager.RequestCattleSurvey(InnoFarmConstant.UserID, getCattleSurvey(), failrequest());


        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {

                switch (id) {
                    case R.id.rb_niuqun_gaikuang:
                        iv_nqgk.setVisibility(View.VISIBLE);
                        iv_qrntj.setVisibility(View.INVISIBLE);
                        viewPager.setCurrentItem(0);


                        break;
                    case R.id.rb_qinruniu_tongji:
                        iv_nqgk.setVisibility(View.INVISIBLE);
                        iv_qrntj.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(1);

                        break;

                }

            }
        });
        viewPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
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
        radiogroup.check(R.id.rb_niuqun_gaikuang);

    }

    private void initPager() {

        pagerList = new ArrayList<BasePager>();
        //pagerList.add(new InfoEventPager(this, eventList));
        pagerList.add(new SurveyNiuQunPager(this, gaikuangList));
        // pagerList.add(new SurveyQinRuNiuPager(this, tongjiList));
        viewPager.setAdapter(new MainPagerAdapter(this, pagerList));

    }


    /**
     * 请求发情事件提醒
     */
    private SuccessRequestCallBack<String> getCattleSurvey() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(String responseInfo) {

                String s = responseInfo;

                FarmSurxeyResult result = UIUtils.json2str(responseInfo, FarmSurxeyResult.class);
                gaikuangList = result.getSurveyList();

                initPager();


            }
        };
    }


    private FailureRequestCallBack failrequest() {
        return new FailureRequestCallBack() {
            @Override
            public void onFailure(HttpException error, String msg) {
                String str = msg;


            }
        };
    }


}
