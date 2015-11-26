package com.innofarm.manager;

import android.content.Context;
import com.innofarm.utils.FileUtils;

import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;


/**
 * User: syc
 * Date: 2015/9/6
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 图片处理
 * */

public class BitmapHelper {
    private BitmapHelper() {
    }

    private static BitmapUtils bitmapUtils;
    /**
     * 创建bitmapUtils  程序中 唯一存在的
     *
     * @return
     */
    public static BitmapUtils getBitmapUtils() {
        if (bitmapUtils == null) {
            /**
             * 参数2  图片缓存到本地路径
             * 参数3  内存最多占用总内存的百分比     0.05-0.8
             * 参数4  限制硬盘缓存大小
             */
            bitmapUtils = new BitmapUtils(UIUtils.getContext(),FileUtils.geIconDir(),0.5f);
        }
        return bitmapUtils;
    }
}
