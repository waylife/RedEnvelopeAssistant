package com.nearucenterplaza.redenvelopeassistant.utils;

import java.util.Iterator;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

public class PackageUtils {
	public static void openApp(Context context, String packageName) {

		PackageManager pm = context.getPackageManager();

		String realPackageName = packageName;
		List<PackageInfo> appInfos = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
		Iterator<PackageInfo> itInfo = appInfos.iterator();
		while (itInfo.hasNext()) {
			PackageInfo info = itInfo.next();
			if (info.packageName.equalsIgnoreCase(packageName)) {
				realPackageName = info.packageName;
			}
		}

		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(realPackageName);

		List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
		if (apps == null)
			return;
		Iterator<ResolveInfo> it = apps.iterator();
		ResolveInfo ri = null;
		if (it.hasNext()) {
			ri = it.next();
		} else {
			Toast.makeText(context, "Can not open application", Toast.LENGTH_SHORT).show();
		}
		if (ri != null) {
			String className = ri.activityInfo.name;

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			ComponentName cn = new ComponentName(realPackageName, className);

			intent.setComponent(cn);
			context.startActivity(intent);
		}
	}
	
	public static boolean isAppInstalled(Context context,String packageName) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (PackageManager.NameNotFoundException e) {
			packageInfo = null;
//			e.printStackTrace();
		}
		if (packageInfo == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean couldOpen(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(packageName);
		return couldOpen(pm, context, resolveIntent);
	}


	private static boolean couldOpen(PackageManager pm, Context context, Intent resolveIntent) {
		List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
		if (apps == null || apps.size() < 1) {
			return false;
		}
		Iterator<ResolveInfo> it = apps.iterator();
		if (it.hasNext()) {
			return true;
		} else {
			return false;
		}
	}
}
