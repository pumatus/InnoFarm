package com.innofarm.manager;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.innofarm.InnoFarmApplication;
import com.innofarm.InnoFarmConstant;
import com.innofarm.MainActivity;
import com.innofarm.external.MyRequestCallBack;
import com.innofarm.model.CalveModel;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.EventDef;
import com.innofarm.model.EventModel;
import com.innofarm.model.REQUESTModel;
import com.innofarm.protocol.LoginInfo;
import com.innofarm.protocol.LoginResult;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: syc
 * Date: 2015/10/26
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:数据库更新时上传
 */
public class TestManager {


   // private static DbUtils dbutils = DbUtils.create(InnoFarmApplication.getAppContext(), "Innofarm");
    private static DbUtils dbutils = InnoFormDB.getDbUtils();


    //public  TestManager(){
    //  dbutils
    // }


    public static void CreateCattle(CattleModel cattle, EventModel event, ArrayList<CattleAddInfoModel> arr) {//

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();


        params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);

        params.put("cattleId", cattle.cattleId);
        params.put("cattleNO", cattle.cattleNo);
        params.put("cattleSor", cattle.cattleSor);
        params.put("cattleBRT", cattle.cattleBrt);
        params.put("cattleSex", cattle.cattleSex);

        params.put("barnName", arr.get(0).getInfoContent());
        params.put("cattleType", arr.get(1).getInfoContent());
        params.put("cattleMotherId", arr.get(2).getInfoContent());
        params.put("cattleFatherId", arr.get(3).getInfoContent());

       /* params.put("barnName", "13");//牛舍
        params.put("cattleType", "12");//品种
        params.put("cattleMotherId", "11");//母系编号
        params.put("cattleFatherId", "10");//父系编号*/

        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30313A3A303A");


        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }




/**发情
 * */
   public static void EventHeat(CattleModel cattle, EventModel event, Map<String, String> arr) {//

        try {
            //dbutils.dropTable(REQUESTModel.class);
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();




         params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);

        params.put("cattleId", cattle.cattleId);
        params.put("cattleNO", cattle.cattleNo);



        params.put("rutTime", arr.get("发情日期"));// 发情时间
        params.put("descript", arr.get("现象描述"));//现象描述
        params.put("judge", arr.get("配种判断"));//配种判断
        params.put("note", arr.get("备注"));//备注
       params.put("recorder", arr.get("观察者"));//观察者

        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30323A3A303A");


        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

/*



    */
/**配种
     * */


    public static void EventBreeding(CattleModel cattle, EventModel event,  Map<String, String> arr) {//

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();



        params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);

        params.put("cattleId", cattle.cattleId);
        params.put("cattleNO", cattle.cattleNo);



        params.put("breedTime", arr.get("配种日期"));//配种时间
        params.put("frozenID", arr.get("冻精编号"));//冻精编号
        params.put("note", arr.get("备注"));//备注
        params.put("operater", arr.get("配种操作者"));//配种操作者

        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30333A3A303A");


        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }




/**妊检
     **/


    public static void EventPregExam(CattleModel cattle, EventModel event, Map<String, String> arr) {//

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();




        params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);

        params.put("cattleId", cattle.cattleId);
        params.put("cattleNO", cattle.cattleNo);



        params.put("examTime", arr.get("妊检日期"));//妊检时间
        params.put("examWay", arr.get("妊检方式"));//妊检方式
        params.put("examResult", arr.get("妊检结果"));//妊检结果
        params.put("abortReason", arr.get("流产原因"));//流产原因
        params.put("note", arr.get("备注"));//备注
        params.put("recorder", arr.get("妊检操作者"));//观察者


        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30343A3A303A");


        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }



/**
* 干奶
*
*/


    public static void DryMilk(CattleModel cattle, EventModel event, Map<String, String> arr) {//

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();



        params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);

        params.put("cattleId", cattle.cattleId);
        params.put("cattleNO", cattle.cattleNo);


        params.put("dryTime", arr.get("干奶日期"));//干奶日期
        params.put("note", arr.get("备注"));//备注
        params.put("recorder", arr.get("干奶操作者"));//观察者


        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30353A3A303A");

        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }


