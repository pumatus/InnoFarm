package com.innofarm.manager;

import com.innofarm.InnoFarmConstant;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.MyRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.model.CalveModel;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.EventModel;
import com.innofarm.model.REQUESTModel;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * User: syc
 * Date: 2015/9/8
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 请求管理类，负责管理所有的网络请求
 */
public class ServiceManager {

    private static HttpUtils http;

    //自动实现异步处理
    public static void doPost(String url, RequestParams params, final MyRequestCallBack callback) {
        if (http == null) {
            http = new HttpUtils();
        }
        http.configCurrentHttpCacheExpiry(1000 * 20);//设置超时时间
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {//接口回调
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                callback.onFailure(arg0, arg1);
            }

            @Override
            public void onSuccess(ResponseInfo<String> info) {
                callback.onSuccess(info.result);//利用接口回调数据传输
            }
        });
    }


    //自动实现异步处理
    public static void doPost(String url, RequestParams params, final SuccessRequestCallBack successcallback, final FailureRequestCallBack failurecallback) {


        if (http == null) {
            http = new HttpUtils();
        }
        http.configCurrentHttpCacheExpiry(1000 * 10);//设置超时时间
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {//接口回调

            @Override
            public void onFailure(HttpException arg0, String arg1) {

                failurecallback.onFailure(arg0, arg1);

            }

            @Override
            public void onSuccess(ResponseInfo<String> info) {

                successcallback.onSuccess(info.result);//利用接口回调数据传输
            }

        });
    }


    //自动实现异步处理
    public static void doPost(String url, final SuccessRequestCallBack successcallback, final FailureRequestCallBack failurecallback) {
        if (http == null) {
            http = new HttpUtils();
        }
        http.configCurrentHttpCacheExpiry(1000 * 30);//设置超时时间
        http.send(HttpRequest.HttpMethod.POST, url, new RequestCallBack<String>() {//接口回调
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                failurecallback.onFailure(arg0, arg1);
            }

            @Override
            public void onSuccess(ResponseInfo<String> info) {
                successcallback.onSuccess(info.result);//利用接口回调数据传输
            }
        });
    }


    /**
     * 登入
     * 1001
     */


    public static void doPostLogin(String name, String password, MyRequestCallBack callback) {//外部接口函数
        String url = HttpHelper.SERVER_URL + "login.do?fw.excute.event=30313A3A303A";//+"login.do?fw.excute.event=30313A3A303A"

        RequestParams params = new RequestParams();
        params.addQueryStringParameter("loginId", name);
        params.addQueryStringParameter("loginPassword", password);
        doPost(url, params, callback);
    }


    /**
     *
     * */


    public static void CreateCattle(CattleModel cattle, EventModel event, ArrayList<CattleAddInfoModel> arr, MyRequestCallBack callback) {//外部接口函数
        String url = HttpHelper.SERVER_URL + "1002.do?fw.excute.event=30313A3A303A";

        RequestParams params = new RequestParams();


        params.addQueryStringParameter("eventId", event.eventId);
        params.addQueryStringParameter("eventTime", event.eventTime);
        params.addQueryStringParameter("ventOpName", event.eventOpName);
        params.addQueryStringParameter("eventSummary", event.eventSummary);
        params.addQueryStringParameter("logSt", event.logSt);
        params.addQueryStringParameter("logChgTime", event.logChgTime);
        params.addQueryStringParameter("recordUid", event.recordUid);
        params.addQueryStringParameter("recordTime", event.recordTime);

        params.addQueryStringParameter("cattleId", cattle.cattleId);
        params.addQueryStringParameter("cattleNO", cattle.cattleNo);
        params.addQueryStringParameter("cattleSor", cattle.cattleSor);
        params.addQueryStringParameter("cattleBRT", cattle.cattleBrt);
        params.addQueryStringParameter("cattleSex", cattle.cattleSex);

        params.addQueryStringParameter("barnName", arr.get(0).getInfoContent());
        params.addQueryStringParameter("cattleType", arr.get(1).getInfoContent());
        params.addQueryStringParameter("cattleMotherId", arr.get(2).getInfoContent());
        params.addQueryStringParameter("cattleFatherId", arr.get(3).getInfoContent());

        doPost(url, params, callback);
    }


    /**
     * 提交数据更新
     */

    public static void SubmitDBUpdate(REQUESTModel req, MyRequestCallBack callback) {//外部接口函数

        String url = HttpHelper.SERVER_URL + req.getUrl();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("command", req.getContent());
        doPost(url, params, callback);
    }


    /**
     * 第一次登录下载所有业务事件记录	1003
     */

    public static void FetchAll(SuccessRequestCallBack success, FailureRequestCallBack failure) {//外部接口函数
        String url = HttpHelper.SERVER_URL + "1003.do?fw.excute.event=30313A3A303A";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", InnoFarmConstant.UserID);//录入者
        doPost(url,params, success, failure);
    }


    /**
     * 更新本地数据库 	1003
     */

    public static void UpdateDB(String time, SuccessRequestCallBack success, FailureRequestCallBack failure) {//外部接口函数
        String url = HttpHelper.SERVER_URL + "1003.do?fw.excute.event=30323A3A303A";
        // lastUpdateTime
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", InnoFarmConstant.UserID);//录入者
        params.addBodyParameter("lastUpdateTime", time);
        doPost(url, params, success, failure);
    }


    /**
     * 获取各类业务操作事件记录总数
     * 1004
     */


    public static void EventRemindCount(SuccessRequestCallBack success, FailureRequestCallBack failure) {//外部接口函数
        String url = HttpHelper.SERVER_URL + "1004.do?fw.excute.event=30313A3A303A";
        doPost(url, success, failure);
    }

    /**
     * 获取发情类事件的提醒列表
     * 1004
     */


    public static void HeatEventRemind(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1004.do?fw.excute.event=30323A3A303A";
        doPost(url, success, failure);
    }

    /**
     * 获取配种类事件的提醒列表
     * 1004
     */


    public static void MatingEventRemind(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1004.do?fw.excute.event=30333A3A303A";
        doPost(url, success, failure);
    }

    /**
     * 获取妊检类事件的提醒列表
     * 1004
     */


    public static void PregnancyEventRemind(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1004.do?fw.excute.event=30343A3A303A";
        doPost(url, success, failure);
    }

    /**
     * 获取干奶类事件的提醒列表
     * 1004
     */


    public static void GanNaiEventRemind(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1004.do?fw.excute.event=30353A3A303A";
        doPost(url, success, failure);
    }

    /**
     * 获取围产前类事件的提醒列表
     * 1004
     */


    public static void BeforeWeiChanEventRemind(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1004.do?fw.excute.event=30363A3A303A";
        doPost(url, success, failure);
    }

    /**
     * 获取产犊类事件的提醒列表
     * 1004
     */


    public static void CalvingEventRemind(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1004.do?fw.excute.event=30373A3A303A";
        doPost(url, success, failure);
    }

    /**
     * 获取围产后类事件的提醒列表
     * 1004
     */


    public static void AfaterWeiChanHeatEventRemind(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1004.do?fw.excute.event=30383A3A303A";
        doPost(url, success, failure);
    }


    /**
     * 获取牛只档案信息
     * 1005
     */


    public static void GetCattleFile(String cattleId, SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1005.do?fw.excute.event=30313A3A303A";

        RequestParams params = new RequestParams();
        params.addBodyParameter("cattleId", cattleId);

        doPost(url, params, success, failure);
    }


    /**
     * 提交个人提醒
     * 1006
     */


    public static void setPersonalReminder(String userId, SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1006.do?fw.excute.event=30313A3A303A";

        RequestParams params = new RequestParams();
        params.addBodyParameter("operator", userId);//操作者
        params.addBodyParameter("eventTime", userId);//事件时间
        params.addBodyParameter("userId", userId);//录入者
        params.addBodyParameter("address", userId);//提醒地点
        params.addBodyParameter("descript", userId);//提醒时间

        doPost(url, params, success, failure);
    }


    /**
     * 获取个人提醒
     * 1006
     */


    public static void GetPersonalReminder(String userId, SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1006.do?fw.excute.event=30323A3A303A";


        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", userId);

        doPost(url, params, success, failure);
    }


    /**
     * 操作记录获取
     * 1007
     */


    public static void GetOperationRecords(String operator, String end, String start, SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1007.do?fw.excute.event=30313A3A303A";

        RequestParams params = new RequestParams();
        params.addBodyParameter("operator", operator);
        params.addBodyParameter("endDate", end);
        params.addBodyParameter("startDate", start);
        params.addBodyParameter("userId", InnoFarmConstant.UserID);

        doPost(url, params, success, failure);
    }


    /**
     * 获取牧场概况
     * 1008
     */


    public static void RequestCattleSurvey(String userId, SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1008.do?fw.excute.event=30313A3A303A";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", userId);
        doPost(url, params, success, failure);
    }


    /**
     * 获取版本号
     * 1009
     */


    public static void getVersion(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1009.do?fw.excute.event=30313A3A303A";
        doPost(url, success, failure);
    }

    /**
     * 下载最新版本
     * 1009
     */


    public static void downloadVersion(SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1009.do?fw.excute.event=30323A3A303A";
        doPost(url, success, failure);
    }




    /**
     * 删除个人提醒
     1006
     */


    public static void DeletePersonalRemind( String eventid, SuccessRequestCallBack success, FailureRequestCallBack failure) {
        String url = HttpHelper.SERVER_URL + "1006.do?fw.excute.event=30333A3A303A";

        RequestParams params = new RequestParams();
        params.addBodyParameter("eventId", eventid);

        doPost(url, params, success, failure);
    }






}












