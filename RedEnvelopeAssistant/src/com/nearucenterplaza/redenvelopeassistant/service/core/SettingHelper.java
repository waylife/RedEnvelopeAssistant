package com.nearucenterplaza.redenvelopeassistant.service.core;

import com.nearucenterplaza.redenvelopeassistant.utils.PreferenceUtil;

public class SettingHelper {
	private static final String RE_AUTO_MODE="re_mode_auto";
	private static final String RE_SAFE_MODE="re_mode_safe";
	
	public static boolean setREAutoMode(boolean value){
		return PreferenceUtil.setBooleanValue(RE_AUTO_MODE, value);
	}
	
	public static boolean getREAutoMode(){
		return PreferenceUtil.getBooleanValue(RE_AUTO_MODE, true);
	}
	
	public static boolean setRESafeMode(boolean value){
		return PreferenceUtil.setBooleanValue(RE_SAFE_MODE, value);
	}
	
	public static boolean getRESafeMode(){
		return PreferenceUtil.getBooleanValue(RE_SAFE_MODE, false);
	}
}
