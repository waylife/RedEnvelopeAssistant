package com.nearucenterplaza.redenvelopeassistant.ui.fragmant;

import com.nearucenterplaza.redenvelopeassistant.R;
import com.nearucenterplaza.redenvelopeassistant.ui.activity.HomeActivity;
import com.nearucenterplaza.redenvelopeassistant.ui.activity.SettingActivity;
import com.nearucenterplaza.redenvelopeassistant.utils.AccessibilityServiceHelper;
import com.nearucenterplaza.redenvelopeassistant.utils.PackageUtils;
import com.nearucenterplaza.redenvelopeassistant.utils.XLog;
import com.nearucenterplaza.redenvelopeassistant.utils.XToast;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class WeChatFragment extends Fragment implements OnClickListener  {
//Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
//	startActivityForResult(intent, 0);
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static WeChatFragment newInstance(int sectionNumber) {
		WeChatFragment fragment = new WeChatFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public WeChatFragment() {
	}

	TextView mOneKeyCleanTv;
	TextView mServiceStateTv;
	TextView mOpenWechatTv;

	public  final static String WECHAT_PACKAGENAME = "com.tencent.mm";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_wechat, container, false);
		initView(rootView);
		return rootView;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		refreshLayout();
	}

	void initView(View view) {
		// find views
		mServiceStateTv = (TextView) view.findViewById(R.id.wechat_service_state_tv);
		mOneKeyCleanTv = (TextView) view.findViewById(R.id.wechat_onekey_clean_data_tv);
		mOpenWechatTv = (TextView) view.findViewById(R.id.wechat_openapp_tv);

		// set listeners
		mServiceStateTv.setOnClickListener(this);
		mOneKeyCleanTv.setOnClickListener(this);
		mOpenWechatTv.setOnClickListener(this);
		view.findViewById(R.id.wechat_setting_tv).setOnClickListener(this);

		// set values
		refreshLayout();
	}

	void refreshLayout(){
		if(mServiceStateTv==null)
			return;
		Context context=getActivity();
		if(context==null)
			return;
		boolean isRunning = AccessibilityServiceHelper.isAccessibilitySettingsOn(context);
		mServiceStateTv.setText(isRunning ? getString(R.string.ui_wechat_tv_service_on) : getString(R.string.ui_wechat_tv_service_off));
		mServiceStateTv.setTextColor(isRunning ? Color.BLUE : Color.RED);

		mOneKeyCleanTv.setText(isRunning ? getString(R.string.ui_wechat_btn_service_on_text) : getString(R.string.ui_wechat_btn_service_off_text));
		mOneKeyCleanTv.setTextColor(isRunning ? Color.RED : Color.WHITE);
		
		if(PackageUtils.isAppInstalled(getActivity(), WECHAT_PACKAGENAME)){
			mOpenWechatTv.setVisibility(View.VISIBLE);
		}else{
			mOpenWechatTv.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wechat_service_state_tv:
//			AccessibilityServiceHelper.startService(getActivity());
			break;
		case R.id.wechat_onekey_clean_data_tv:
			Context context=getActivity();
			if(context==null)
				return;
			if(AccessibilityServiceHelper.isAccessibilitySettingsOn(getActivity())){
				XToast.xtShort(context, getText(R.string.close_accessibility_service_hint));
			}else{
				XToast.xtShort(context, getText(R.string.open_accessibility_service_hint));
			}
			AccessibilityServiceHelper.startService(getActivity());
			break;
		case R.id.wechat_openapp_tv:
			PackageUtils.openApp(getActivity(), WECHAT_PACKAGENAME);
			break;
		case R.id.wechat_setting_tv:
			SettingActivity.actionTo(getActivity());
			break;
		}
	}



	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((HomeActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}

	public static void log(String msg) {
		XLog.i("wechat", msg);
	}
}
