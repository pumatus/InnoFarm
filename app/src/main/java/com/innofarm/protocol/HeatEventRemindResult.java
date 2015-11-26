package com.innofarm.protocol;

import com.innofarm.model.HeatEventRemindInfo;

import java.util.ArrayList;

/**
 * Created by dell on 2015/10/31.
 */
public class HeatEventRemindResult {
    public String return_sts;
    public ArrayList<HeatEventRemindInfo> cntList;

    public String getReturn_sts() {
        return return_sts;
    }

    public ArrayList<HeatEventRemindInfo> getCntList() {
        return cntList;
    }


}
