package com.nearucenterplaza.redenvelopeassistant.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import com.nearucenterplaza.redenvelopeassistant.ui.activity.HomeActivity;

/**
 * Created by wangyun on 2015/1/21.
 */
public class ActivityHelper {
	
	

	/**
	 * 慎重使用此函数  be cautious to use this<br/>
	 * GET_TASK was deprecated in API level 21. No longer enforced
	 */
	public static String getTopActivityName(Context context) {
		try {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
			if (!tasks.isEmpty()) {
				ComponentName topActivity = tasks.get(0).topActivity;
				return topActivity.getClassName();
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 慎重使用此函数 be cautious to use this<br/>
	 * GET_TASK was deprecated in API level 21. No longer enforced
	 */
	public static boolean isApplicationBroughtToBackground(final Context context) {
		try {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
			if (!tasks.isEmpty()) {
				ComponentName topActivity = tasks.get(0).topActivity;
				if (!topActivity.getPackageName().equals(context.getPackageName())) {
					return true;
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否在前台运行
	 * 慎重使用此函数 be cautious to use this<br/>
	 * GET_TASK was deprecated in API level 21. No longer enforced
	 * @return
	 */
	public static  boolean isAppOnForeground(Context context) {
		return !isApplicationBroughtToBackground(context);
	}
	
	public static void goHome(Context context){
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
	}
	
	public static void goMainActivity(Context context){
		Intent intent = new Intent();
		intent.setClassName("com.nearucenterplaza.redenvelopeassistant", "com.nearucenterplaza.redenvelopeassistant.ui.activity.HomeActivity");
		context.startActivity(intent);
	}

}
