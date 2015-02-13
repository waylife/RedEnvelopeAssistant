package com.nearucenterplaza.redenvelopeassistant.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {
	/** get the current version of current application */
	public static String getVersionName( Context context) {
		String versionName = "";
		String pkName = context.getPackageName();
		try {
			versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
}
