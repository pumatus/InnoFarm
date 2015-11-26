package com.innofarm.protocol;

import com.innofarm.model.EventRemindCountInfo;

import java.util.ArrayList;

/**
 * Created by dell on 2015/11/4.
 */
public class EventRemindCountResult {

    public String return_sts;
    public ArrayList<EventRemindCountInfo> AlertCnt;

    public String getReturn_sts() {
        return return_sts;
    }

    public ArrayList<EventRemindCountInfo> getAlertCnt() {
        return AlertCnt;
    }

    @Override
    public String toString() {
        return "EventRemindCountResult{" +
                "return_sts='" + return_sts + '\'' +
                ", AlertCnt=" + AlertCnt +
                '}';
    }
}
