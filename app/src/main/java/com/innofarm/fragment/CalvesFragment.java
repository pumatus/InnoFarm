package com.innofarm.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.innofarm.R;
import com.innofarm.activity.OperationActivity;
import com.innofarm.activity.TransitActivity;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.ViewHolder;
import com.innofarm.manager.InnoFormDB;
import com.innofarm.model.CalveModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.E_C_RESP;
import com.innofarm.model.EventDef;
import com.innofarm.model.EventModel;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.zip.Inflater;

/**
 * Created by dell on 2015/10/14.
 */
public class CalvesFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;
    @ViewInject(R.id.lv_calve)
    ListView lv_calve;
    @ViewInject(R.id.btn_right)
    Button btn_right;

    PopupWindow popupWindow;

    @ViewInject(R.id.calve_radio)
    RadioGroup calve_radio;

    DbUtils dbUtils;
    Adapter<CalveModel> calveadapter;
    ArrayList<CalveModel> calvelist = new ArrayList<CalveModel>();
    ArrayList<CalveModel> templist = new ArrayList<CalveModel>();
    String calveID;


    public static Fragment instance() {
        return new CalvesFragment();
    }

    @Override
    public View initView() {

        view = View.inflate(context, R.layout.fragment_calves, null);
        ViewUtils.inject(this, view);

        dbUtils = InnoFormDB.getDbUtils();

        /*dbUtils = DbUtils.create(getActivity(), "InfaDB");

        ProgressDialog pdg =new ProgressDialog(context);
        pdg.setCancelable(false);
        pdg.setMessage("加载数据库");
        pdg.show();


        pdg.dismiss();*/


        return view;

    }


    @Override
    public void initData() {
        //操作
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setText("操作");
        btn_right.setOnClickListener(this);
        //title
        txt_title.setText("犊牛操作");
        //返回
        imgbtn_left.setOnClickListener(this);
        //操作
        btn_right.setText("操作");
        btn_right.setOnClickListener(this);


        //选择时间段
        calve_radio.setOnCheckedChangeListener(this);


        calve_radio.check(R.id.rb_week);


        // calvelist = findALL(CalveModel.class,"","","");


        lvSetAdapter();

        lvSetOnItemClick();


    }


    /**
     * listView点击
     */

    private void lvSetOnItemClick() {

        lv_calve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(context, OperationActivity.class);
                intent.putExtra("comefrom", "犊牛操作");
                String id = calvelist.get(position).calvesId;
                intent.putExtra("calveId", id);
                startActivity(intent);


                ViewHolder holder = ViewHolder.get(getActivity(), view, adapterView, R.layout.adapter_calve_lv, position);
                final RadioButton radio = holder.getView(R.id.rb_calve);
                radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        });
    }


    /**
     * listView set adapter
     */
    private void lvSetAdapter() {
        calveadapter = new Adapter<CalveModel>(getActivity(), calvelist, R.layout.adapter_calve_lv) {

            HashMap<String, Boolean> states = new HashMap<String, Boolean>();
            int mCheckedPosition = -1;

            @Override
            public void convert(ViewHolder helper, final CalveModel item) {


                final int position = calvelist.indexOf(item);


                E_C_RESP resp = InnoFormDB.find(E_C_RESP.class, "eventId", "=", item.eventId);
                CattleModel cattle = InnoFormDB.find(CattleModel.class, "cattleId", "=", resp.cattleId);


                helper.setText(R.id.tv_calve_cattlenum, cattle.getCattleNo());
                helper.setText(R.id.tv_calve_date, item.calvesBir);
                helper.setText(R.id.tv_calve_calveno, item.calvesNo);

                final RadioButton radio = helper.getView(R.id.rb_calve);
                radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        calveID = item.calvesId;
                        mCheckedPosition = position;
                        notifyDataSetChanged();
                    }
                });

                radio.setChecked(position == mCheckedPosition);


            }
        };

        lv_calve.setAdapter(calveadapter);

    }


    /**
     * 点击事件
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_left:
                TransitActivity finish = (TransitActivity) getActivity();
                finish.finish();
                break;
            case R.id.btn_right:
                getPopupWindow();
                popupWindow.showAsDropDown(view);
                break;
            case R.id.add_cattle:
                if (calveID == null) {
                    Toast.makeText(context, "无选中犊牛", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "添加", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, OperationActivity.class);
                intent.putExtra("comefrom", "牛只建档");
                intent.putExtra("calveId", calveID);
                startActivity(intent);
                break;
            case R.id.die_out:
                if (calveID == null) {
                    Toast.makeText(context, "无选中犊牛", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "淘汰", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(context, OperationActivity.class);
                intent2.putExtra("comefrom", "牛只淘汰");
                intent2.putExtra("calveId", calveID);
                startActivity(intent2);
                break;

        }

    }

    /**
     * 初始化popupwindw
     */

    private void initPopupWindow() {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater(null).inflate(R.layout.popuwindow_calvefragment, null,
                false);

        Button add = (Button) popupWindow_view.findViewById(R.id.add_cattle);
        Button dieout = (Button) popupWindow_view.findViewById(R.id.die_out);

        add.setOnClickListener(CalvesFragment.this);
        dieout.setOnClickListener(CalvesFragment.this);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, 300, RadioGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        // popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
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
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopupWindow();
        }
    }

    /**
     * radiobtn 点击
     */

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {

        switch (id) {
            case R.id.rb_week:
                calvelist.clear();

                templist = findALL(CalveModel.class, "", "", "");//保持calvelist不变
                if (templist != null) {
                    for (CalveModel calve : templist) {
                        calvelist.add(calve);
                    }
                    if (calveadapter != null)
                        calveadapter.notifyDataSetChanged();
                }

                break;
            case R.id.rb_month:


                calvelist.clear();
                if (calveadapter != null)
                    calveadapter.notifyDataSetChanged();

                break;
            case R.id.rb_all:

                break;


        }

    }


    /**
     * 数据库查找
     */
    public <T> ArrayList<T> findALL(Class<T> cls, String str, String str1, String str2) {

        try {
            return (ArrayList<T>) dbUtils.findAll(Selector.from(cls));
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }

    }


}
