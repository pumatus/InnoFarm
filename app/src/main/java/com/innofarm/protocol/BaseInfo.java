package com.innofarm.protocol;

import java.io.Serializable;

/**
 * User: lengjiqiang
 * Date: 2015/6/2
 * Time: 17:53
 * Email: jqleng@isoftstone.com
 * Dest:
 */
public class BaseInfo implements Serializable {
    private String function_id;
    private String return_flag;
    private String return_message;

    public String getFunction_id() {
        return function_id;
    }

    public void setFunction_id(String function_id) {
        this.function_id = function_id;
    }

    public String getReturn_flag() {
        return return_flag;
    }

    public void setReturn_flag(String return_flag) {
        this.return_flag = return_flag;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }
}

