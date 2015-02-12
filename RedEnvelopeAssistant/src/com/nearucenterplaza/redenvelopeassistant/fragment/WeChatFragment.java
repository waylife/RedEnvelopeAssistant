package com.nearucenterplaza.redenvelopeassistant.fragment;

import com.nearucenterplaza.redenvelopeassistant.HomeActivity;
import com.nearucenterplaza.redenvelopeassistant.R;
import com.nearucenterplaza.redenvelopeassistant.utils.AccessibilityServiceHelper;
import com.nearucenterplaza.redenvelopeassistant.utils.PackageUtils;
import com.nearucenterplaza.redenvelopeassistant.utils.XLog;
import android.app.Activity;
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

		// set values
		refreshLayout();
	}

	void refreshLayout(){
		if(mServiceStateTv==null)
			return;
		boolean isRunning = AccessibilityServiceHelper.isAccessibilitySettingsOn(getActivity());
		mServiceStateTv.setText(isRunning ? "服务已启动" : "服务未启动");
		mServiceStateTv.setTextColor(isRunning ? Color.BLUE : Color.RED);

		mOneKeyCleanTv.setText(isRunning ? "停止服务" : "启动服务");
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
			AccessibilityServiceHelper.startService(getActivity());
			break;
		case R.id.wechat_openapp_tv:
			PackageUtils.openApp(getActivity(), WECHAT_PACKAGENAME);
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
