package com.innofarm.model;

/**
 * Created by dell on 2015/10/31.
 *发情事件提醒
 */
public class HeatEventRemindModel {

    public String alertId;
    public String cattleId;
    public String cattleNo;
    public String alertDate;
     public String alertType;
    public String alertReason;


    public String getCattleNo() {
        return cattleNo;
    }

    public String getAlertId() {
        return alertId;
    }

    public String getCattleId() {
        return cattleId;
    }

    public String getAlertType() {
        return alertType;
    }

    public String getAlertReason() {
        return alertReason;
    }

    public String getAlertDate() {
        return alertDate;
    }
}
