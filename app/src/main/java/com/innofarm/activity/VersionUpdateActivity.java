package com.innofarm.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innofarm.R;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.manager.DownLoadVersionManager;
import com.innofarm.manager.HttpHelper;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.UpdataInfoParser;
import com.innofarm.protocol.GetVersionResult;
import com.innofarm.protocol.UpdataInfo;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

import java.io.FileOutputStream;
import java.io.InputStream;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by dell on 2015/11/8.
 */
public class VersionUpdateActivity extends BaseActivity {

    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;

    @ViewInject(R.id.tv_nowversion)
    TextView nowVersion;

    @ViewInject(R.id.rl_checkversion)
    RelativeLayout checkVersion;


    private final String TAG = this.getClass().getName();
    private final int UPDATA_NONEED = 0;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int SDCARD_NOMOUNTED = 3;
    private final int DOWN_ERROR = 4;
    private Button getVersion;
    // private UpdataInfo info;
    private String localVersion;


    String url = HttpHelper.SERVER_URL + "1009.do?fw.excute.event=30323A3A303A";
    private ProgressBar pb;
    private TextView tv;
    public static int loading_process;

    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_versionupdate, null);
        ViewUtils.inject(this, view);
        setContentView(view);


    }

    @Override
    protected void initView() {
        txt_title.setText("关于我们");
        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        try {
            nowVersion.setText(getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkVersion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckVersionTask();

            }
        });


    }

    private String getVersionName() throws Exception {

        //getPackageName()是你当前类的包名，0代表是获取版本信息

        PackageManager packageManager = getPackageManager();

        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),

                0);

        return packInfo.versionName;

    }


    private void CheckVersionTask() {

        ServiceManager.getVersion(getVersion(), failrequest());

    }




    /*protected void showUpdataDialog() {

        AlertDialog.Builder builer = new Builder(this);

        builer.setTitle("版本升级");

        builer.setMessage("是否升级");
        //  builer.setMessage(info.getDescription());

        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ

        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Log.i(TAG, "下载apk,更新");

                downLoadApk();

            }

        });

        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {


                //do sth

            }

        });

        AlertDialog dialog = builer.create();

        dialog.show();

    }*/

	/*

	 * 从服务器中下载APK

	 */
/*

    protected void downLoadApk() {

        final ProgressDialog pd;    //进度条对话框

        pd = new ProgressDialog(this);

        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        pd.setMessage("正在下载更新");

        pd.show();

        new Thread() {

            @Override

            public void run() {

                try {

                    File file = DownLoadVersionManager.getFileFromServer(HttpHelper.SERVER_URL + "1009.do?fw.excute.event=30323A3A303A"
                            , pd);

                    sleep(3000);

                    installApk(file);

                    pd.dismiss(); //结束掉进度条对话框

                } catch (Exception e) {

                    //下载apk失败

                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }
        }.start();

    }
*/


    //安装apk

    protected void installApk(File file) {

        Intent intent = new Intent();

        //执行动作

        intent.setAction(Intent.ACTION_VIEW);

        //执行的数据类型

        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

        startActivity(intent);

    }


    /**
     * 请求发情事件提醒
     */
    private SuccessRequestCallBack<String> getVersion() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(String responseInfo) {

                String s = responseInfo;
                GetVersionResult result = UIUtils.json2str(responseInfo, GetVersionResult.class);


                if (result.getVersion().equals(localVersion)) {

                    Log.i(TAG, "版本号相同");
                    Toast.makeText(getApplicationContext(), "不需要更新", Toast.LENGTH_SHORT).show();
                    // LoginMain();

                } else {

                    Log.i(TAG, "版本号不相同 ");
                    //对话框通知用户升级程序
                    Beginning();

                }
            }
        };
    }

    //开始加载文件
    public void Beginning() {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.layout_loadapk, null);
        pb = (ProgressBar) ll.findViewById(R.id.down_pb);
        tv = (TextView) ll.findViewById(R.id.tv);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(ll);
        builder.setTitle("版本更新进度提示");
        /*builder.setNegativeButton("后台下载",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(this, VersionService.class);
                        startService(intent);
                        dialog.dismiss();
                    }
                });*/

        builder.show();
        //开启线程根据url请求apk
        new Thread() {
            public void run() {
                loadFile(url);//TODO
            }
        }.start();
    }


    //下载apk
    public void loadFile(String url) {


        URL u = null;
        try {
            u = new URL(url);
            HttpURLConnection conn =  (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            float length = 1488*1024;//conn.getContentLength();//TODO
            InputStream is = conn.getInputStream();

            FileOutputStream fileOutputStream = null;
            if (is != null) {
                File file = new File(Environment.getExternalStorageDirectory(),
                        "Update.apk");
                fileOutputStream = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int ch = -1;
                float count = 0;
                while ((ch = is.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, ch);
                    count += ch;
                    sendMsg(1, (int) (count * 100 / length));
                }
            }
            sendMsg(2, 0);
            fileOutputStream.flush();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Exception e) {
            sendMsg(-1, 0);
        }



      /*  HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response;
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();

            float length = entity.getContentLength();

            InputStream is = entity.getContent();
            FileOutputStream fileOutputStream = null;
            if (is != null) {
                File file = new File(Environment.getExternalStorageDirectory(),
                        "Update.apk");
                fileOutputStream = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int ch = -1;
                float count = 0;
                while ((ch = is.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, ch);
                    count += ch;
                    sendMsg(1, (int) (count * 100 / length));
                }
            }
            sendMsg(2, 0);
            fileOutputStream.flush();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Exception e) {
            sendMsg(-1, 0);
        }*/
    }

    private void sendMsg(int flag, int c) {
        Message msg = new Message();
        msg.what = flag;
        msg.arg1 = c;
        handler.sendMessage(msg);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 1:
                        //进度条
                        pb.setProgress(msg.arg1);
                        loading_process = msg.arg1;
                        tv.setText("已为您加载了：" + loading_process + "%");
                        break;
                    case 2:
                        //安装APK
                        File file = new File(Environment.getExternalStorageDirectory(), "Update.apk");
                        installApk(file);
                        break;
                    case -1:
                        //报错
                        String error = msg.getData().getString("error");
                        Toast.makeText(VersionUpdateActivity.this, error, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };


    private FailureRequestCallBack failrequest() {
        return new FailureRequestCallBack() {
            @Override
            public void onFailure(HttpException error, String msg) {
                String str = msg;

                Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_SHORT).show();

            }
        };
    }


}
