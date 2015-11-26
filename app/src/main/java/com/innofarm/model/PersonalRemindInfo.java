package com.innofarm.model;

/**
 * Created by dell on 2015/11/8.
 */
public class PersonalRemindInfo {
    public String eventId;
    public String address;
    public String descript;
    public String alertTime;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    @Override
    public String toString() {
        return "PersonalRemindInfo{" +
                "eventId='" + eventId + '\'' +
                ", address='" + address + '\'' +
                ", descript='" + descript + '\'' +
                ", alertTime='" + alertTime + '\'' +
                '}';
    }
}

