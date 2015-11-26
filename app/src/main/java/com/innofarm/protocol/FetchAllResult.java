package com.innofarm.protocol;

import com.innofarm.model.CalveAddInfoModel;
import com.innofarm.model.CalveModel;
import com.innofarm.model.CattleAddInfoModel;
import com.innofarm.model.CattleModel;
import com.innofarm.model.E_C_RESP;
import com.innofarm.model.EventDef;
import com.innofarm.model.EventModel;
import com.innofarm.model.NumMappingModel;

import java.util.ArrayList;

/**
 * User: syc
 * Date: 2015/10/26
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:1003
 */
public class FetchAllResult {
    private String return_sts;
    private String date;
    private ArrayList<EventModel> logList;
    private ArrayList<E_C_RESP> ecrList;
    private ArrayList<EventDef> edList;
    private ArrayList<CattleModel> ciList;
    private ArrayList<CattleAddInfoModel>  ciaList;

    private ArrayList<CalveModel>  cirList;
    private ArrayList<CalveAddInfoModel>  ciraList;
    private ArrayList<NumMappingModel>  cList;


    public String getReturn_sts() {
        return return_sts;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<EventModel> getLogList() {
        return logList;
    }

    public ArrayList<EventDef> getEdList() {
        return edList;
    }

    public ArrayList<CalveModel> getCirList() {
        return cirList;
    }

    public ArrayList<NumMappingModel> getcList() {
        return cList;
    }

    public ArrayList<CalveAddInfoModel> getCiraList() {
        return ciraList;
    }

    public ArrayList<CattleAddInfoModel> getCiaList() {
        return ciaList;
    }

    public ArrayList<CattleModel> getCiList() {
        return ciList;
    }

    public ArrayList<E_C_RESP> getEcrList() {
        return ecrList;
    }


    @Override
    public String toString() {
        return "FetchAllResult{" +
                "return_sts='" + return_sts + '\'' +
                ", date='" + date + '\'' +
                ", logList=" + logList +
                ", ecrList=" + ecrList +
                ", edList=" + edList +
                ", ciList=" + ciList +
                ", ciaList=" + ciaList +
                ", cirList=" + cirList +
                ", ciraList=" + ciraList +
                ", cList=" + cList +
                '}';
    }
}
