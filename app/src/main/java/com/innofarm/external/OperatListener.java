package com.innofarm.external;

import android.os.Bundle;


 /**
 * User: syc
 * Date: 2015/9/8
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: 通知Fragment界面数据变更
 * */
public interface OperatListener {
	/** 系统通知 */
	final int MSG_SYS = 1;
	/** 切换首页 */
	final int CHANGE = 2;
	/** 数据刷新 */
	final int REFRESH = 3;

	/**
	 * 当fragment界面发生操作时回调
	 * 
	 * @param optionId
	 *            : 操作id
	 */
	void onOperate(int optionId, Bundle bundle);
}