/**
     *产犊
     * */


    public static void Calving(CattleModel cattle, EventModel event, Map<String, String> arr) {//

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();


        params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);

        params.put("cattleId", cattle.cattleId);
        params.put("cattleNO", cattle.cattleNo);


        params.put("calvingTime", arr.get("产犊日期"));//产犊日期
        params.put("calvingType", arr.get("生产方式"));//生产方式
        params.put("calvingAmount", arr.get("犊牛数量"));//产犊数量
        params.put("calvingResult", arr.get("分娩结果"));//分娩结果
        params.put("newCowHouse", arr.get("新牛舍名"));//新牛舍名
        params.put("note", arr.get("备注"));//备注
        params.put("recorder",arr.get("接产者"));//观察者

        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30383A3A303A");

        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }


/**
     *牛只转移
     **/


    public static void CattleTrans(CattleModel cattle, EventModel event, Map<String, String> arr) {//

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();



        params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);

        params.put("cattleId", cattle.cattleId);
        params.put("cattleNO", cattle.cattleNo);


        params.put("transferTime", arr.get("转移日期"));//转舍时间
        params.put("transferReason", arr.get("转移原因"));//转舍原因
        params.put("newCowHouse", arr.get("新牛舍名"));//新牛舍名
        params.put("note", arr.get("备注"));//备注
        params.put("operater", arr.get("操作者"));//操作者


        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30363A3A303A");

        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }


/**
     *牛只淘汰
     **/


    public static void CattleEliminat(CattleModel cattle, EventModel event, Map<String, String> arr) {//

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();

        params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);

        params.put("cattleId", cattle.cattleId);
        params.put("cattleNO", cattle.cattleNo);


        params.put("eliminateTime", arr.get("淘汰日期"));//淘汰日期
        params.put("eliminateReason", arr.get("淘汰原因"));//淘汰原因
        params.put("handleType", arr.get("处理方式"));//处理
        params.put("note", arr.get("备注"));//备注
        params.put("operater", "");//操作者


        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30373A3A303A");

        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }



/**
     *个人提醒
     * */


    public static void PersonRemind( EventModel event, Map<String, String> arr) {//

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();





        params.put("eventId", event.eventId);
        params.put("eventTime", event.eventTime);
        params.put("eventOpName", event.eventOpName);
        params.put("eventSummary", event.eventSummary);
        params.put("logSt", event.logSt);
        params.put("logChgTime", event.logChgTime);
        params.put("recordUid", event.recordUid);
        params.put("recordTime", event.recordTime);



        params.put("alertTime",arr.get("提醒日期"));//提醒时间
        params.put("address", arr.get("地点"));//提醒地点
        params.put("descript", arr.get("描述"));//提醒描述



        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1006.do?fw.excute.event=30313A3A303A");

        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }





    /**
     *犊牛信息
     * */


    public static void createCalve( CalveModel calve, Map<String, String> arr) {

        try {
            dbutils.createTableIfNotExist(REQUESTModel.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();




        params.put("cattleNo", calve.calvesNo);//牛只号
        params.put("sex", InnoFormDB.getMapNum(calve.calvesSex));//犊牛性别
        params.put("shape", InnoFormDB.getMapNum(calve.calvesSig));//生命体征
        params.put("newHome", arr.get("新牛舍名"));//新牛舍名
        params.put("weight", arr.get("体重"));//出生体重
        params.put("userId", InnoFarmConstant.UserID);//添加者用户ID
        params.put("eventId", calve.eventId);//事件Id



        Gson mGson = new Gson();
        String gson = mGson.toJson(params).toString();

        REQUESTModel request = new REQUESTModel();
        request.setContent(gson);
        request.setUrl("1002.do?fw.excute.event=30393A3A303A");

        try {
            dbutils.save(request);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }














}