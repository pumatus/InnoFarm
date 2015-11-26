package com.innofarm.protocol;

import java.io.Serializable;

/**
 * User: syc
 * Date: 2015/9/6
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:login
 */
public class LoginResult  {//implements Serializable
   // private BaseInfo head;
    private LoginInfo userInfo;

   /* public BaseInfo getHead() {
        return head;
    }

    public void setHead(BaseInfo head) {
        this.head = head;
    }*/

    public LoginInfo getBody() {
        return userInfo;
    }

   /* public void setBody(LoginInfo body) {
        this.body = body;
    }*/

   /* @Override
    public String toString() {
        return body.toString();
    }*/
}
