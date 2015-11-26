package com.innofarm.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.innofarm.InnoFarmConstant;
import com.innofarm.R;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.external.ViewHolder;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.HeatEventRemindInfo;
import com.innofarm.model.PersonalRemindInfo;
import com.innofarm.protocol.HeatEventRemindResult;
import com.innofarm.protocol.PersonalReminderResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import app.com.swipemenulistview.SwipeMenu;
import app.com.swipemenulistview.SwipeMenuCreator;
import app.com.swipemenulistview.SwipeMenuItem;
import app.com.swipemenulistview.SwipeMenuListView;

/**
 * 任务列表界面
 */
public class RemindContentActivity extends BaseActivity {
    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;
    @ViewInject(R.id.lv_remind_slide)
    SwipeMenuListView slv;


    DisplayMetrics dm;
    List<String> infos = new ArrayList<String>();
    private AppAdapter mAdapter;
    private List<HeatEventRemindInfo> mList;

    ProgressDialog pdl;
    ArrayList<PersonalRemindInfo> list;


    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_remindcontent, null);
        ViewUtils.inject(this, view);
        setContentView(view);
    }

    @Override
    protected void initView() {

        final String name = getIntent().getStringExtra("name");
        txt_title.setText(name);
        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*List list = new ArrayList();
        list.add("haha");*/


       /* for (int i = 0; i < 5; i++) {
            infos.add("info  " + i);
        }*/


        pdl = new ProgressDialog(this);
        pdl.setMessage("加载中");
        pdl.setCancelable(false);
        pdl.show();

        switch (name) {
            case "发情监测":
                ServiceManager.HeatEventRemind(heatEventRemind(), failrequest());
                break;
            case "配种操作":
                ServiceManager.MatingEventRemind(heatEventRemind(), failrequest());
                break;
            case "妊检操作":
                ServiceManager.PregnancyEventRemind(heatEventRemind(), failrequest());
                break;
            case "干奶操作":
                ServiceManager.GanNaiEventRemind(heatEventRemind(), failrequest());
                break;
            case "围产（前）操作":
                ServiceManager.BeforeWeiChanEventRemind(heatEventRemind(), failrequest());
                break;
            case "产犊操作":
                ServiceManager.CalvingEventRemind(heatEventRemind(), failrequest());
                break;
            case "围产（后）操作":
                ServiceManager.AfaterWeiChanHeatEventRemind(heatEventRemind(), failrequest());
                break;
            case "个人提醒":
                ServiceManager.GetPersonalReminder(InnoFarmConstant.UserID, personalReminderRequest(), failrequest());
                break;
            case "系统提醒":
                //  ServiceManager.AfaterWeiChanHeatEventRemind(heatEventRemind(), failrequest());
                break;


        }


        //mAppList = new ArrayList<>();


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
               /* openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));*/
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                /*openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);*/
                // add to menu
                openItem.setIcon(R.drawable.slide_niuzhidangan);
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                /*deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));*/
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.slide_guanbi);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

