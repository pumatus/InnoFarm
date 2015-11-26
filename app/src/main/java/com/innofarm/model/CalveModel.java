package com.innofarm.model;

import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Column;
/**
 * Created by dell on 2015/10/14.
 *
 */

@Table (name ="calve")
public class CalveModel {
/*
    @Id
    public int Id;
*/




    @Id
    public String calvesId;//犊牛ID	char
    @NotNull
    @Column (column = "eventId")
    public String eventId;//	事件ID	char
    @NotNull
    @Column (column = "calvesNo")
    public String calvesNo;//	犊牛号	varchar
    @NotNull
    @Column (column = "calvesBir")
    public String calvesBir;// 	出生日期	date
    @NotNull
    @Column (column = "calvesSex")
    public String calvesSex;//	犊牛性别	char
    @NotNull
    @Column (column = "calvesSig")
    public String calvesSig;//	生命特征	char
    @Column (column = "calvesSt")
    public String calvesSt;//	处理状态	char
    @NotNull
    @Column (column = "lastUpTime")
    public String lastUpTime;//	最终更新时间	datetime





    public String getCalvesId() {
        return calvesId;
    }

    public void setCalvesId(String calvesId) {
        this.calvesId = calvesId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCalvesNo() {
        return calvesNo;
    }

    public void setCalvesNo(String calvesNo) {
        this.calvesNo = calvesNo;
    }

    public String getCalvesBir() {
        return calvesBir;
    }

    public void setCalvesBir(String calvesBir) {
        this.calvesBir = calvesBir;
    }

    public String getCalvesSex() {
        return calvesSex;
    }

    public void setCalvesSex(String calvesSex) {
        this.calvesSex = calvesSex;
    }

    public String getCalvesSt() {
        return calvesSt;
    }

    public void setCalvesSt(String calvesSt) {
        this.calvesSt = calvesSt;
    }

    public String getCalvesSig() {
        return calvesSig;
    }

    public void setCalvesSig(String calvesSig) {
        this.calvesSig = calvesSig;
    }

    public String getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }


    @Override
    public String toString() {
        return "CalveModel{" +
                ", calvesId='" + calvesId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", calvesNo='" + calvesNo + '\'' +
                ", calvesBir='" + calvesBir + '\'' +
                ", calvesSex='" + calvesSex + '\'' +
                ", calvesSig='" + calvesSig + '\'' +
                ", calvesSt='" + calvesSt + '\'' +
                ", lastUpTime='" + lastUpTime + '\'' +
                '}';
    }
}
