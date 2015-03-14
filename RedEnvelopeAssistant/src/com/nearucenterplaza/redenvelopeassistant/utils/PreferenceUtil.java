package com.nearucenterplaza.redenvelopeassistant.utils;

import com.nearucenterplaza.redenvelopeassistant.ui.RedEnvelopeApplication;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceUtil {


	public static boolean setBooleanValue(String key, boolean value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RedEnvelopeApplication.getInstance());
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}


	public static boolean getBooleanValue(String key) {
		return getBooleanValue(key, false);
	}


	public static boolean getBooleanValue(String key, boolean defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RedEnvelopeApplication.getInstance());
		return prefs.getBoolean(key, defaultValue);
	}
}
