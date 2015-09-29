package com.nearucenterplaza.redenvelopeassistant.service;

import com.nearucenterplaza.redenvelopeassistant.R;
import com.nearucenterplaza.redenvelopeassistant.ui.RedEnvelopeApplication;
import com.nearucenterplaza.redenvelopeassistant.ui.fragmant.WeChatFragment;
import com.nearucenterplaza.redenvelopeassistant.service.core.Notifier;
import com.nearucenterplaza.redenvelopeassistant.service.core.RedEnvelopeHelper;
import com.nearucenterplaza.redenvelopeassistant.service.core.SettingHelper;
import com.nearucenterplaza.redenvelopeassistant.service.core.SoundHelper;
import com.nearucenterplaza.redenvelopeassistant.utils.ActivityHelper;
import com.nearucenterplaza.redenvelopeassistant.utils.XLog;

import android.R.anim;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class WechatAccService extends AccessibilityService {

	public static void log(String message) {
		XLog.e("WechatAccService", message);
	} 
	
	/**
	 * {@inheritDoc}
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public void onServiceConnected() {
		AccessibilityServiceInfo accessibilityServiceInfo = getServiceInfo();
		if (accessibilityServiceInfo == null)
			accessibilityServiceInfo = new AccessibilityServiceInfo();
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
		Notifier.getInstance().notify(getString(R.string.app_name), getString(R.string.wechat_acc_service_start_notification), getString(R.string.wechat_acc_service_start_notification),
				Notifier.TYPE_WECHAT_SERVICE_RUNNING, false);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if (event == null)
			return;
		if(SettingHelper.getREAutoMode()){
			handleNotificationChange(event);
		}
		AccessibilityNodeInfo nodeInfo = event.getSource();
		if (nodeInfo == null) {
			return;
		}

		AccessibilityNodeInfo rowNode = nodeInfo;// we can also use getRootInActiveWindow() instead;
		if (rowNode == null) {
			log( "noteInfo is　null");
			return;
		}

		// String currentActivityName =
		// ActivityHelper.getTopActivityName(RedEnvelopeApplication.getInstance());
		if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			CharSequence currentActivityName = event.getClassName();
			if ("com.tencent.mm.ui.LauncherUI".equals(currentActivityName) || "com.tencent.mm.ui.chatting.ChattingUI".equals(currentActivityName)) {
				// 聊天以及主页 chat page and the main page
				log( "Chat page");
				if (SettingHelper.getREAutoMode()) {
					handleChatPage(rowNode);
				}
			} else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI"
					.equals(currentActivityName)) {
				//打开红包主页 red envelope open page
				log("LuckyMoneyReceiveUI page");
				if (SettingHelper.getREAutoMode()
						|| SettingHelper.getRESafeMode()){
					handleLuckyMoneyReceivePage(rowNode);
				}
			} else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI"
					.equals(currentActivityName)) {
				// 红包详情主页 red envelope detail page
				if (SettingHelper.getREAutoMode()){
					handleLuckyMoneyDetailPage(rowNode);
				}
			} else {
				log( currentActivityName + " page");
			}
		}
	}

	/** handle notification notice */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void handleNotificationChange(AccessibilityEvent event) {
		log( "eventtype:" + event.getEventType());
		if (event == null)
			return;
		
		if (!(event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)) {
			return;
		}
		if (event.getParcelableData() instanceof Notification) {
			Notification notification = (Notification) event
					.getParcelableData();
			if (notification.tickerText != null
					&& notification.tickerText.toString().contains(getString(R.string.wechat_acc_service_red_envelope_notification_identification))) {
				log("来红包啦 get red envelope message");
				if(SettingHelper.isRESound()){
					((RedEnvelopeApplication) RedEnvelopeApplication
							.getInstance()).getSoundHelper()
							.playSoundRedEnvelopeComing();
				}
				RedEnvelopeHelper.openNotification(event);
			}
		}
	}


	public void handleChatPage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		if(android.os.Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
			 AccessibilityNodeInfo tempNode=RedEnvelopeHelper.getLastWechatRedEnvelopeNodeById(node);
			 if(tempNode!=null){
				 tempNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				 tempNode.recycle();
			 }
		}else if(android.os.Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
			 AccessibilityNodeInfo tempNode=RedEnvelopeHelper.getLastWechatRedEnvelopeNodeByText(node,this);
			 if(tempNode!=null){
				 tempNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				 tempNode.recycle();
			 }
		}
	}

	public void handleLuckyMoneyReceivePage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		AccessibilityNodeInfo nodeDetail = RedEnvelopeHelper
				.getWechatRedEnvelopeOpenDetailNode(node);
		if (nodeDetail != null) {// the red envelope already opened
									// 红包已经被打开
			if (SettingHelper.getREAutoMode())
				ActivityHelper.goHome(this);
		} else {
			AccessibilityNodeInfo nodeOpen = RedEnvelopeHelper
					.getWechatRedEnvelopeOpenNode(node);
			if (nodeOpen != null) {
				nodeOpen.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				nodeOpen.recycle();
			} else {// this page is loading red envelope data, no action

			}
		}
	}

	public void handleLuckyMoneyDetailPage(AccessibilityNodeInfo node) {
		if (node == null)
			return;
		ActivityHelper.goHome(this);
	}

	

	@Override
	public void onInterrupt() {
		log("onInterrupt");
	}

	public void onDestroy() {
		super.onDestroy();
		Notifier.getInstance().cancelByType(
				Notifier.TYPE_WECHAT_SERVICE_RUNNING);
	}

}
