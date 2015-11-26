package com.innofarm.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.innofarm.R;
import com.innofarm.activity.OperationActivity;
import com.innofarm.activity.TransitActivity;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.ViewHolder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * User: syc
 * Date: 2015/9/18
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 选择名字
 */
public class SelectNameFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.et_selectname)
    EditText selectname_et;
    @ViewInject(R.id.lv_selectname)
    ListView selectname_lv;
    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;
    @ViewInject(R.id.imgbtn_right)
    ImageButton imgbtn_right;


    String name;
    Adapter adapter;
    String arg;//操作名称

    LinkedList<String> nameList = new LinkedList();

    SharedPreferences.Editor editor;
    SharedPreferences sp;

    public static Fragment instance() {
        return new SelectNameFragment();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_selectname, null);
        ViewUtils.inject(this, view);


        Bundle bundle = getArguments();

        arg = (String) bundle.get("Arg");
        if (arg.contains("者")) {
            sp = context.getSharedPreferences("Username", context.MODE_PRIVATE);
            editor = sp.edit();
        } else if (arg.contains("品种")) {
            sp = context.getSharedPreferences("CattleBreed", context.MODE_PRIVATE);
            editor = sp.edit();
        } else if (arg.contains("地点")) {
            sp = context.getSharedPreferences("Place", context.MODE_PRIVATE);
            editor = sp.edit();
        } else if (arg.contains("系")) {
            sp = context.getSharedPreferences("Parents", context.MODE_PRIVATE);
            editor = sp.edit();
        } else if (arg.contains("冻精编号")) {
            sp = context.getSharedPreferences("FrozenNO", context.MODE_PRIVATE);
            editor = sp.edit();
        }


        return view;
    }

    @Override
    public void initData() {


        imgbtn_left.setOnClickListener(this);
        imgbtn_right.setVisibility(View.VISIBLE);
        imgbtn_right.setOnClickListener(this);
        txt_title.setText("选择");


        for (int i = 0; i < 5; i++) {
            String str = i + "";
            String name = sp.getString(str, "");
            nameList.addLast(name);
        }

        selectname_et.setText(nameList.getFirst());

        adapter = new Adapter<String>(getActivity(), nameList, R.layout.adapter_operation_lv) {
            @Override
            public void convert(ViewHolder helper, String item) {

                helper.setText(R.id.operation_lv_item_tv, item);

            }
        };
        selectname_lv.setAdapter(adapter);


        selectname_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (nameList.size() == 0) {
                    Toast.makeText(context, "请先添加", Toast.LENGTH_SHORT).show();
                } else {
                    TextView operation_lv_item_tv = (TextView) view.findViewById(R.id.operation_lv_item_tv);
                    name = operation_lv_item_tv.getText().toString();
                    set();
                    TransitActivity finish = (TransitActivity) getActivity();
                    finish.finish();
                }

            }
        });


    }

    public void set() {
        if (name == null) {
            if (nameList.size() == 0)
                name = "";
            else
                name = (String) nameList.get(0);
        }
        OperationActivity.setTv_Content(arg, name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_left:
                set();
                TransitActivity finish = (TransitActivity) getActivity();
                finish.finish();
                break;

            case R.id.imgbtn_right:
                String str = selectname_et.getText().toString();
                if (str == null || str.equals("")) {
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    nameList.addFirst(str);
                    nameList.removeLast();
                    for (int x = 0; x < 5; x++) {
                        editor.putString(x + "", nameList.get(x));
                        editor.commit();
                    }

                    adapter.notifyDataSetChanged();
                    set();
                    TransitActivity finish1 = (TransitActivity) getActivity();
                    finish1.finish();

                }


        }

    }
}
