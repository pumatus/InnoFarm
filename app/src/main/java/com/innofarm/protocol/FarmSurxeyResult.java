package com.innofarm.protocol;

import com.innofarm.model.NiuQunSurveyInfo;

import java.util.ArrayList;

/**
 * Created by dell on 2015/11/11.
 */
public class FarmSurxeyResult {
    public String return_sts;
    public ArrayList<NiuQunSurveyInfo> surveyList;

    public String getReturn_sts() {
        return return_sts;
    }

    public void setReturn_sts(String return_sts) {
        this.return_sts = return_sts;
    }

    public ArrayList getSurveyList() {
        return surveyList;
    }

    public void setSurveyList(ArrayList surveyList) {
        this.surveyList = surveyList;
    }
}
