package com.innofarm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innofarm.InnoFarmConstant;
import com.innofarm.R;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.SelectCountDialog;
import com.innofarm.external.ViewHolder;
import com.innofarm.fragment.CalvesFragment;
import com.innofarm.manager.InnoFormDB;
import com.innofarm.manager.TestManager;
import com.innofarm.model.CalveModel;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.E_C_RESP;
import com.innofarm.model.EventDef;
import com.innofarm.model.EventModel;
import com.innofarm.model.NumMappingModel;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wwj.datetimepicker.DateTimePickDialogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * User: syc
 * Date: 2015/9/16
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:操作详情
 */
public class OperationActivity extends BaseActivity {

    @ViewInject(R.id.operation_et)
    EditText et;
    @ViewInject(R.id.tv_operation)
    TextView tv_operation;
    @ViewInject(R.id.operation_lv)
    ListView lv;
    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;
    @ViewInject(R.id.operation_top)
    RelativeLayout operation_top;
    @ViewInject(R.id.btn_right)
    Button tv_right;
    @ViewInject(R.id.cattle_inf)
    RelativeLayout cattle_inf;
    @ViewInject(R.id.cattle_parity)
    TextView cattle_parity;
    @ViewInject(R.id.cowshed)
    TextView cowshed;
    @ViewInject(R.id.prod_days)
    TextView prod_days;


    static TextView tv_content;//操作详情
    private TextView time_tv_content;//时间详情

    private List templist = new ArrayList();//Temp 集合
    private int count; //setsave
    private PopupWindow popupWindow;
    private String name;//操作名称


    static Map<String, String> eventmap = new HashMap<String, String>();//事件类型，事件内容
    private String eventname; //操作内容

    private static Adapter<Temp<String, Integer>> myadapter;
    private String initDateTime = "2012年9月3日 14:44";//初始日期时间值 在点击事件中使用
    private DbUtils dbUtils;

    //业务事件日志
    EventModel event;
    //事件详情
    EventDef eventdef;
    //事件牛只关系
    E_C_RESP resp;
    //牛只基础档案
    CattleModel cattle;
    //CattleModel entity;//
    //犊牛
    CalveModel calve;
    CalveModel showcalve;


    ArrayList<CattleAddInfoModel> cattleAddInflist;


    @Override
    protected void init() {

        View view = View.inflate(this, R.layout.activity_operation, null);
        ViewUtils.inject(this, view);
        setContentView(view);

        dbUtils = InnoFormDB.getDbUtils();


    }


