package com.innofarm.model;

import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by dell on 2015/11/16.
 */
@Table(name = "NumMapping")
public class NumMappingModel {

    public int id;


    public String codeType	;
    public String codeId;//编号
    public String codeCaption;//内容


    public String ADD_DATE;
    public String ADD_USER_ID;
    public String ADD_IP_ADDR;
    public String UPD_DATE;
    public String UPD_USER_ID;
    public String UPD_IP_ADDR;
    public String DEL_FLG;


    public int getId() {
        return id;
    }

    public String getCodeType() {
        return codeType;
    }

    public String getCodeCaption() {
        return codeCaption;
    }

    public String getADD_DATE() {
        return ADD_DATE;
    }

    public String getADD_IP_ADDR() {
        return ADD_IP_ADDR;
    }

    public String getUPD_DATE() {
        return UPD_DATE;
    }

    public String getDEL_FLG() {
        return DEL_FLG;
    }

    public String getUPD_IP_ADDR() {
        return UPD_IP_ADDR;
    }

    public String getUPD_USER_ID() {
        return UPD_USER_ID;
    }

    public String getADD_USER_ID() {
        return ADD_USER_ID;
    }

    public String getCodeId() {
        return codeId;
    }


    @Override
    public String toString() {
        return "NumMappingModel{" +
                "id=" + id +
                ", codeType='" + codeType + '\'' +
                ", codeId='" + codeId + '\'' +
                ", codeCaption='" + codeCaption + '\'' +
                ", ADD_DATE='" + ADD_DATE + '\'' +
                ", ADD_USER_ID='" + ADD_USER_ID + '\'' +
                ", ADD_IP_ADDR='" + ADD_IP_ADDR + '\'' +
                ", UPD_DATE='" + UPD_DATE + '\'' +
                ", UPD_USER_ID='" + UPD_USER_ID + '\'' +
                ", UPD_IP_ADDR='" + UPD_IP_ADDR + '\'' +
                ", DEL_FLG='" + DEL_FLG + '\'' +
                '}';
    }
}
