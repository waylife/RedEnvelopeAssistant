package com.nearucenterplaza.redenvelopeassistant.service;



import com.nearucenterplaza.redenvelopeassistant.utils.XLog;

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
	private static final String TAG = "WechatAccService";
	String[] PACKAGES = { "com.android.settings" };
	 /** An intent for launching the system settings. */
    private static final Intent sSettingsIntent =
        new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onServiceConnected() {
    	XLog.i(TAG, "config success!");
		AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
		// accessibilityServiceInfo.packageNames = PACKAGES;
		accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
		accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
		accessibilityServiceInfo.notificationTimeout = 1000;
		setServiceInfo(accessibilityServiceInfo);
    }
    
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		//#1start
		 // get the source node of the event
		/*
        AccessibilityNodeInfo nodeInfo = event.getSource();

        // Use the event and node information to determine
        // what action to take

        // take action on behalf of the user
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//        nodeInfo.performAction(AccessibilityNodeInfo.ACTION)

        // recycle the nodeInfo object
        nodeInfo.recycle();
        */
        AccessibilityNodeInfo rowNode = getRootInActiveWindow();
        if (rowNode == null) {
        	XLog.i(TAG, "noteInfo is　null");
        	return;
        } else {
        	recycle(rowNode);
        }
        
        //#1end
        
     // TODO Auto-generated method stub
     		int eventType = event.getEventType();
     		String eventText = "";
     		XLog.i(TAG, "==============Start====================");
     		switch (eventType) {
     		case AccessibilityEvent.TYPE_VIEW_CLICKED:
     			eventText = "TYPE_VIEW_CLICKED";
     			break;
     		case AccessibilityEvent.TYPE_VIEW_FOCUSED:
     			eventText = "TYPE_VIEW_FOCUSED";
     			break;
     		case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
     			eventText = "TYPE_VIEW_LONG_CLICKED";
     			break;
     		case AccessibilityEvent.TYPE_VIEW_SELECTED:
     			eventText = "TYPE_VIEW_SELECTED";
     			break;
     		case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
     			eventText = "TYPE_VIEW_TEXT_CHANGED";
     			break;
     		case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
     			eventText = "TYPE_WINDOW_STATE_CHANGED";
     			break;
     		case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
     			eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
     			break;
     		case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
     			eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
     			break;
     		case AccessibilityEvent.TYPE_ANNOUNCEMENT:
     			eventText = "TYPE_ANNOUNCEMENT";
     			break;
     		case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
     			eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
     			break;
     		case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
     			eventText = "TYPE_VIEW_HOVER_ENTER";
     			break;
     		case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
     			eventText = "TYPE_VIEW_HOVER_EXIT";
     			break;
     		case AccessibilityEvent.TYPE_VIEW_SCROLLED:
     			eventText = "TYPE_VIEW_SCROLLED";
     			break;
     		case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
     			eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
     			break;
     		case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
     			eventText = "TYPE_WINDOW_CONTENT_CHANGED";
     			break;
     		}
     		eventText = eventText + ":" + eventType;
     		XLog.i(TAG, eventText);
     		XLog.i(TAG, "=============END=====================");
	}

	@Override
	public void onInterrupt() {
			
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void recycle(AccessibilityNodeInfo info) {
		if (info.getChildCount() == 0) {
			XLog.i(TAG, "child widget----------------------------" + info.getClassName());
			XLog.i(TAG, "showDialog:" + info.canOpenPopup());
			XLog.e(TAG, "Text:" + info.getText());
			XLog.i(TAG, "windowId:" + info.getWindowId());
			if("Clear data".equals(info.getText()==null?"":info.getText().toString().trim())){
				XLog.e(TAG, info.getViewIdResourceName()==null?"null":"not null");
//				info.performAction(GLOBAL_ACTION_BACK);
				info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
			}
				//com.android.settings:id/clean_user_data
		} else {
			for (int i = 0; i < info.getChildCount(); i++) {
				if(info.getChild(i)!=null){
					recycle(info.getChild(i));
				}
			}
		}
	}

}
