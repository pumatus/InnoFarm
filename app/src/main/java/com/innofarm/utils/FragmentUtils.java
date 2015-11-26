package com.innofarm.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.innofarm.R;
import com.innofarm.external.OperatListener;


 /**
 *
 * User: syc
 * Date: 2015/9/6
 * Time: 10:06
 * Email: ycshi@isoftstone.com
 * Dest: Fragment工具类
 */

public class FragmentUtils {
	/**
	 * 添加一个Fragment
	 * 
	 * @param activity
	 * @param fragment
	 *            替换的Fragment
	 * @param id
	 * @param addToBackStack
	 *            是否加入返回栈
	 */
	private static void addFragment(FragmentActivity activity, Fragment fragment, int id, boolean addToBackStack, Bundle extras) {
		if (null == activity) {
			return;
		}
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		// 得到一个fragment 事务（类似sqlite的操作）
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// 标准专场动画TRANSIT_NONE==无动画,TRANSIT_FRAGMENT_OPEN==打开形式的动画,TRANSIT_FRAGMENT_CLOSE==关闭形式的动画。
		ft.setTransition(FragmentTransaction.TRANSIT_NONE);
		// 自定义转场动画，注意setCustomAnimations()方法必须在add、remove、replace调用之前被设置，否则不起作用。
		// ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
		// R.anim.fragment_slide_left_exit, R.anim.fragment_slide_right_enter,
		// R.anim.fragment_slide_right_exit);
		if (addToBackStack) {
			ft.addToBackStack(null);
		}
		id = (-1 == id) ? R.id.transit_container:id;
		// ft.replace(id, fragment);// 将得到的fragment替换当前的viewGroup内容，add则不替换会依次累加
		if (null != extras) {
			fragment.setArguments(extras);// 传递参数
		}
		// 第三个参数加Tag,用户标识或者通知
		ft.add(id, fragment, fragment.getClass().getSimpleName());
		// ft.add(id, fragment);
		// ft.commit();// 提交
		ft.commitAllowingStateLoss();
	}

	/**
	 * 添加一个Fragment
	 *
	 * @param activity
	 * @param to
	 *            添加的Fragment
	 */
	public static void addFragment(FragmentActivity activity, Fragment to, boolean addToBackStack) {
		addFragment(activity, to, -1, addToBackStack, null);
	}
     /**
      * 添加一个Fragment
      *
      * @param activity
      * @param to
      *            添加的Fragment
      */
     public static void addFragment(FragmentActivity activity,int id, Fragment to, boolean addToBackStack) {
         addFragment(activity, to, id, addToBackStack, null);
     }



	/**
	 * 添加一個Fragment
	 * 
	 * @param activity
	 * @param to
	 * @param addToBackStack
	 * @param extras
	 *            传递数据
	 */
	public static void addFragment(FragmentActivity activity, Fragment to, boolean addToBackStack, Bundle extras) {
		addFragment(activity, to, -1, addToBackStack, extras);
	}

	/**
	 * 替换Fragment
	 * 
	 * @param activity
	 * @param fragment
	 * @param id
	 * @param addToBackStack
	 *            是否加入返回栈
	 */
	private static void replaceFragment(FragmentActivity activity, Fragment fragment, int id, boolean addToBackStack, Bundle extras) {
		if (null == activity) {
			return;
		}
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		// 得到一个fragment 事务（类似sqlite的操作）
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);// 设置动画效果
		if (addToBackStack) {
			ft.addToBackStack(null);
		}
		id = (-1 == id) ? R.id.transit_container : id;
		ft.replace(id, fragment, fragment.getClass().getSimpleName());// 将得到的fragment替换当前的viewGroup内容，add则不替换会依次累加
		if (null != extras) {
			fragment.setArguments(extras);// 传递参数
		}
		// ft.add(id, fragment);
		// ft.commit();// 提交
		ft.commitAllowingStateLoss();
	}

	/**
	 * 替换Fragment
	 * 
	 * @param activity
	 * @param to
	 * @param addToBackStract
	 *            是否加入返回栈
	 */
	public static void replaceFragment(FragmentActivity activity, Fragment to, boolean addToBackStract) {
		replaceFragment(activity, to, -1, addToBackStract, null);
	}

	/**
	 * 替换Fragment
	 * 
	 * @param activity
	 * @param to
	 * @param addToBackStract
	 * @param extras
	 *            传递参数
	 */
	public static void replaceFragment(FragmentActivity activity, Fragment to, boolean addToBackStract, Bundle extras) {
		replaceFragment(activity, to, -1, addToBackStract, extras);
	}

	/**
	 * 从返回栈移除Fragment
	 * 
	 * @param activity
	 */
	public static void removeFragment(FragmentActivity activity) {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		if (fragmentManager != null) {
			fragmentManager.popBackStack();
		}
	}

	/**
	 * 根据fragment tag通知事件执行
	 * 
	 * @param activity
	 *            //通知界面数据更改FragmentUtils.notifyAction(getActivity(),
	 * @param action
	 * @param extras
	 */
	public static void notifyAction(FragmentActivity activity, Class<? extends Fragment> clazz, int action, Bundle extras) {
		if (null != activity && null != clazz) {
			Fragment findFragment = activity.getSupportFragmentManager().findFragmentByTag(clazz.getSimpleName());
			if (null != findFragment && findFragment.isAdded() && findFragment instanceof OperatListener) {
				((OperatListener) findFragment).onOperate(action, extras);
			}
		}
	}
}
