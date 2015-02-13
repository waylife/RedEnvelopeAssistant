package com.nearucenterplaza.redenvelopeassistant.utils;

import com.nearucenterplaza.redenvelopeassistant.ui.RedEnvelopeApplication;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceUtil {

	/** 设置Key对应的boolean值 */
	public static boolean setBooleanValue(String key, boolean value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RedEnvelopeApplication.getInstance());
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * 获得Key对应的boolean值 <br/>
	 * 默认返回false<br/>
	 */
	public static boolean getBooleanValue(String key) {
		return getBooleanValue(key, false);
	}

	/**
	 * 获得Key对应的boolean值 <br/>
	 * 
	 * @param defaultValue
	 *            默认返回值
	 */
	public static boolean getBooleanValue(String key, boolean defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RedEnvelopeApplication.getInstance());
		return prefs.getBoolean(key, defaultValue);
	}
}
