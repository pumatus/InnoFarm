package com.innofarm.utils;

import java.io.File;

import android.os.Environment;
/**
 *
 * User: syc
 * Date: 2015/9/6
 * Time: 10:06
 * Email: ycshi@isoftstone.com
 * Dest:  管理缓存
 */

public class FileUtils {
	private static final String ROOT_DIR="InnoFarm";


	public static String getCacheDir() {
		return getDir("cache");
	}
	public static String getDownLoadDir() {
		return getDir("download");
	}


	public static String geIconDir() {
		return getDir("icon");
	}

	private static String getDir(String cache) {
		String path;
		StringBuilder  sb=new StringBuilder();

		if(isSDAvailable()){
			String sd=Environment.getExternalStorageDirectory().getAbsolutePath();
			sb.append(sd);
			sb.append(File.separator);
			sb.append(ROOT_DIR);
			sb.append(File.separator).append(cache);
			path=sb.toString();

		}else{

			String dataPath=UIUtils.getContext().getCacheDir().getAbsolutePath();
			sb.append(dataPath);
			sb.append(File.separator).append(cache);
			path=sb.toString();
			
		}
		File file=new File(path);
		if(!file.exists()||!file.isDirectory()){
			file.mkdirs();
		}
		
		return path;
	}

	private static boolean isSDAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

}
