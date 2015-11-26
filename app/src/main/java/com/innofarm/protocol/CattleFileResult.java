package com.innofarm.protocol;


import com.innofarm.model.CattleFileEventInfo;
import com.innofarm.model.CattleFileInfo;
import com.innofarm.model.EventAlertModel;

import java.util.ArrayList;

/**
 * Created by dell on 2015/11/4.
 */
public class CattleFileResult {


    private String return_sts;
    private ArrayList<CattleFileInfo> cattleInfo;
    private ArrayList<CattleFileEventInfo> cList;

    private ArrayList<EventAlertModel> eventAlertList;

    public ArrayList<CattleFileInfo> getCattleInfo() {
        return cattleInfo;
    }

    public ArrayList<CattleFileEventInfo> getcList() {
        return cList;
    }

    public String getReturn_sts() {
        return return_sts;
    }

    public ArrayList<EventAlertModel> getEventAlertList() {
        return eventAlertList;
    }



    @Override
    public String toString() {
        return "CattleFileResult{" +
                "return_sts='" + return_sts + '\'' +
                ", cattleInfo=" + cattleInfo +
                ", cList=" + cList +
                ", eventAlertList=" + eventAlertList +
                '}';
    }
}
