package com.innofarm.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.innofarm.R;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.ViewHolder;
import com.innofarm.manager.InnoFormDB;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.E_C_RESP;
import com.innofarm.model.EventDef;
import com.innofarm.model.EventModel;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * Created by dell on 2015/11/3.
 */
public class FileQueryActivity extends BaseActivity {


    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;

    @ViewInject(R.id.et_filequery)
    TextView et;
    @ViewInject(R.id.lv_filequery)
    ListView lv;

    private DbUtils dbUtils;
    List<CattleModel> cattleList;

    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_filequery, null);
        ViewUtils.inject(this, view);

        dbUtils = InnoFormDB.getDbUtils();
        setContentView(view);

    }

    @Override
    protected void initView() {


        txt_title.setText("档案查询");
        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //检索牛只  Edittext监听
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //刷新listView
               /* templist.clear();
                fillListView();
                myadapter.notifyDataSetChanged();*/

                if (editable.length() == 0) {
                    //当文本框为空时
                    try {
                        cattleList = dbUtils.findAll(CattleModel.class);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                } else {
                    //当文本框不为空时
                    try {


                        cattleList = dbUtils.findAll(Selector.from(CattleModel.class).where("cattleNo", "like", editable.toString()+"%"));


                        //listView的填充

                        lvSetAdapter();


                    } catch (Exception e) {

                        // Toast.makeText(FileQueryActivity.this, "没数据", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CattleModel item = cattleList.get(position);
                Intent intent = new Intent(FileQueryActivity.this, CattleFileActivity.class);
                intent.putExtra("cattleId",item.getCattleId());
                intent.putExtra("comefrom","档案查询");
                startActivity(intent);

            }
        });


    }

    private void lvSetAdapter() {
        lv.setAdapter(new Adapter<CattleModel>(FileQueryActivity.this,cattleList,R.layout.item_list_app) {
            @Override
            public void convert(ViewHolder helper, CattleModel item) {
                helper.setText(R.id.reminder_service,item.getCattleNo());
                helper.getView(R.id.service_time).setVisibility(View.GONE);
                //helper.getView(R.id.service_details).setVisibility(View.INVISIBLE);
                helper.getView(R.id.service_number).setVisibility(View.INVISIBLE);


                helper.setText(R.id.service_details, "");
                List<CattleAddInfoModel> infolist = null;
                try {
                    infolist = dbUtils.findAll(Selector.from(CattleAddInfoModel.class).where("cattleId", "=", item.cattleId));
                    for (CattleAddInfoModel info : infolist) {
                        if (info.infoType.contains("牛舍")) {
                            helper.setText(R.id.service_details, "牛舍:  " + info.getInfoContent());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



                //helper.setText(R.id.service_details,"月龄"+item.);


            }
        });



    }
}
