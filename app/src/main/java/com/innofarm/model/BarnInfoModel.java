package com.innofarm.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 牛舍信息
 */
@Table(name="BarnInfo")
public class BarnInfoModel {


    @Id
    public String  BARN_ID	;//牛舍ID
    @NotNull
    @Column(column = "BARN_NAME")
    public String BARN_NAME;//	牛舍名
    @Column(column = "BARN_TYPE")
    public String  BARN_TYPE;//	牛舍类型
    @NotNull
    @Column(column = "LAST_UP_TIME")
    public String  LAST_UP_TIME;//	最终更新时间


    public String getBARN_ID() {
        return BARN_ID;
    }

    public String getBARN_NAME() {
        return BARN_NAME;
    }

    public String getBARN_TYPE() {
        return BARN_TYPE;
    }

    public String getLAST_UP_TIME() {
        return LAST_UP_TIME;
    }


    @Override
    public String toString() {
        return "BarnInfoModel{" +
                "BARN_ID='" + BARN_ID + '\'' +
                ", BARN_NAME='" + BARN_NAME + '\'' +
                ", BARN_TYPE='" + BARN_TYPE + '\'' +
                ", LAST_UP_TIME='" + LAST_UP_TIME + '\'' +
                '}';
    }
}
