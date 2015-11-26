package com.innofarm.model;

/**
 * Created by dell on 2015/11/4.
 */
public class EventAlertModel {

    public  String cattleId;
    public  String alertDate;
    public  String alertType;
    public  String alertReason;

    public String getCattleId() {
        return cattleId;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public String getAlertType() {
        return alertType;
    }

    public String getAlertReason() {
        return alertReason;
    }

    @Override
    public String toString() {
        return "EventAlertModel{" +
                "cattleId='" + cattleId + '\'' +
                ", alertDate='" + alertDate + '\'' +
                ", alertType='" + alertType + '\'' +
                ", alertReason='" + alertReason + '\'' +
                '}';
    }
}
