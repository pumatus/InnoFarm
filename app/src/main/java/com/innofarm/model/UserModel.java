package com.innofarm.model;

/**
 * Created by dell on 2015/9/25.
 */
public class UserModel {

    public int id;
    public String USER_ID;//用户编号
    public String USER_ACNT;    //用户帐号
    public String INIT_PWD;//初始密码
    public String PRE_PWD;//前回密码
    public String NOW_PWD;//用户密码
    public String USER_IDNO;    //用户身份证号	
    public String USER_NAME;    //用户姓名
    public String USER_TYPE;//用户类型
    public String USER_ST;//用户状态
    public String USER_PST;//用户职位
    public String USER_START_DATE;//	用户开设日期
    public String USER_END_DATE;//	用户停用日期
    public String LAST_UP_TIME;//	最终更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getNOW_PWD() {
        return NOW_PWD;
    }

    public void setNOW_PWD(String NOW_PWD) {
        this.NOW_PWD = NOW_PWD;
    }

    public String getUSER_IDNO() {
        return USER_IDNO;
    }

    public void setUSER_IDNO(String USER_IDNO) {
        this.USER_IDNO = USER_IDNO;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getUSER_START_DATE() {
        return USER_START_DATE;
    }

    public void setUSER_START_DATE(String USER_START_DATE) {
        this.USER_START_DATE = USER_START_DATE;
    }

    public String getLAST_UP_TIME() {
        return LAST_UP_TIME;
    }

    public void setLAST_UP_TIME(String LAST_UP_TIME) {
        this.LAST_UP_TIME = LAST_UP_TIME;
    }

    public String getUSER_END_DATE() {
        return USER_END_DATE;
    }

    public void setUSER_END_DATE(String USER_END_DATE) {
        this.USER_END_DATE = USER_END_DATE;
    }

    public String getUSER_PST() {
        return USER_PST;
    }

    public void setUSER_PST(String USER_PST) {
        this.USER_PST = USER_PST;
    }

    public String getUSER_ST() {
        return USER_ST;
    }

    public void setUSER_ST(String USER_ST) {
        this.USER_ST = USER_ST;
    }

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public String getPRE_PWD() {
        return PRE_PWD;
    }

    public void setPRE_PWD(String PRE_PWD) {
        this.PRE_PWD = PRE_PWD;
    }

    public String getINIT_PWD() {
        return INIT_PWD;
    }

    public void setINIT_PWD(String INIT_PWD) {
        this.INIT_PWD = INIT_PWD;
    }

    public String getUSER_ACNT() {
        return USER_ACNT;
    }

    public void setUSER_ACNT(String USER_ACNT) {
        this.USER_ACNT = USER_ACNT;
    }
}
