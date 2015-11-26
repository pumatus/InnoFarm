package com.innofarm.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 牛只事件关系表
 */
@Table(name = "E_C_RESP")
public class E_C_RESP {

    public int id;
    @NotNull
    @Column(column = "eventId")
    public String eventId;//	事件编号	char
    @NotNull
    @Column(column = "cattleId")
    public String cattleId;//	牛只编号	char

    public int getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getCattleId() {
        return cattleId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setCattleId(String cattleId) {
        this.cattleId = cattleId;
    }
}
