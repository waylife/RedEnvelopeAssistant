package com.nearucenterplaza.redenvelopeassistant.service.core;

import java.util.List;

import com.nearucenterplaza.redenvelopeassistant.utils.XLog;

import android.R.integer;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

public class RedEnvelopeHelper {
	
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static AccessibilityNodeInfo getWechatRedEnvelopeOpenNode(AccessibilityNodeInfo info) {
		if (info == null)
			return null;
		List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/amt");
		AccessibilityNodeInfo tempNode=null;
		for(int i=0;i<list.size();i++){
			tempNode=list.get(i);
			XLog.e("WechatAccService", "e2ee"+tempNode.isVisibleToUser()+"-"+tempNode.isEnabled());
			if ("android.widget.Button".equals(tempNode.getClassName())&&tempNode.isVisibleToUser()){
				return tempNode;
			}
		}
		return null;
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static AccessibilityNodeInfo getWechatRedEnvelopeOpenDetailNode(AccessibilityNodeInfo info) {
		if (info == null)
			return null;
		List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/amk");
		AccessibilityNodeInfo tempNode=null;
		for(int i=0;i<list.size();i++){
			tempNode=list.get(i);
			XLog.e("WechatAccService", "eee"+tempNode.isVisibleToUser()+"-"+tempNode.isEnabled());
			if ("android.widget.TextView".equals(tempNode.getClassName())&&tempNode.isVisibleToUser()){
				return tempNode;
			}
		}
		return null;
	}
	
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static boolean isWechatRedEnvelopeOpenNode(AccessibilityNodeInfo info) {
		if (info == null)
			return false;
		String residName = info.getViewIdResourceName();
		if ("com.tencent.mm:id/amt".equals(residName)) {
			if ("android.widget.Button".equals(info.getClassName())) {
				return true;
				/*AccessibilityNodeInfo infoChild22 = info.getChild(0);
				XLog.e(TAG, "red main layout2 "+infoChild22.getChildCount());
				if (infoChild22 != null && infoChild22.getChildCount() == 2 && "android.widget.RelativeLayout".equals(infoChild22.getClassName())) {
					XLog.e(TAG, "red main layout3");
					AccessibilityNodeInfo infoChild30 = infoChild22.getChild(0);
					if (infoChild30 != null && "微信红包".equals(infoChild30.getText() == null ? "" : infoChild30.getText().toString())) {
						XLog.e(TAG, "red main layout4");
						return true;

					}
				}*/
			}
		}
		return false;
	}
	
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static  boolean isWechatRedEnvelopeNode(AccessibilityNodeInfo info) {
		if (info == null)
			return false;
		String residName = info.getViewIdResourceName();
		if ("com.tencent.mm:id/s_".equals(residName)) {
			if ("android.widget.LinearLayout".equals(info.getClassName())) {// 是3,与层次图显示的不一致
				return true;
				/*AccessibilityNodeInfo infoChild22 = info.getChild(0);
				XLog.e(TAG, "red main layout2 "+infoChild22.getChildCount());
				if (infoChild22 != null && infoChild22.getChildCount() == 2 && "android.widget.RelativeLayout".equals(infoChild22.getClassName())) {
					XLog.e(TAG, "red main layout3");
					AccessibilityNodeInfo infoChild30 = infoChild22.getChild(0);
					if (infoChild30 != null && "微信红包".equals(infoChild30.getText() == null ? "" : infoChild30.getText().toString())) {
						XLog.e(TAG, "red main layout4");
						return true;

					}
				}*/
			}
		}
		return false;
	}
}
