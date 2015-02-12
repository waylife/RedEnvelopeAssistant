package com.nearucenterplaza.redenvelopeassistant.utils;

import android.util.Log;


import java.util.HashSet;

/** log管理类，方便正式上线时不展示log,如果希望上线时显示log，请使用系统自带的Log类 */
public class XLog {
	/** 非线上环境会显示log */
	private static boolean showLog = true;//!HttpConstants.IS_REALEASE;
	/** 默认tag，默认值为RedEnvelopeAssistant */
	private static String defaultTag = "RedEnvelopeAssistant";
	private static final String SEPERATE = "=========";
	private static final  HashSet<String> ALLOW_TAGS=new HashSet<String>();

	static {
		//请添加小写的，过滤时不区分大小写
		ALLOW_TAGS.add("tag");
		ALLOW_TAGS.add(defaultTag);
		ALLOW_TAGS.add("alipay".toLowerCase());
		ALLOW_TAGS.add("WechatAccService".toLowerCase());
	}

	/**判断tag是否是被允许的*/
	public  static boolean isTagAllow(String tag){
		if(tag==null)
			return false;
		return  ALLOW_TAGS.contains(tag.toLowerCase());
	}



	/** 默认tag为{@link #defaultTag} */
	public static void v(String msg) {
		v(defaultTag, msg);
	}

	public static void v(String tag, String msg) {
		if (isShowLog(tag))
			Log.v(tag, msg);
	}

	/** 默认tag为{@link #defaultTag} */
	public static void d(String msg) {
		d(defaultTag, msg);
	}

	public static void d(String tag, String msg) {
		if (isShowLog(tag))
			Log.d(tag, msg);
	}


	/** 默认tag为{@link #defaultTag} */
	public static void i(String msg) {
		i(defaultTag, msg);
	}

	public static void i(String tag, String msg) {
		if (isShowLog(tag))
			Log.i(tag, msg);
	}

	/** 默认tag为{@link #defaultTag} */
	public static void w(String msg) {
		w(defaultTag, msg);
	}

	public static void w(String tag, String msg) {
		if (isShowLog(tag))
			Log.w(tag, msg);
	}

	/** 默认tag为{@link #defaultTag} */
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
