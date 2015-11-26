package com.innofarm.protocol;

/**
 * User: syc
 * Date: 2015/10/26
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:数据库更新时上传
 */
public class SubmitDataBaseInfo {

    private String  return_sts;

    public String getReturn_sts() {
        return return_sts;
    }

    @Override
    public String toString() {
        return "SubmitDataBaseInfo{" +
                "return_sts='" + return_sts + '\'' +
                '}';
    }
}
