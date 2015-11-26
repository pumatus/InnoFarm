package com.innofarm.model;

import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;

/**
 *事件
 */
@Table(name = "event")


public class EventModel {


    @NotNull
    @Id
    public  String eventId ;//= "id"; //事件ID
    @NotNull
    @Column (column = "eventTime")
    public  String eventTime;// = "time";//事件时间
    @Column (column = "eventOpName")
    public  String eventOpName ;//= "name";//事件执行者
    @NotNull
    @Column (column = "eventSummary")
    public  String eventSummary;// = "log";//事件概述
    @NotNull
    @Column (column = "logSt")
    public  String logSt ;//= "name";//日志状态
    @NotNull
    @Column (column = "logChgTime")
    public  String logChgTime ;//= "_time";//日志更改时间
    @NotNull
    @Column (column = "recordUid")
    public  String recordUid;// = "Uid";//记录用户ID
    @NotNull
    @Column (column = "recordTime")
    public  String recordTime;// = "now_time";//记录时间


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventOpName() {
        return eventOpName;
    }

    public void setEventOpName(String eventOpName) {
        this.eventOpName = eventOpName;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    public String getLogSt() {
        return logSt;
    }

    public void setLogSt(String logSt) {
        this.logSt = logSt;
    }

    public String getLogChgTime() {
        return logChgTime;
    }

    public void setLogChgTime(String logChgTime) {
        this.logChgTime = logChgTime;
    }

    public String getRecordUid() {
        return recordUid;
    }

    public void setRecordUid(String recordUid) {
        this.recordUid = recordUid;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
