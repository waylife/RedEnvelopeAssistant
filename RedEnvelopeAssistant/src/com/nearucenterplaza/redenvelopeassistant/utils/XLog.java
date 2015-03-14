package com.nearucenterplaza.redenvelopeassistant.utils;

import android.util.Log;


import java.util.HashSet;

public class XLog {
	/** switch for log*/
	private static boolean showLog = true;
	/** the defaule value is RedEnvelopeAssistant */
	private static String defaultTag = "RedEnvelopeAssistant";
	private static final String SEPERATE = "=========";
	private static final  HashSet<String> ALLOW_TAGS=new HashSet<String>();

	static {
		ALLOW_TAGS.add("tag");
		ALLOW_TAGS.add(defaultTag);
		ALLOW_TAGS.add("alipay".toLowerCase());
		ALLOW_TAGS.add("WechatAccService".toLowerCase());
		ALLOW_TAGS.add("Xnotification".toLowerCase());
	}

	public  static boolean isTagAllow(String tag){
		if(tag==null)
			return false;
		return  ALLOW_TAGS.contains(tag.toLowerCase());
	}


	public static void v(String msg) {
		v(defaultTag, msg);
	}

	public static void v(String tag, String msg) {
		if (isShowLog(tag))
			Log.v(tag, msg);
	}

	public static void d(String msg) {
		d(defaultTag, msg);
	}

	public static void d(String tag, String msg) {
		if (isShowLog(tag))
			Log.d(tag, msg);
	}


	public static void i(String msg) {
		i(defaultTag, msg);
	}

	public static void i(String tag, String msg) {
		if (isShowLog(tag))
			Log.i(tag, msg);
	}

	public static void w(String msg) {
		w(defaultTag, msg);
	}

	public static void w(String tag, String msg) {
		if (isShowLog(tag))
			Log.w(tag, msg);
	}

	public static void e(String msg) {
		e(defaultTag, msg);
	}

	public static void e(String tag, String msg) {
		if (isShowLog(tag))
			Log.e(tag, msg);
	}

	private static boolean  isShowLog(String tag){
		return showLog&&isTagAllow(tag);
	}

	public static void e(Exception e) {
		if (showLog)
			e.printStackTrace();
	}

	public static void e(String tag, Exception e) {
		if (showLog) {
			Log.e(tag, SEPERATE + e.getClass().getSimpleName() + SEPERATE, e);
		}
	}

}
