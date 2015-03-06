package com.nearucenterplaza.redenvelopeassistant.service;

import java.util.Iterator;
import java.util.List;

import com.nearucenterplaza.redenvelopeassistant.ui.RedEnvelopeApplication;
import com.nearucenterplaza.redenvelopeassistant.ui.fragmant.WeChatFragment;
import com.nearucenterplaza.redenvelopeassistant.service.core.Notifier;
import com.nearucenterplaza.redenvelopeassistant.service.core.RedEnvelopeHelper;
import com.nearucenterplaza.redenvelopeassistant.service.core.SettingHelper;
import com.nearucenterplaza.redenvelopeassistant.service.core.ViewHelper;
import com.nearucenterplaza.redenvelopeassistant.utils.ActivityHelper;
import com.nearucenterplaza.redenvelopeassistant.utils.XLog;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class WechatAccService extends AccessibilityService {
	// 0:unkown, 1:chat page, 2:open red envelope, 3, red envelope details
	// private final int state=0;
	private static final String TAG = "WechatAccService";

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
		// accessibilityServiceInfo.packageNames = new String[] {
		// WeChatFragment.WECHAT_PACKAGENAME };
		accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
		accessibilityServiceInfo.notificationTimeout = 10;
		setServiceInfo(accessibilityServiceInfo);
		// 4.0之后可通过xml进行配置,以下加入到Service里面
		/*
		 * <meta-data android:name="android.accessibilityservice"
		 * android:resource="@xml/accessibility" />
		 */
		Notifier.getInstance().notify("红包助手", "微信红包服务已启动", "微信红包服务已启动",
				Notifier.TYPE_WECHAT_SERVICE_RUNNING, false);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if (event == null)
			return;
		handleNotificationChange(event);
		AccessibilityNodeInfo nodeInfo = event.getSource();
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
		}

		AccessibilityNodeInfo rowNode = getRootInActiveWindow();
		if (rowNode == null) {
			XLog.i(TAG, "noteInfo is　null");
			return;
		}

		// String currentActivityName =
		// ActivityHelper.getTopActivityName(RedEnvelopeApplication.getInstance());
		if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			CharSequence currentActivityName = event.getClassName();
			if ("com.tencent.mm.ui.LauncherUI".equals(currentActivityName)) {// 聊天以及主页
				XLog.e(TAG, "Chat page");
				if (SettingHelper.getREAutoMode()) {
					handleChatPage(rowNode);
				}
			} else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI".equals(currentActivityName)) {
				XLog.e(TAG, "LuckyMoneyReceiveUI page");
				if(SettingHelper.getREAutoMode()||SettingHelper.getRESafeMode())
					handleLuckyMoneyReceivePage(rowNode);
			} else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI"
					.equals(currentActivityName)) {// lucky
													// money
													// details
				if(SettingHelper.getREAutoMode())
					handleLuckyMoneyDetailPage(rowNode);
				
			} else {
				XLog.e(TAG, currentActivityName + " page");
			}
		}
	}

	public void log(String message) {
		XLog.e("Xnotification", message);
	}

	/** handle notification notice */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void handleNotificationChange(AccessibilityEvent event) {
		XLog.e("Xnotification", "eventtype:" + event.getEventType());
		if (event == null)
			return;
		// 通知栏服务
		if (!(event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)) {
			return;
		}
		if (event.getParcelableData() instanceof Notification) {
			Notification notification = (Notification) event
					.getParcelableData();
			if (notification.tickerText != null
					&& notification.tickerText.toString().contains(": [微信红包]")) {
				log("来红包啦");
				RedEnvelopeHelper.openNotification(event);
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void peformGlobalBack() {
		if (System.currentTimeMillis() - mLastGlobalBackTime >= GLOBAL_BACK_TIME) {
			performGlobalAction(GLOBAL_ACTION_BACK);
			mLastGlobalBackTime = System.currentTimeMillis();
		} else {
			// do nothing
		}
	}

	public void handleChatPage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		recycleChatPage(node);
//		AccessibilityNodeInfo tempNode=RedEnvelopeHelper.getLatesWechatRedEnvelopeNode(node);
//		if(tempNode!=null){
//			tempNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//			tempNode.recycle();
//		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void handleLuckyMoneyReceivePage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		AccessibilityNodeInfo nodeDetail = RedEnvelopeHelper
				.getWechatRedEnvelopeOpenDetailNode(node);
		if (nodeDetail != null) {// 红包已经被打开
//			peformGlobalBack();
			ActivityHelper.goHome(this);
		} else {
			AccessibilityNodeInfo nodeOpen = RedEnvelopeHelper
					.getWechatRedEnvelopeOpenNode(node);
			if (nodeOpen != null) {
				nodeOpen.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				nodeOpen.recycle();
			} else {// loading data
				
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void handleLuckyMoneyDetailPage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		ActivityHelper.goHome(this);
		//peformGlobalBack();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public boolean recycleChatPage(AccessibilityNodeInfo info) {
		if (info.getChildCount() == 0) {
			// XLog.i(TAG, "child widget:" + info.getClassName() +
			// " showDialog:" + info.canOpenPopup() + " text:" + info.getText()
			// + " windowId:" + info.getWindowId());
			info.recycle();
			return false;
		} else {
			if (RedEnvelopeHelper.isWechatRedEnvelopeNode(info)) {
				info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
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

	public void onDestroy() {
		super.onDestroy();
		Notifier.getInstance().cancelByType(
				Notifier.TYPE_WECHAT_SERVICE_RUNNING);
	}

}
