package com.innofarm.protocol;

import com.innofarm.model.UserModel;

import java.io.Serializable;

/**
 * Created by dell on 2015/9/28.
 */
public class LoginInfo {// implements Serializable

    private String user_id;
    private String user_type;
    private String user_status;
   // private String user_service_status;


    public String getUser_id() {
        return user_id;
    }

    public String getUser_status() {
        return user_status;
    }

    public String getUser_type() {
        return user_type;
    }

   // public String getUser_service_status() {
    //    return user_service_status;
   // }


    @Override
    public String toString() {
        return "LoginInfo{" +
                "user_id='" + user_id + '\'' +
                ", user_type='" + user_type + '\'' +
                ", user_status='" + user_status + '\'' +
                //", user_service_status='" + user_service_status + '\'' +
                '}';
    }


}
