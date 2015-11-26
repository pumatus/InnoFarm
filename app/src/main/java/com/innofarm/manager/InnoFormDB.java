package com.innofarm.manager;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.innofarm.model.CalveModel;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.E_C_RESP;
import com.innofarm.model.EventDef;
import com.innofarm.model.EventModel;
import com.innofarm.model.NumMappingModel;
import com.innofarm.model.REQUESTModel;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;
import java.util.UUID;

/**
 * Created by dell on 2015/9/15.
 * <p/>
 * <p/>
 * 数据库表的创建，  dbutils 的获取，
 */


public class InnoFormDB {
    Context context;
    static DbUtils dbUtils;


    public InnoFormDB(Context context) {
        this.context = context;
        //创建数据库

        dbUtils = DbUtils.create(this.context, "InfaDB");


    }

    /**
     * 创建表
     */
    public void createTable() {
        try {
            // 删除表
           /* dbUtils.dropTable(CattleModel.class);
            dbUtils.dropTable(EventModel.class);
            dbUtils.dropTable(EventDef.class);
            dbUtils.dropTable(E_C_RESP.class);
            dbUtils.dropTable(CalveModel.class);
            dbUtils.dropTable(REQUESTModel.class);
            dbUtils.dropTable(CattleAddInfoModel.class);*/

            dbUtils.createTableIfNotExist(CattleModel.class);
            dbUtils.createTableIfNotExist(EventModel.class);
            dbUtils.createTableIfNotExist(E_C_RESP.class);
            dbUtils.createTableIfNotExist(EventDef.class);
            dbUtils.createTableIfNotExist(CalveModel.class);
            dbUtils.createTableIfNotExist(REQUESTModel.class);
            dbUtils.createTableIfNotExist(CattleAddInfoModel.class);
            dbUtils.createTableIfNotExist(NumMappingModel.class);
            // dbUtils.createTableIfNotExist(CattleModel.class);

           /* for (int i = 0; i < 10; i++) {
                CattleModel cattleModel = new CattleModel();
                cattleModel.setCATTLE_ID(String.valueOf(i));
                cattleModel.setCATTLE_NO(String.valueOf(i + 5));
                dbUtils.save(cattleModel);
            }

            for (int i = 0; i < 10; i++) {
                CalveModel calveModel = new CalveModel();
                calveModel.setCALVES_ID(UUID.randomUUID().toString());
                calveModel.setCALVES_BIR(String.valueOf(System.currentTimeMillis()));
                calveModel.setCALVES_NO(String.valueOf(i));
                dbUtils.save(calveModel);
            }*/

        } catch (DbException e) {
            Log.i("数据库", e.toString());
            e.printStackTrace();
        }

    }

    /**
     * 取得
     */
    public static DbUtils getDbUtils() {
        return dbUtils;
    }


    /**
     * 删除表
     */
    public static void dropTable() {


        try {
            dbUtils.dropTable(CattleModel.class);
            dbUtils.dropTable(EventModel.class);
            dbUtils.dropTable(EventDef.class);
            dbUtils.dropTable(E_C_RESP.class);
            dbUtils.dropTable(CalveModel.class);
            dbUtils.dropTable(CattleAddInfoModel.class);
            dbUtils.dropTable(NumMappingModel.class);



        } catch (DbException e) {
            Log.i("数据库", e.toString());
            e.printStackTrace();
        }

    }

    /**
     * 保存数据
     */
    public static <T> void save(T t) {


        try {
            dbUtils.save(t);
        } catch (DbException e) {
            Log.i("数据库", e.toString());
            e.printStackTrace();
        }

    }

    /**
     * 获取对应选项
     */
    public static String getMapNum(String str) {


        try {
            NumMappingModel map = dbUtils.findFirst(Selector.from(NumMappingModel.class).where("codeCaption", "=", str));
            return map.getCodeId();
        } catch (DbException e) {
            Log.i("数据库", e.toString());
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取对应选项表
     */
    public static List<NumMappingModel> getMapStr(String str) {


        try {

            return dbUtils.findAll(Selector.from(NumMappingModel.class).where("codeType", "=", str));
        } catch (DbException e) {
            Log.i("数据库", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询数据
     */
    public static <T> T find(Class<T> cls, String str, String str1, String str2) {

        try {
            return dbUtils.findFirst(Selector.from(cls).where(str, str1, str2));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
