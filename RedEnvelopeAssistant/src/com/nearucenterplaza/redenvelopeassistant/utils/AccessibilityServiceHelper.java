package com.nearucenterplaza.redenvelopeassistant.utils;

import com.nearucenterplaza.redenvelopeassistant.service.WechatAccService;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class AccessibilityServiceHelper {

	public static void checkWithStartService(Context context) {
		if (!isAccessibilitySettingsOn(context.getApplicationContext())) {
			context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
			Toast.makeText(context, "请选择打开【红包助手】服务，否则将无法使用本功能", Toast.LENGTH_SHORT).show();
		}
	}

	public static void startService(Context context) {
		context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
		Toast.makeText(context, "请选择打开【红包助手】服务，否则将无法使用本功能", Toast.LENGTH_SHORT).show();
	}

	// To check if service is enabled
	public static boolean isAccessibilitySettingsOn(Context context) {
		int accessibilityEnabled = 0;
		final String service = context.getPackageName() + "/" + WechatAccService.class.getName();
		boolean accessibilityFound = false;
		try {
			accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
			// Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
		} catch (SettingNotFoundException e) {
			// Log.e(TAG,
			// "Error finding setting, default accessibility to not found: "
			// + e.getMessage());
		}
		TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

		if (accessibilityEnabled == 1) {
			// Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
			String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
			if (settingValue != null) {
				TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
				splitter.setString(settingValue);
				while (splitter.hasNext()) {
					String accessabilityService = splitter.next();

					// Log.v(TAG, "-------------- > accessabilityService :: " +
					// accessabilityService);
					if (accessabilityService.equalsIgnoreCase(service)) {
						// Log.v(TAG,
						// "We've found the correct setting - accessibility is switched on!");
						return true;
					}
				}
			}
		} else {
			// Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
		}

		return accessibilityFound;
	}
}
