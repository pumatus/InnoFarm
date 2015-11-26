package com.innofarm.external;

import com.lidroid.xutils.exception.HttpException;

/**
 * User: syc
 * Date: 2015/9/8
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 网络请求接口
 */

public interface SuccessRequestCallBack<T> {

    public void onSuccess(T responseInfo);
}
