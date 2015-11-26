package com.innofarm.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by dell on 2015/10/22.
 */
@Table(name = "CattleAddInfo")
public class CattleAddInfoModel {

    public int id;

    @NotNull
    @Column(column = "cattleId")
    public String cattleId;//牛只编号

    @NotNull
    @Column(column = "infoType")
    public String infoType;//信息类型

    @Column(column = "InfoContent")
    public String InfoContent;//	信息内容

    @Column(column = "ADD_TIME")
    public String ADD_TIME;//	追加时间

    @Column(column = "ADD_USER_ID")
    public String ADD_USER_ID;//	追加用户ID
    @Column(column = "UPD_USER_ID")
    public String UPD_USER_ID;//更新用户ID
    @Column(column = "UPD_TIME")
    public String UPD_TIME;//	更新时间


    @Column(column = "DEL_FLG")
    public String DEL_FLG;//删除标识






    public String getCattleId() {
        return cattleId;
    }

    public void setCattleId(String cattleId) {
        this.cattleId = cattleId;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getADD_TIME() {
        return ADD_TIME;
    }

    public void setADD_TIME(String ADD_TIME) {
        this.ADD_TIME = ADD_TIME;
    }

    public String getADD_USER_ID() {
        return ADD_USER_ID;
    }

    public void setADD_USER_ID(String ADD_USER_ID) {
        this.ADD_USER_ID = ADD_USER_ID;
    }

    public String getUPD_USER_ID() {
        return UPD_USER_ID;
    }

    public void setUPD_USER_ID(String UPD_USER_ID) {
        this.UPD_USER_ID = UPD_USER_ID;
    }

    public String getUPD_TIME() {
        return UPD_TIME;
    }

    public void setUPD_TIME(String UPD_TIME) {
        this.UPD_TIME = UPD_TIME;
    }

    public String getDEL_FLG() {
        return DEL_FLG;
    }

    public void setDEL_FLG(String DEL_FLG) {
        this.DEL_FLG = DEL_FLG;
    }

    public String getInfoContent() {
        return InfoContent;
    }

    public void setInfoContent(String infoContent) {
        InfoContent = infoContent;
    }
}
