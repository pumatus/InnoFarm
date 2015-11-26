package com.innofarm.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.innofarm.InnoFarmConstant;
import com.innofarm.MainActivity;
import com.innofarm.R;
import com.innofarm.external.MyRequestCallBack;
import com.innofarm.manager.ServiceManager;
import com.innofarm.protocol.LoginInfo;
import com.innofarm.protocol.LoginResult;
import com.innofarm.utils.DeviceInfoUtils;
import com.innofarm.utils.MD5;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * User: syc
 * Date: 2015/9/6
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:login
 */
public class LoginActivity extends BaseActivity {


    private ProgressDialog pDlg;
    @ViewInject(R.id.login_imbtn)
    private ImageButton login;
    @ViewInject(R.id.username)
    private EditText et_username;
    @ViewInject(R.id.password)
    private EditText et_password;

    private String password;
    private String username;
    private SharedPreferences  sp;


    protected void init() {
        View view = View.inflate(this, R.layout.activity_login, null);
        ViewUtils.inject(this, view);
/*        //判断是否第一次登录
        boolean user_first = sp.getBoolean("FIRST", true);//取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        if (!user_first) {
            //如果不是第一次登录，直接跳转到下一个界面
        }
        */
        setContentView(view);
    }

    /**
     * 初始化view界面
     */

    protected void initView() {


//        sp.edit().putBoolean("FIRST", false).commit();

        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        username = et_username.getText().toString();
                        password = et_password.getText().toString();
                        password = MD5.getMessageDigest(password.getBytes());
                        login(username, password);
                   }
                }
        );
    }



    public static <T> T getUser(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }


    /**
     * 登入
     *
     * @param strNumber   手机号
     * @param strPassword 密码
     */
    private void login(String strNumber, String strPassword) {
        String userName = "";
        String password = "";
        //手机号验证
        if ((!UIUtils.isMobileNO(strNumber) && (strNumber.length() != 11))) {
            UIUtils.showToast(LoginActivity.this, getString(R.string.regist_phone_error));
        }
         /*else if (strPassword.length() < 11) {
            //密码长度验证
            UIUtils.showToast(LoginActivity.this, getString(R.string.regist_password_error));
            return;
        } */
        else if (!"".equals(strNumber) && !"".equals(strPassword)) {
            pDlg = new ProgressDialog(this);
            pDlg.setCancelable(false);
            pDlg.setMessage(getString(R.string.loading));
            if (DeviceInfoUtils.isNetworkConnected() == true) {
                ServiceManager.doPostLogin(strNumber, strPassword, mycalback());
                pDlg.show();
            } else { //脱机断网情况下
                try {
                    sp = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                    userName = sp.getString("name", "");
                    password = sp.getString("password", "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            if (userName == null) {
//                ServiceManager.doPostLogin(strNumber, strPassword, mycalback());
//                pDlg.show();
                if (( userName.equals(strNumber)) && ( password.equals(strPassword))) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("boss", "");
                    startActivity(intent);
                    finish();
                    pDlg.show();
                } else if ( !userName.equals(strNumber)){
                    UIUtils.showToast(LoginActivity.this, getString(R.string.user_not_found));
                } else if (( userName.equals(strNumber) ) && ( !password.equals(strPassword) )){
                    UIUtils.showToast(LoginActivity.this, getString(R.string.password_error));
                }
            }
        } else {
            UIUtils.showToast(this, "帐号、密码必须填写");
        }
    }


    /**
     * 请求响应
     */
    private MyRequestCallBack<String> mycalback() {

        return new MyRequestCallBack<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
            }

            //响应成功
            @Override
            public void onSuccess(String  responseInfo) {
                /*{
     "userInfo" :
		{
			"user_id" : "EU00000002",
			"user_type" : "OA-R-0201",
			"user_status" : "0000"
		}
}*/
                LoginResult result = getUser(responseInfo,LoginResult.class);
                LoginInfo login = result.getBody();




                if(login==null)
                    Toast.makeText(getApplicationContext(),"输入有误",Toast.LENGTH_LONG).show();
                else {
                    InnoFarmConstant.UserID = login.getUser_id();
                    if (login.getUser_status().equals(InnoFarmConstant.USERSTATE_STOP)) {
                        Toast.makeText(getApplicationContext(), "用户已停用", Toast.LENGTH_SHORT).show();
                    } else if (login.getUser_status().equals(InnoFarmConstant.USERSTATE_INUSE)) {
                        //创建SharedPreferences
                        sp = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("name", username);
                        editor.putString("password", password);
                        editor.commit();
                        if (login.getUser_type().equals(InnoFarmConstant.USERPERMISSION_EMPLOYEE)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("boss", "");
                            startActivity(intent);
                            finish();

                        } else if (login.getUser_type().equals(InnoFarmConstant.USERPERMISSION_BOSS)) {
                            Toast.makeText(getApplicationContext(), "BOSS", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("boss", "boss");
                            startActivity(intent);
                        }
                    }
                }
                    pDlg.dismiss();
            }

            //响应失败
            @Override
            public void onFailure(HttpException error, String msg) {
               // Toast.makeText(LoginActivity.this, error.toString() + "*****" + msg, Toast.LENGTH_LONG).show();
               // Toast.makeText(LoginActivity.this, "登入错误" + msg, Toast.LENGTH_LONG).show();

                pDlg.dismiss();
/*                if (username.equals(sp.getString("name","18810032680"))&&password.equals(sp.getString("password","1"))){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("boss", "");
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "脱机登入", Toast.LENGTH_LONG).show();
                finish();}else {Toast.makeText(LoginActivity.this, "登入失败", Toast.LENGTH_LONG).show();}
*/
            }
        };
    }
}
