package com.nearucenterplaza.redenvelopeassistant.service.core;

import com.nearucenterplaza.redenvelopeassistant.utils.PreferenceUtil;

public class SettingHelper {
	private static final String AUTO_CLICK_ON_CHAT="auto_click_on_chat";
	private static final String AUTO_BACK_WHEN_GET＿LUCKY_MONEY="auto_back_when_get_lucky_money";
	
	public static boolean setAutoClickOnChatPage(boolean value){
		return PreferenceUtil.setBooleanValue(AUTO_CLICK_ON_CHAT, value);
	}
	
	public static boolean getAutoClickOnChatPage(){
		return PreferenceUtil.getBooleanValue(AUTO_CLICK_ON_CHAT, false);
	}
	
	public static boolean setAutoBackWhenGetLuckMoney(boolean value){
		return PreferenceUtil.setBooleanValue(AUTO_BACK_WHEN_GET＿LUCKY_MONEY, value);
	}
	
	public static boolean getAutoBackWhenGetLuckMoney(){
		return PreferenceUtil.getBooleanValue(AUTO_BACK_WHEN_GET＿LUCKY_MONEY, false);
	}
}