if(!name.equals("个人提醒")) {
    // set creator
    slv.setMenuCreator(creator);

    // step 2. listener item click event
    slv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
        @Override
        public void onMenuItemClick(int position, SwipeMenu menu, int index) {
            // ApplicationInfo item = mAppList.get(position);
            HeatEventRemindInfo item = mList.get(position);

            switch (index) {
                case 0:

                    Intent intent = new Intent(RemindContentActivity.this, CattleFileActivity.class);
                    intent.putExtra("cattleId", item.getCattleId());
                    intent.putExtra("comefrom", name);
                    startActivity(intent);

                    break;
                case 1:

                    closeItemDialog(position);

                    break;
            }
        }
    });
}


        // set SwipeListener
        slv.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }
            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });


        // test item long click
        slv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                return false;
            }
        });

        //item click
        slv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(name.equals("个人提醒")){

                    Intent intent = new Intent(RemindContentActivity.this, PersonalRemindContentActivity.class);
                    intent.putExtra("shijian",list.get(position).alertTime);
                    intent.putExtra("didian",list.get(position).address);
                    intent.putExtra("miaoshu",list.get(position).descript);
                    intent.putExtra("eventID",list.get(position).eventId);

                    startActivity(intent);
                    finish();
                    return;
                }


                HeatEventRemindInfo item = mList.get(position);

                Intent intent = new Intent(RemindContentActivity.this, OperationActivity.class);

                switch (name) {
                    case "发情监测":
                        intent.putExtra("comefrom", "发情");
                        break;
                    case "配种操作":
                        intent.putExtra("comefrom", "配种");
                        break;
                    case "妊检操作":
                        intent.putExtra("comefrom", "妊检");
                        break;
                    case "干奶操作":
                        intent.putExtra("comefrom", "干奶");
                        break;
                    case "围产（前）操作":
                        intent.putExtra("comefrom", "牛只转移");
                        break;
                    case "产犊操作":
                        intent.putExtra("comefrom", "产犊");
                        break;
                    case "围产（后）操作":
                        intent.putExtra("comefrom", "牛只转移");
                        break;
                }

                intent.putExtra("cattleNo", item.getCattleNo());
                startActivity(intent);
            }
        });

    }


    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public HeatEventRemindInfo getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_list_app, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            HeatEventRemindInfo item = getItem(position);

            holder.iv_icon.setText(item.cattleNo);
            holder.iv_icon.setTextColor(Color.BLACK);
            holder.tv_name.setText(item.alertDate);
            holder.tv_name.setTextColor(Color.BLACK);
            holder.details.setText(item.alertType + "  " + item.alertReason);
            holder.details.setTextColor(Color.BLACK);

            return convertView;
        }

        class ViewHolder {
            TextView iv_icon;
            TextView tv_name;
            TextView details;

            public ViewHolder(View view) {
                iv_icon = (TextView) view.findViewById(R.id.reminder_service);
                tv_name = (TextView) view.findViewById(R.id.service_time);
                details = (TextView) view.findViewById(R.id.service_details);
                view.setTag(this);
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    /**
     * 请求发情事件提醒
     */
    private SuccessRequestCallBack<String> heatEventRemind() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(String responseInfo) {

                String str = responseInfo;
                HeatEventRemindResult result = UIUtils.json2str(str, HeatEventRemindResult.class);
                String status = result.getReturn_sts();
                mList = result.getCntList();
                pdl.dismiss();

                mAdapter = new AppAdapter();
                slv.setAdapter(mAdapter);

            }
        };
    }

    /**
     * 请求个人提醒
     */
    private SuccessRequestCallBack<String> personalReminderRequest() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(String responseInfo) {

                String str = responseInfo;
                PersonalReminderResult result = UIUtils.json2str(str, PersonalReminderResult.class);
                String status = result.getReturn_sts();

                list = result.getFinalList();
                pdl.dismiss();


                slv.setAdapter(new Adapter<PersonalRemindInfo>(getApplicationContext(), list, R.layout.item_list_app) {
                    @Override
                    public void convert(ViewHolder helper, PersonalRemindInfo item) {
                        TextView view1 = helper.getView(R.id.reminder_service);//时间
                        TextView view2 = helper.getView(R.id.service_number);//描述
                        TextView view3 = helper.getView(R.id.service_details);//地点
                        TextView view4 = helper.getView(R.id.service_time);
                        view1.setTextColor(Color.BLACK);
                        view2.setTextColor(Color.BLACK);
                        view3.setTextColor(Color.BLACK);
                        view4.setVisibility(View.INVISIBLE);




                        helper.setText(R.id.reminder_service, item.alertTime);
                        helper.setText(R.id.service_number, "描述:  "+item.descript);
                        helper.setText(R.id.service_details,"地点： "+ item.address);
                    }
                });


            }
        };
    }


    private FailureRequestCallBack failrequest() {
        return new FailureRequestCallBack() {
            @Override
            public void onFailure(HttpException error, String msg) {
                String str = msg;

                pdl.dismiss();
            }
        };
    }


    /**
     * 关闭item对话框
     *
     * @param
     */

    private void closeItemDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        //builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认删除?"); //设置内容
        //builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog


                //TODO  删除Item请求
                mList.remove(position);
                mAdapter.notifyDataSetChanged();


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();
    }


}
