package com.innofarm.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Id;

/**
 *牛只
 */

@Table(name = "cattle")
public class CattleModel {

    public String getCattleId() {
        return cattleId;
    }

    public String getCattleNo() {
        return cattleNo;
    }

    public String getCattleSt() {
        return cattleSt;
    }

    public String getCattleSor() {
        return cattleSor;
    }

    public String getCattleBrt() {
        return cattleBrt;
    }

    public String getCattleSex() {
        return cattleSex;
    }

    public String getCattleODate() {
        return cattleODate;
    }

    public String getLastUpTime() {
        return lastUpTime;
    }


    @Override
    public String toString() {
        return "CattleModel{" +
                "cattleId='" + cattleId + '\'' +
                ", cattleNo='" + cattleNo + '\'' +
                ", cattleSt='" + cattleSt + '\'' +
                ", cattleSor='" + cattleSor + '\'' +
                ", cattleBrt='" + cattleBrt + '\'' +
                ", cattleSex='" + cattleSex + '\'' +
                ", cattleODate='" + cattleODate + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }

    @Id
    public  String cattleId ;//牛只编号
    @NotNull
    @Column(column = "cattleNo")
    public  String cattleNo ;//牛只号
    @NotNull
    @Column(column = "cattleSt")
    public  String cattleSt ;//牛只状态
    @Column(column = "cattleSor")
    public  String cattleSor ;//牛只来源
   /* @Column(column = "CATTLE_VAT")
    public  String CATTLE_VAT ;//牛只品种*/
    @Column(column = "cattleBrt")
    public  String cattleBrt ;//出生日期
    @Column(column = "cattleSex")
    public  String cattleSex ;//性别
    @NotNull
    @Column(column = "cattleODate")
    public String  cattleODate; //牛只登记时间
    @NotNull
    @Column(column = "lastUpTime")
    public  String lastUpTime ;//最终更新时间


    public void setCattleId(String cattleId) {
        this.cattleId = cattleId;
    }

    public void setCattleNo(String cattleNo) {
        this.cattleNo = cattleNo;
    }

    public void setCattleSt(String cattleSt) {
        this.cattleSt = cattleSt;
    }

    public void setCattleSor(String cattleSor) {
        this.cattleSor = cattleSor;
    }

    public void setCattleBrt(String cattleBrt) {
        this.cattleBrt = cattleBrt;
    }

    public void setCattleSex(String cattleSex) {
        this.cattleSex = cattleSex;
    }

    public void setCattleODate(String cattleODate) {
        this.cattleODate = cattleODate;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }
}
