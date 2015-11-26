package com.innofarm.protocol;

import com.innofarm.model.OperationRecordsInfo;

import java.util.ArrayList;

/**
 * Created by dell on 2015/11/8.
 */
public class OperationRecordsResult {

    public String return_sts;
    public ArrayList<OperationRecordsInfo> finalList;

    public String getReturn_sts() {
        return return_sts;
    }

    public void setReturn_sts(String return_sts) {
        this.return_sts = return_sts;
    }

    public ArrayList<OperationRecordsInfo> getFinalList() {
        return finalList;
    }

    public void setFinalList(ArrayList<OperationRecordsInfo> finalList) {
        this.finalList = finalList;
    }


    @Override
    public String toString() {
        return "OperationRecordsResult{" +
                "return_sts='" + return_sts + '\'' +
                ", finalList=" + finalList +
                '}';
    }
}