    @Override
    protected void initView() {

        name = getIntent().getStringExtra("comefrom");
        //保存按钮
        tv_right.setVisibility(View.VISIBLE);

        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //犊牛操作进入时
        if (name.equals("犊牛操作")) {
            tv_operation.setText("牛只编码");
            tv_right.setText("操作");
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //popuwindow
                    getPopupWindow();
                    popupWindow.showAsDropDown(tv_right);
                }
            });
            String str = getIntent().getStringExtra("calveId");
            showcalve = find(CalveModel.class, "calvesId", "=", str);
            et.setText(showcalve.calvesNo);
            //填充listView
            fillListView();
            lisViewSetAdapter();
            return;
        } else if (name.equals("牛只建档") && getIntent().getStringExtra("calveId") != null) {
            String str = getIntent().getStringExtra("calveId");
            showcalve = find(CalveModel.class, "calvesId", "=", str);
            //填充listView
            fillListView();
            lisViewSetAdapter();


        } else if (name.equals("牛只淘汰") && getIntent().getStringExtra("calveId") != null) {
            tv_operation.setText("犊牛号");
            String str = getIntent().getStringExtra("calveId");
            showcalve = find(CalveModel.class, "calvesId", "=", str);
            et.setText(getIntent().getStringExtra("calveId"));
            //et.setEnabled(false);
            et.setInputType(InputType.TYPE_NULL);

            //填充listView
            fillListView();
            lisViewSetAdapter();

            calve = showcalve;
            cattle_inf.setVisibility(View.VISIBLE);
            cattle_parity.setText("胎次  " + "");
            cowshed.setText("牛舍  " + "");
            prod_days.setText("产后天数  " + "");

            //listView的点击
            listViewClick();


        } else {
            //填充listView
            fillListView();
            lisViewSetAdapter();
            //listVi ew的点击
            listViewClick();

        }


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
                } else {
                    //当文本框不为空时
                    if (name.equals("牛只建档")) {
                        // cattle = find(CattleModel.class, "cattleNo", "=", editable.toString());

                        try {
                            cattle = dbUtils.findFirst(Selector.from(CattleModel.class)
                                    .where("cattleNo", "=", editable.toString())
                                    .and("cattleSt", "=", "养殖中"));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }


                        if (cattle != null) {
                            Toast.makeText(OperationActivity.this, "编号重复", Toast.LENGTH_SHORT).show();
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(OperationActivity.this, "编号重复", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {//listView的点击
                            listViewClick();
                        }

                    } else if (name.equals("产犊记录")) {
                        cattle = find(CattleModel.class, "cattleNo", "=", editable.toString());
                        if (cattle != null) {
                            Toast.makeText(OperationActivity.this, "编号重复", Toast.LENGTH_SHORT).show();

                        } else {//listView的点击
                            listViewClick();
                        }
                    } else {
                        try {


                            cattle = dbUtils.findFirst(Selector.from(CattleModel.class).where("cattleNo", "=", editable.toString()));

                            cattle_inf.setVisibility(View.VISIBLE);

                           /* //牛舍信息
                            List<CattleAddInfoModel> infolist = dbUtils.findAll(Selector.from(CattleAddInfoModel.class).where("cattleId", "=", cattle.cattleId));

                            for (CattleAddInfoModel info : infolist) {
                                if (info.infoType.contains("牛舍")) {
                                    cowshed.setText("牛舍:  " + info.getInfoContent());
                                }
                            }

                            //胎次信息
                            int taici = 0;
                            List<E_C_RESP> eventlist = dbUtils.findAll(Selector.from(E_C_RESP.class).where("cattleId", "=", cattle.cattleId));
                            for (E_C_RESP e : eventlist) {
                                EventModel c_e = dbUtils.findFirst(Selector.from(EventModel.class).where("eventId", "=", e.eventId));
                                if (c_e.eventSummary.equals("产犊")) {
                                    taici++;
                                }
                            }
                            cattle_parity.setText("胎次:  " + taici);
                            prod_days.setVisibility(View.INVISIBLE);*/








                           /* cattle_parity.setText("事件  " + cattle.cattleODate);
                            cowshed.setText("牛只状态" + cattle.cattleSt);
                            prod_days.setText("牛只ID" + cattle.cattleId);*/

                            //listView的点击
                            listViewClick();

                        } catch (Exception e) {
                            // e.printStackTrace();
                            cattle_inf.setVisibility(View.GONE);
                             Toast.makeText(OperationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(OperationActivity.this, "没数据", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }
                }
            }
        });

        //填充牛只号
        if (getIntent().getStringExtra("cattleNo") != null) {
            et.setText(getIntent().getStringExtra("cattleNo"));
        }






       /* et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                {//do something;
                    cattle_inf.setVisibility(View.VISIBLE);
                    cattle_parity.setText(et.getText().toString());
                    cowshed.setText(entity.CATTLE_ID);
                    prod_days.setText(entity.CATTLE_NO);
                    return true;
                }
                return false;
            }

        });*/


    }


    /**
     * 填充listView
     */

    private void fillListView() {

        lv.removeAllViewsInLayout();
        switch (name) {

            case "发情":
                txt_title.setText(name);

                String[] s = {"发情时间", "现象描述", "配种判断", "备注", "观察者"};
                Integer[] i = {R.drawable.image1};
                templist = getTemplist(s, i, templist);
                break;

            case "配种" +
                    "":
                txt_title.setText(name);

                String[] s1 = {"配种日期", "冻精编号", "备注", "配种操作者"};
                Integer[] i1 = {R.drawable.image1};
                templist = getTemplist(s1, i1, templist);

                break;
            case "干奶":
                txt_title.setText(name);

                String[] s2 = {"干奶日期", "备注", "干奶操作者"};
                Integer[] i2 = {R.drawable.image1};
                templist = getTemplist(s2, i2, templist);

                break;
            case "产犊":
                txt_title.setText(name);

                String[] s3 = {"产犊日期", "生产方式", "犊牛数量", "分娩结果", "新牛舍名", "备注", "接产者"};
                Integer[] i3 = {R.drawable.image1};
                templist = getTemplist(s3, i3, templist);

                break;
            case "妊检":
                txt_title.setText(name);

                String[] s4 = {"妊检日期", "妊检方式", "妊检结果", "流产原因", "备注", "妊检操作者"};
                Integer[] i4 = {R.drawable.image1};
                templist = getTemplist(s4, i4, templist);

                break;

            case "牛只建档":
                txt_title.setText(name);

                String[] s5 = {"牛只来源", "出生日期", "性别", "新牛舍名", "牛只品种", "母系编号", "父系编号", "出生体重", "备注"};
                Integer[] i5 = {R.drawable.image1};
                templist = getTemplist(s5, i5, templist);

                break;

            case "牛只转移":
                txt_title.setText(name);

                String[] s6 = {"转移日期", "转移原因", "新牛舍名", "备注", "操作者"};
                Integer[] i6 = {R.drawable.image1};
                templist = getTemplist(s6, i6, templist);

                break;

            case "牛只淘汰":
                txt_title.setText(name);

                String[] s7 = {"淘汰日期", "淘汰原因", "处理方式", "备注"};
                Integer[] i7 = {R.drawable.image1};
                templist = getTemplist(s7, i7, templist);

                break;

            case "个人提醒":
                txt_title.setText(name);
                operation_top.setVisibility(View.GONE);

                String[] s8 = {"日期", "地点", "描述"};
                Integer[] i8 = {R.drawable.image1};
                templist = getTemplist(s8, i8, templist);

                break;

            case "产犊记录":
                txt_title.setText(name);

                String[] s9 = {"犊牛性别", "生命特征", "新牛舍名", "出生体重"};
                Integer[] i9 = {R.drawable.image1};
                templist = getTemplist(s9, i9, templist);
                break;

            case "犊牛操作":

                txt_title.setText(name);

                String[] s10 = {"犊牛性别", "生命特征", "新牛舍名", "出生体重"};
                Integer[] i10 = {R.drawable.image1};
                templist = getTemplist(s10, i10, templist);
                break;


        }
    }


    /**
     * listView点击
     */

    private void listViewClick() {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int position, long l) {
                //保存监听
                setSave();
                ViewHolder holder = ViewHolder.get(OperationActivity.this, view, adapterView, R.layout.adapter_operation_lv, position);

                Temp<String, Integer> item = (Temp) templist.get(position);

                eventname = item.t;

                tv_content = holder.getView(R.id.operation_lv_item_tv_content);

                if (eventname.contains("时间") | eventname.contains("日期")) {
                    time_tv_content = holder.getView(R.id.operation_lv_item_tv_content);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    initDateTime = formatter.format(curDate);
                    /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm");
                    Date curDate = new Date(System.currentTimeMillis());
                    initDateTime = formatter.format(curDate);*/

                    DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(OperationActivity.this, initDateTime);
                    dateTimePicKDialog.dateTimePicKDialog(time_tv_content);
                    time_tv_content.setVisibility(View.VISIBLE);
                    myadapter.notifyDataSetChanged();

                    count++;

                } else if (eventname.equals("备注")) {

                    startTransit("beizhu", tv_content.getText().toString());

                    count++;

                } else if (eventname.contains("操作者") | eventname.equals("观察者") | eventname.equals("接产者")) {

                    startTransit("selectname", eventname);

                    count++;

                } else if (eventname.equals("现象描述")) {
                    //复选
                    Multiselectdialog(new String[]{"表征异常", "摸检异常"}, view);

                } else if (eventname.equals("配种判断")) {
                    //单选
                    Radiodialog(new String[]{"配种", "不配种"}, view);

                } else if (eventname.equals("冻精编号")) {
                    // selectname
                    startTransit("selectname", "冻精编号");
                    count++;

                } else if (eventname.equals("妊检方式")) {
                    //多选
                    Multiselectdialog(new String[]{"摸检", "血检", "B超", "其他"}, view);

                } else if (eventname.equals("妊检结果")) {
                    //单选
                    Radiodialog(new String[]{"怀孕", "未怀孕", "流产"}, view);

                } else if (eventname.equals("流产原因")) {

                    // 备注
                    startTransit("beizhu", "流产原因");
                    count++;

                } else if (eventname.equals("生产方式")) {
                    //单选
                    Radiodialog(new String[]{"顺产", "助产", "接产"}, view);

                } else if (eventname.equals("犊牛数量")) {

                    //
                    SelectCountDialog dialog = new SelectCountDialog(OperationActivity.this, new SelectCountDialog.OnSelectCountDialogListener() {
                        @Override
                        public void back(String count) {
                            setTv_Content(eventname, count);
                        }
                    });
                    dialog.show();

                    count++;

                } else if (eventname.equals("分娩结果")) {
                    //单选
                    Radiodialog(new String[]{"正常", "异常"}, view);

                } else if (eventname.contains("牛舍名")) {

                    //单选
                    Radiodialog(new String[]{"2栋3舍", "1栋1舍", "1栋2舍", "围产（前）", "围产（后）", "绿洲舍", "犊牛岛"}, view);

                } else if (eventname.equals("牛只来源")) {

                    //单选
                    Radiodialog(new String[]{"自产", "外购"}, view);

                } else if (eventname.equals("性别")) {

                    List<NumMappingModel> list = InnoFormDB.getMapStr("CATTLE_SEX_ID");
                    String[] strs = new String[list.size()];
                    for (int x = 0; x < list.size(); x++) {
                        strs[x] = list.get(x).codeCaption;
                    }


                    //单选
                    Radiodialog(strs, view);


                } else if (eventname.equals("胎次")) {

                    //单选
                    count++;
                    SelectCountDialog dialog = new SelectCountDialog(OperationActivity.this, new SelectCountDialog.OnSelectCountDialogListener() {
                        @Override
                        public void back(String count) {
                            setTv_Content(eventname, count);
                        }
                    });
                    dialog.show();

                } else if (eventname.equals("牛只品种")) {

                    //selectname
                    startTransit("selectname", "牛只品种");
                    count++;


                } else if (eventname.contains("系编号")) {

                    //selectname
                    startTransit("selectname", eventname);
                    count++;


                } else if (eventname.equals("转移原因")) {

                    //selectname
                    startTransit("beizhu", "转移原因");
                    count++;


                } else if (eventname.equals("淘汰原因")) {

                    //单选
                    Radiodialog(new String[]{"死亡", "超龄", "低产", "疾病", "不孕"}, view);

                } else if (eventname.equals("处理方式")) {

                    //单选
                    Radiodialog(new String[]{"卖出", "埋葬"}, view);

                } else if (eventname.equals("地点")) {

                    // select
                    startTransit("selectname", "地点");

                    count++;


                } else if (eventname.equals("描述")) {
                    //备注
                    startTransit("beizhu", "描述");
                    count++;
                } else if (eventname.equals("犊牛性别")) {

                    List<NumMappingModel> list = InnoFormDB.getMapStr("CATTLE_SEX_ID");
                    String[] strs = new String[list.size()];
                    for (int x = 0; x < list.size(); x++) {
                        strs[x] = list.get(x).codeCaption;
                    }


                    //单选
                    Radiodialog(strs, view);
                    // Radiodialog(new String[]{"公", "母"}, view);
                    count++;
                    calve = new CalveModel();
                } else if (eventname.equals("生命特征")) {


                    List<NumMappingModel> list = InnoFormDB.getMapStr("CALVING_RESULTS_ID");
                    String[] strs = new String[list.size()];
                    for (int x = 0; x < list.size(); x++) {
                        strs[x] = list.get(x).codeCaption;
                    }


                    //多选
                    // Multiselectdialog(new String[]{"正常", "畸形", "死亡", "其他"}, view);
                    Multiselectdialog(strs, view);
                    count++;


                } else if (eventname.equals("出生体重")) {

                    startTransit("beizhu", "体重");
                    count++;
                }


            }
        });
    }


    /**
     * 填充详情
     */

    public static void setTv_Content(String eventname, String eventinf) {
        tv_content.setText(eventinf);
        tv_content.setVisibility(View.VISIBLE);
        myadapter.notifyDataSetChanged();
        eventmap.put(eventname, eventinf);
    }


    /**
     * 保存按钮设置
     */

    public void setSave() {


        //保存
        if (count + 1 >= templist.size()) {
            tv_right.setTextColor(Color.WHITE);

            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (et.getText().toString().equals("") && !name.equals("个人提醒")) {
                            Toast.makeText(OperationActivity.this, "请输入牛只号", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        save();
                        Toast.makeText(OperationActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

                        if (name.equals("牛只建档")) {
                            TestManager.CreateCattle(cattle, event, cattleAddInflist);
                        } else if (name.contains("发情")) {
                            TestManager.EventHeat(cattle, event, eventmap);

                        } else if (name.contains("配种")) {
                            TestManager.EventBreeding(cattle, event, eventmap);

                        } else if (name.contains("妊检")) {
                            TestManager.EventPregExam(cattle, event, eventmap);

                        } else if (name.contains("干奶")) {
                            TestManager.DryMilk(cattle, event, eventmap);

                        } else if (name.equals("产犊")) {
                            TestManager.Calving(cattle, event, eventmap);

                        } else if (name.contains("转移")) {
                            TestManager.CattleTrans(cattle, event, eventmap);

                        } else if (name.contains("淘汰")) {
                            TestManager.CattleEliminat(cattle, event, eventmap);

                        } else if (name.contains("个人提醒")) {
                            TestManager.PersonRemind(event, eventmap);

                        } else if (name.equals("产犊记录")) {
                            TestManager.createCalve(calve, eventmap);
                        }

                        finish();
                    } catch (DbException e) {

                        Toast.makeText(OperationActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(OperationActivity.this, e.toString() + "保存失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    /**
     * 数据库保存
     */
    private void save() throws DbException {


        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String curtime = formatter.format(curDate);


        if (time_tv_content != null)//部分界面无时间选项
            if (name.equals("牛只建档")) {
                eventmap.put("出生日期", time_tv_content.getText().toString());
            } else if (name.contains("发情")) {
                eventmap.put("发情日期", time_tv_content.getText().toString());

            } else if (name.contains("配种")) {
                eventmap.put("配种日期", time_tv_content.getText().toString());

            } else if (name.contains("妊检")) {
                eventmap.put("妊检日期", time_tv_content.getText().toString());

            } else if (name.contains("干奶")) {
                eventmap.put("干奶日期", time_tv_content.getText().toString());

            } else if (name.contains("产犊")) {
                eventmap.put("产犊日期", time_tv_content.getText().toString());
            } else if (name.contains("转移")) {
                eventmap.put("转移日期", time_tv_content.getText().toString());

            } else if (name.contains("淘汰")) {
                eventmap.put("淘汰日期", time_tv_content.getText().toString());

            } else if (name.contains("个人提醒")) {
                eventmap.put("提醒日期", time_tv_content.getText().toString());

            }


        /**
         * 设置事件编号
         */
        event.setEventId("phone" + UUID.randomUUID().toString());//事件ID
        event.setEventTime(curtime);//事件时间
        event.setEventOpName(InnoFarmConstant.UserID);//事件执行者
        // event.setEVENT_SUMMARY("");//事件概述
        event.setLogSt("01");//日志状态
        event.setLogChgTime(curtime);//日志更改时间
        event.setRecordTime(curtime);//记录用户ID
        event.setRecordUid(InnoFarmConstant.UserID);//记录时间

        dbUtils.save(event);

        /**創建事件詳情*/

        for (String name : eventmap.keySet()) {
            eventdef = new EventDef();
            eventdef.setEventId(event.eventId);
            eventdef.setEventIns(eventmap.get(name));
            eventdef.setEventName(name);
            dbUtils.save(eventdef);
        }


        if (name.equals("牛只建档")) {

            cattle = new CattleModel();
            String id = "";
            String[] str = UUID.randomUUID().toString().split("-");
            for (int x = 0; x < str.length; x++) {
                id = id + str[x];
            }
            cattle.setCattleId(id);//牛只ID
            cattle.setCattleNo(et.getText().toString());//牛只号
            cattle.setCattleSor(eventmap.get("牛只来源"));//来源
            cattle.setCattleBrt(eventmap.get("出生日期"));//日期
            cattle.setCattleSex(eventmap.get("性别"));//性别

            cattle.setCattleSt("养殖中");//牛只状态
            cattle.setCattleODate(curtime);//牛只登记时间
            cattle.setLastUpTime(curtime);//最终更新时间
            dbUtils.save(cattle);


            cattleAddInflist = new ArrayList();
            CattleAddInfoModel addInfo = new CattleAddInfoModel();
            addInfo.setCattleId(cattle.cattleId);
            addInfo.setInfoType("牛舍");
            addInfo.setInfoContent(eventmap.get("新牛舍名"));
            dbUtils.save(addInfo);
            cattleAddInflist.add(addInfo);

            CattleAddInfoModel addInfo1 = new CattleAddInfoModel();
            addInfo1.setCattleId(cattle.cattleId);
            addInfo1.setInfoType("牛只品种");
            addInfo1.setInfoContent(eventmap.get("牛只品种"));
            dbUtils.save(addInfo1);
            cattleAddInflist.add(addInfo1);

            CattleAddInfoModel addInfo2 = new CattleAddInfoModel();
            addInfo2.setCattleId(cattle.cattleId);
            addInfo2.setInfoType("母系编号");
            addInfo2.setInfoContent(eventmap.get("母系编号"));
            dbUtils.save(addInfo2);
            cattleAddInflist.add(addInfo2);

            CattleAddInfoModel addInfo3 = new CattleAddInfoModel();
            addInfo3.setCattleId(cattle.cattleId);
            addInfo3.setInfoType("父系编号");
            addInfo3.setInfoContent(eventmap.get("父系编号"));
            dbUtils.save(addInfo3);
            cattleAddInflist.add(addInfo3);


        } else if (name.equals("牛只淘汰")) {

            //  犊牛淘汰
            if (tv_operation.getText().equals("犊牛号")) {
                calve.setCalvesSt("已淘汰");//牛只状态
                calve.setLastUpTime(curtime);//最终更新时间
                dbUtils.update(calve, "calves_St", "last_Up_Time");
            } else {
                cattle.setCattleSt("已淘汰");//牛只状态
                cattle.setLastUpTime(curtime);//最终更新时间
                dbUtils.update(cattle, "cattleSt", "lastUpTime");

            }


        } else if (name.equals("牛只转移")) {


        } else if (name.equals("产犊")) {

            int count = Integer.parseInt(eventmap.get("犊牛数量"));
            for (int x = 0; x < count; x++) {
                Intent intent = new Intent(OperationActivity.this, OperationActivity.class);
                intent.putExtra("comefrom", "产犊记录");
                intent.putExtra("eventId", event.eventId);
                startActivity(intent);
            }

            /** 創建牛隻—事件*/
            resp = new E_C_RESP();
            resp.setEventId(event.eventId);
            //if (name.equals(""))
            resp.setCattleId(cattle.cattleId);// TODO DBSave(resp);
            dbUtils.save(resp);

            Toast.makeText(OperationActivity.this, "保存成功,继续填写犊牛信息", Toast.LENGTH_SHORT).show();

        } else if (name.equals("产犊记录")) {

            String eventID = getIntent().getStringExtra("eventId");
            calve.setCalvesId(UUID.randomUUID().toString());//犊牛ID
            calve.setEventId(eventID);//事件ID
            calve.setCalvesNo(et.getText().toString());//犊牛号
            calve.setCalvesBir(curtime);//出生日期
            calve.setLastUpTime(curtime);//最终更新时间

            calve.setCalvesSex(eventmap.get("犊牛性别"));//犊牛性别
            calve.setCalvesSig(eventmap.get("生命特征"));//生命特征

            dbUtils.save(calve);

        }

        if (cattle != null) {
            /** 創建牛隻—事件*/
            resp = new E_C_RESP();
            resp.setEventId(event.eventId);
            //if (name.equals(""))
            resp.setCattleId(cattle.cattleId);// TODO DBSave(resp);
            dbUtils.save(resp);
        }
        finish();
    }


    /**
     * 数据库保存
     */
    private void DBSave(Object obj) {
        try {
            dbUtils.save(obj);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 数据库查找
     */
    public <T> T find(Class<T> cls, String str, String str1, String str2) {

        try {
            return dbUtils.findFirst(Selector.from(cls).where(str, str1, str2));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 打开界面
     */


    private void startTransit(String str, String str1) {
        Intent intent = new Intent(getApplicationContext(), TransitActivity.class);
        intent.putExtra("tag", str);
        intent.putExtra("arg", str1);

        startActivity(intent);
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

    /**
     * listView setAdapter
     */

    public void lisViewSetAdapter() {


        myadapter = new Adapter<Temp<String, Integer>>(this, templist, R.layout.adapter_operation_lv) {
            @Override
            public void convert(ViewHolder helper, Temp<String, Integer> item) {


                helper.setText(R.id.operation_lv_item_tv, item.t);
                helper.setImageResource(R.id.operation_lv_item_iv, item.e);

                if (name.equals("犊牛操作")) {
                    TextView content = helper.getView(R.id.operation_lv_item_tv_content);
                    content.setVisibility(View.VISIBLE);
                    int position = templist.indexOf(item);
                    switch (position) {
                        case 0:
                            content.setText(showcalve.calvesSex);
                            break;
                        case 1:
                            content.setText(showcalve.calvesSig);
                            break;
                        case 2:
                            content.setText(showcalve.calvesNo);
                            break;
                        case 3:
                            content.setText(showcalve.calvesNo);
                            break;
                    }
                } else if (name.equals("牛只建档") && getIntent().getStringExtra("calveId") != null) {


                } else if (name.equals("牛只淘汰") && getIntent().getStringExtra("calveId") != null) {


                }


            }
        };

        lv.setAdapter(myadapter);


        /**建立业务事件日志对象*/
        //业务事件概述----
        event = new EventModel();
        event.setEventSummary(name);//事件概述//TODO 替换

    }


    /**
     * 临时内部类，填充listView所需资源
     */
    class Temp<T, E> {
        public T t;
        public E e;
    }


    /**
     * 单选对话框
     *
     * @param str 选项名称
     */


    private void Radiodialog(String[] str, final View view) {
        final String items[] = str;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        // builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //Toast.makeText(OperationActivity.this, items[which], Toast.LENGTH_SHORT).show();
                count++;
                setTv_Content(eventname, items[which]);
            }
        });

       /* builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(OperationActivity.this, "确定", Toast.LENGTH_SHORT).show();
            }
        });*/
        builder.create().show();
    }


    /**
     * 复选对话框
     *
     * @param str 选项名称
     */

    private void Multiselectdialog(String[] str, final View view) {
        final String items[] = str;
        int length = str.length;
        final boolean selected[] = new boolean[length];
        for (int i = 0; i < length; i++) {
            selected[i] = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        // builder.setIcon(R.mipmap.ic_launcher);
        builder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Toast.makeText(OperationActivity.this, items[which] + isChecked, Toast.LENGTH_SHORT).show();

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String temp = "";
                //android会自动根据你选择的改变selected数组的值。
                for (int i = 0; i < selected.length; i++) {
                    Log.e("hongliang", "" + selected[i]);

                    if (selected[i]) {
                        temp = temp + "" + items[i];
                    }
                }
                setTv_Content(eventname, temp);
                count++;

            }
        });
        builder.create().show();
    }


    /**
     * 初始化popupwindw
     */

    private void initPopupWindow() {

        final String calveID = getIntent().getStringExtra("calveId");
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        final View popupWindow_view = getLayoutInflater().inflate(R.layout.popuwindow_calvefragment, null, false);
        Button add = (Button) popupWindow_view.findViewById(R.id.add_cattle);
        Button dieout = (Button) popupWindow_view.findViewById(R.id.die_out);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OperationActivity.this, "添加", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OperationActivity.this, OperationActivity.class);
                intent.putExtra("comefrom", "牛只建档");
                intent.putExtra("calveId", calveID);
                startActivity(intent);
                finish();

            }
        });
        dieout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OperationActivity.this, "淘汰", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(OperationActivity.this, OperationActivity.class);
                intent2.putExtra("comefrom", "牛只淘汰");
                intent2.putExtra("calveId", calveID);
                startActivity(intent2);
                finish();
            }
        });

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, 300, RadioGroup.LayoutParams.WRAP_CONTENT, true);
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


}
