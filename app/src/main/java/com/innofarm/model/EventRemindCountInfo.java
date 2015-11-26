package com.innofarm.model;

/**
 * Created by dell on 2015/11/4.
 */
public class EventRemindCountInfo {

    private String alertType;

    public String getAlertType() {
        return alertType;
    }

    public String getCnt() {
        return cnt;
    }

    private String cnt;

    @Override
    public String toString() {
        return "EventRemindCountInfo{" +
                "alertType='" + alertType + '\'' +
                ", cnt='" + cnt + '\'' +
                '}';
    }
}
