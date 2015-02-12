package com.nearucenterplaza.redenvelopeassistant.service;

import com.nearucenterplaza.redenvelopeassistant.fragment.RedEnvelopeApplication;
import com.nearucenterplaza.redenvelopeassistant.fragment.WeChatFragment;
import com.nearucenterplaza.redenvelopeassistant.service.core.RedEnvelopeHelper;
import com.nearucenterplaza.redenvelopeassistant.utils.ActivityHelper;
import com.nearucenterplaza.redenvelopeassistant.utils.XLog;

import android.R.integer;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class WechatAccService extends AccessibilityService {
	// 0:unkown, 1:chat page, 2:open red envelope, 3, red envelope details
	// private final int state=0;
	private static final String TAG = "WechatAccService";
	String[] PACKAGES = { "com.android.settings" };
	boolean isOpened = false;

	private long mLastGlobalBackTime = 0;
	private long GLOBAL_BACK_TIME = 500;

	
	/**
	 * {@inheritDoc}
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onServiceConnected() {
		XLog.i(TAG, "config success!");
		AccessibilityServiceInfo accessibilityServiceInfo = getServiceInfo();
		if (accessibilityServiceInfo == null)
			accessibilityServiceInfo = new AccessibilityServiceInfo();
		// accessibilityServiceInfo.packageNames = PACKAGES;
		accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
		accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
		accessibilityServiceInfo.packageNames = new String[] { WeChatFragment.WECHAT_PACKAGENAME };
		accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
		accessibilityServiceInfo.notificationTimeout = 10;
		setServiceInfo(accessibilityServiceInfo);
		// 4.0之后可通过xml进行配置,以下加入到Service里面
		/*
		 * <meta-data android:name="android.accessibilityservice"
		 * android:resource="@xml/accessibility" />
		 */
	}

	private int inner = 0;
	private int outer = 0;

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		AccessibilityNodeInfo nodeInfo = event.getSource();
		// 一直刷新，根据手机刷新频率
		if (nodeInfo == null) {
			return;
		} else {// 只有视图重绘才会刷新
			int eventType = event.getEventType();
			// XLog.i(TAG, "child widget:" + nodeInfo.getClassName() +
			// " showDialog:" + nodeInfo.canOpenPopup() + " text:" +
			// nodeInfo.getText() + " windowId:" + nodeInfo.getWindowId());
			// XLog.i(TAG, "eventtype:" + eventType + "-action:" +
			// event.getAction() + "resource name:" +
			// nodeInfo.getViewIdResourceName());
			nodeInfo.recycle();
			inner++;
		}

		AccessibilityNodeInfo rowNode = getRootInActiveWindow();
		if (rowNode == null) {
			XLog.i(TAG, "noteInfo is　null");
			return;
		}

		outer++;

		String currentActivityName = ActivityHelper.getTopActivityName(RedEnvelopeApplication.getInstance());
		if ("com.tencent.mm.ui.LauncherUI".equals(currentActivityName)) {// 聊天以及主页
			// com.tencent.mm.ui.chatting.ChattingUI，聊天
			// com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI 红包
			XLog.e(TAG, "Chat page");
			handleChatPage(rowNode);
		} else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI".equals(currentActivityName)) {
			XLog.e(TAG, "LuckyMoneyReceiveUI page");
			handleLuckyMoneyReceivePage(rowNode);
		} else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(currentActivityName)) {// lucky
																											// money
																											// details

			XLog.e(TAG, "LuckyMoneyDetailPage page" + inner + "-" + outer);
			handleLuckyMoneyDetailPage(rowNode);
		} else {
			XLog.e(TAG, currentActivityName + " page");
		}

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void peformGlobalBack() {
		if (System.currentTimeMillis() - mLastGlobalBackTime >= GLOBAL_BACK_TIME) {
			performGlobalAction(GLOBAL_ACTION_BACK);
			mLastGlobalBackTime=System.currentTimeMillis();
		} else {
			// do nothing
		}
	}

	public void handleChatPage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		recycleChatPage(node);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void handleLuckyMoneyReceivePage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		AccessibilityNodeInfo nodeDetail = RedEnvelopeHelper.getWechatRedEnvelopeOpenDetailNode(node);
		if(nodeDetail!=null){//已经打开
			peformGlobalBack();
		}else{
			AccessibilityNodeInfo nodeOpen = RedEnvelopeHelper.getWechatRedEnvelopeOpenNode(node);
			if (nodeOpen != null) {
				nodeOpen.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				nodeOpen.recycle();
			} else {//loading data
				
			}	
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void handleLuckyMoneyDetailPage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		peformGlobalBack();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public boolean recycleChatPage(AccessibilityNodeInfo info) {
		if (isOpened)
			return false;
		if (info.getChildCount() == 0) {
			// XLog.i(TAG, "child widget:" + info.getClassName() +
			// " showDialog:" + info.canOpenPopup() + " text:" + info.getText()
			// + " windowId:" + info.getWindowId());
			info.recycle();
			return false;
		} else {
			if (RedEnvelopeHelper.isWechatRedEnvelopeNode(info)) {
				info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				isOpened = true;
				info.recycle();
				return true;
			}
			for (int i = info.getChildCount() - 1; i > 0; i--) {
				if (info.getChild(i) != null) {
					boolean returnValue = recycleChatPage(info.getChild(i));
					if (returnValue) {
						info.recycle();
						return true;
					} else
						continue;
				}
			}
		}
		info.recycle();
		return false;
	}

	@Override
	public void onInterrupt() {

	}

}
