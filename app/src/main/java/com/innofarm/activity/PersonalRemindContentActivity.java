package com.innofarm.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.innofarm.R;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.external.ViewHolder;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.PersonalRemindInfo;
import com.innofarm.protocol.PersonalReminderResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2015/11/20.
 */
public class PersonalRemindContentActivity extends BaseActivity {


    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;
    @ViewInject(R.id.btn_right)
    Button tv_right;

    @ViewInject(R.id.operation_lv_item_tv_content1)
    TextView tv_content1;
    @ViewInject(R.id.operation_lv_item_tv_content2)
    TextView tv_content2;
    @ViewInject(R.id.operation_lv_item_tv_content3)
    TextView tv_content3;



    private PopupWindow popupWindow;
    ProgressDialog pdl;


    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_p_r_c, null);
        ViewUtils.inject(this, view);
        setContentView(view);

    }


    @Override
    protected void initView() {

        txt_title.setText("个人提醒");

        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalRemindContentActivity.this, RemindContentActivity.class);
                intent.putExtra("name","个人提醒");
                startActivity(intent);
                finish();
            }
        });
        //操作
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("操作");
        tv_right.setTextColor(Color.WHITE);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPopupWindow();
                popupWindow.showAsDropDown(tv_right);
            }
        });


        tv_content1.setVisibility(View.VISIBLE);
        tv_content2.setVisibility(View.VISIBLE);
        tv_content3.setVisibility(View.VISIBLE);

        tv_content1.setText(getIntent().getStringExtra("shijian"));
        tv_content2.setText(getIntent().getStringExtra("didian"));
        tv_content3.setText(getIntent().getStringExtra("miaoshu"));








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
     * 初始化popupwindw
     */

    private void initPopupWindow() {


        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        final View popupWindow_view = getLayoutInflater().inflate(R.layout.popuwindow_calvefragment, null, false);
        Button add = (Button) popupWindow_view.findViewById(R.id.add_cattle);
        add.setText("修改");
        Button dieout = (Button) popupWindow_view.findViewById(R.id.die_out);
        dieout.setText("删除");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PersonalRemindContentActivity.this, OperationActivity.class);
                intent.putExtra("comefrom", "个人提醒");
                startActivity(intent);
                finish();

            }
        });
        dieout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteDialog();


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
     * 关闭item对话框
     *
     * @param
     */

    private void deleteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        //builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认删除?"); //设置内容
        //builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
              //  Toast.makeText(PersonalRemindContentActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();

                ServiceManager.DeletePersonalRemind(getIntent().getStringExtra("eventID"),DeletRemindRequest(),failrequest());
                pdl = new ProgressDialog(PersonalRemindContentActivity.this);
                pdl.setCancelable(false);
                pdl.show();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
               // Toast.makeText(PersonalRemindContentActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }



    /**
     * 请求个人提醒
     */
    private SuccessRequestCallBack<String> DeletRemindRequest() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(String responseInfo) {



                String str = responseInfo;
                PersonalReminderResult result = UIUtils.json2str(str, PersonalReminderResult.class);
                String status = result.getReturn_sts();
                if(status.equals("0")){
                    pdl.dismiss();
                    Toast.makeText(PersonalRemindContentActivity.this, "删除成功", Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(PersonalRemindContentActivity.this, RemindContentActivity.class);
                    intent.putExtra("name","个人提醒");
                    startActivity(intent);
                    finish();

                }else {
                    pdl.dismiss();
                    Toast.makeText(PersonalRemindContentActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
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







}
