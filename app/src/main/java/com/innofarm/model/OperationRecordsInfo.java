package com.innofarm.model;

/**
 * Created by dell on 2015/11/8.
 */
public class OperationRecordsInfo {


    public String cattleNo;
    public String eventSummary;
    public String eventDetail;
    public String eventTime;



    public String getCattleNo() {
        return cattleNo;
    }

    public void setCattleNo(String cattleNo) {
        this.cattleNo = cattleNo;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public String getEventTime() {
        return eventTime;
    }

    @Override
    public String toString() {
        return "OperationRecordsModel{" +
                "cattleNo='" + cattleNo + '\'' +
                ", eventSummary='" + eventSummary + '\'' +
                ", eventDetail='" + eventDetail + '\'' +
                ", eventTime='" + eventTime + '\'' +
                '}';
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;


    }
}
