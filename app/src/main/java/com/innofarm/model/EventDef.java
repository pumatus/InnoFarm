package com.innofarm.model;

import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Column;


/**
 * Created by dell on 2015/10/10.
 */
@Table(name = "EventDefModel")
public class EventDef {


    int id;
    @NotNull
    @Column(column = "eventId")
    public String eventId;//事件编号	char
    @NotNull
    @Column(column = "eventName")
    public String eventName;//	事件类型描述	varchar
    @NotNull
    @Column(column = "eventIns")
    public String eventIns;//事件内容	varchar

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventIns() {
        return eventIns;
    }

    public void setEventIns(String eventIns) {
        this.eventIns = eventIns;
    }
}
