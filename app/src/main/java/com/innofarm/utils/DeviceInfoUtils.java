package com.innofarm.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.innofarm.InnoFarmApplication;

import java.util.UUID;

/**
 * 获取设备某些信息
 * User: syc
 * Date: 2015/9/6
 * Time: 10:06
 * Email: ycshi@isoftstone.com
 * Dest:  获取设备某些信息
 */
public class DeviceInfoUtils {

	/**
	 * 检测网络是否可用
	 * @return
	 */
	public static boolean isNetworkConnected() {
		try {
			Context context;
            context = InnoFarmApplication.getAppContext();
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			return ni != null && ni.isConnectedOrConnecting();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/** 根据包名检测是否安装 */
	public static boolean isInstall(String packageName) {
		try {
			PackageManager manager = InnoFarmApplication.getAppContext().getPackageManager();
			PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return null != info;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取设备IMEI
	 * 
	 * @return
	 */
	public static String getDeviceIMEI() {
		Context paramContext = InnoFarmApplication.getAppContext();
		if (paramContext != null) {
			TelephonyManager telephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
			// //判断应用是否有权限
			// if (PackageManager.PERMISSION_GRANTED ==
			// paramContext.getPackageManager().checkPermission(Manifest.permission.READ_PHONE_STATE,
			// paramContext.getPackageName())) {
			if (TextUtils.isEmpty(telephonyManager.getDeviceId())) {
				return getDeviceMac();
			}
			return telephonyManager.getDeviceId();
			// } else {
			// return null;
			// }
		} else {
			// 防止出错，如果获取不到创建一个随机id
			return null;
		}
	}

	/**
	 * 获取设备MAC
	 * 
	 * @return
	 */
	public static String getDeviceMac() {
		String result = "";
		try {
			WifiManager wifiManager = (WifiManager) InnoFarmApplication.getAppContext().getSystemService(InnoFarmApplication.getAppContext().WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			result = wifiInfo.getMacAddress();
			result = result.replace(":", "");
			return result.trim();
		} catch (Exception e) {
			return result;
		}
	}

	/**
	 * 获取sim卡信息 (1)有卡并且获取传手机号 (2)有卡获取不到传1 (3)无卡传-1
	 * 
	 * @param paramsContext
	 * @return
	 */
	public static String getSIMCardInfo(Context paramsContext) {
		try {
			TelephonyManager telMgr = null;
			telMgr = (TelephonyManager) paramsContext.getSystemService(paramsContext.TELEPHONY_SERVICE);
			if (telMgr.getSimState() == telMgr.SIM_STATE_READY) {
				if (TextUtils.isEmpty(telMgr.getLine1Number())) {
					return telMgr.getLine1Number();
				}
				return "1";
			} else if (telMgr.getSimState() == telMgr.SIM_STATE_ABSENT) {
				return "-1";
			}
		} catch (Exception e) {
			return "-1";
		}
		return "-1";
	}

	/**
	 * 时间戳加随机数，为子板生成一个id
	 * 
	 * @return
	 */
	public static String getChannel() {
		String str = null;
		str = String.valueOf(System.currentTimeMillis());
		String aa = UUID.randomUUID().toString().toString().replaceAll("-", "");
		aa = aa.substring(0, 7);
		str = str + aa;
		return str;
	}
}
