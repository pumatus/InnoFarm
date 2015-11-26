package com.innofarm.manager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.innofarm.InnoFarmConstant;
import com.innofarm.MainActivity;
import com.innofarm.external.MyRequestCallBack;
import com.innofarm.model.CattleModel;
import com.innofarm.model.REQUESTModel;
import com.innofarm.protocol.LoginInfo;
import com.innofarm.protocol.LoginResult;
import com.innofarm.protocol.SubmitDataBaseInfo;
import com.innofarm.protocol.SubmitDataBaseResult;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * User: syc
 * Date: 2015/10/26
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:数据库更新时上传
 */
public class RequestService extends Service {


    Context context;// = getApplicationContext();

    DbUtils dbUtils;// = DbUtils.create(context, "Innofarm");
    List<REQUESTModel> list = new ArrayList<>();
    LinkedList<REQUESTModel> linklist = new LinkedList<REQUESTModel>();
    boolean flag = false;
    Thread thread;

    private Lock lock = new ReentrantLock();

    boolean nflag = true;


    @Override
    public void onCreate() {


        context = getApplicationContext();
       // dbUtils = DbUtils.create(context, "Innofarm");
        dbUtils = InnoFormDB.getDbUtils();
        try {
            dbUtils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {

        try {
            list = dbUtils.findAll(Selector.from(REQUESTModel.class).orderBy("id"));
        } catch (DbException e) {
            e.printStackTrace();

            // TODO ?

        }


        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("curThread", Thread.currentThread().toString() + "****************************************");

                while (true) {
                    //判断网络状态
                    if (isNetworkAvailable(context)) {
                        //读取数据库数据
                        try {
                            list = dbUtils.findAll(Selector.from(REQUESTModel.class).orderBy("id"));
                        } catch (DbException e) {
                            e.printStackTrace();

                            // TODO ?

                        }
                        //装入link集合，队列
                        if (list != null && list.size() > 0) {
                            linklist.clear();
                            for (REQUESTModel req : list) {
                                linklist.add(req);
                            }


                            //TODO 异步问题....
                            while (true) {

                                if (linklist.size() <= 0) {
                                    break;
                                }
                                REQUESTModel req = linklist.getFirst();
                                if (flag) {
                                    flag = false;
                                    break;
                                }

                               /* if(nflag == flag)
                                {
                                    continue;
                                }*/


                                ServiceManager.SubmitDBUpdate(req, mycalback());
                                // nflag = flag;
                                Log.i("curThread", Thread.currentThread().toString());

                                while (nflag) {
                                    try {
                                        thread.sleep(1000 * 5);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                nflag = true;


                            }
                        }

                    }

                    try {
                        thread.sleep(1000*5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 将JSon转换成字符串
     */
    public static <T> T json2str(String jsonString, Class<T> cls) {
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
            public void onSuccess(String responseInfo) {
               /* { "return_sts":"1"
                } 0 时成功，1 时加入后台数据库成功*/


                SubmitDataBaseResult result = json2str(responseInfo, SubmitDataBaseResult.class);
                //SubmitDataBaseInfo info = result.getBody();
                String stus = result.getBody(); //info.getReturn_sts();

                if (stus.equals("1")) {
                    flag = false;
                    nflag = false;


                } else {

                    try {
                        dbUtils.delete(linklist.removeFirst());
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    if (linklist.size() > 0) {
                        flag = false;
                        nflag = false;

                    } else {
                        flag = true;
                        nflag = false;

                    }


                }


            }

            //响应失败
            @Override
            public void onFailure(HttpException error, String msg) {
                // Toast.makeText(LoginActivity.this, error.toString() + "*****" + msg, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "错误" + msg, Toast.LENGTH_LONG).show();
                flag = false;

                nflag = false;
            }
        };
    }

    /**
     * 检查当前网络是否可用
     *
     * @param context
     * @return
     */

    public boolean isNetworkAvailable(Context context) {
        // Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}






