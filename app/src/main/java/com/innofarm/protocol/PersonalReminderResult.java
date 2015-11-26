package com.innofarm.protocol;

import com.innofarm.model.PersonalRemindInfo;

import java.util.ArrayList;

/**
 * Created by dell on 2015/11/5.
 */
public class PersonalReminderResult {

    public String return_sts;
    public ArrayList<PersonalRemindInfo> finalList;


    public ArrayList getFinalList() {
        return finalList;
    }

    public String getReturn_sts() {
        return return_sts;
    }


    @Override
    public String toString() {
        return "PersonalReminderResult{" +
                "return_sts='" + return_sts + '\'' +
                ", finalList=" + finalList +
                '}';
    }



}
