package com.nearucenterplaza.redenvelopeassistant.ui.fragmant;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.nearucenterplaza.redenvelopeassistant.R;
import com.nearucenterplaza.redenvelopeassistant.ui.activity.HomeActivity;
import com.nearucenterplaza.redenvelopeassistant.utils.PackageUtils;
import com.nearucenterplaza.redenvelopeassistant.utils.XLog;
import com.nearucenterplaza.redenvelopeassistant.utils.XToast;
import com.nearucenterplaza.redenvelopeassistant.utils.rootbox.RootTools;
import com.nearucenterplaza.redenvelopeassistant.utils.rootbox.exceptions.RootDeniedException;
import com.nearucenterplaza.redenvelopeassistant.utils.rootbox.execution.CommandCapture;
import com.nearucenterplaza.redenvelopeassistant.utils.rootbox.execution.Shell;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AlipayFragment extends Fragment implements OnClickListener {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static AlipayFragment newInstance(int sectionNumber) {
		AlipayFragment fragment = new AlipayFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public AlipayFragment() {
	}

	TextView mOneKeyCleanTv;
	TextView mAutoCleanTv;
	TextView mSelfCleanTv;
	TextView mRootStateTv;
	TextView mOpenAlipayTv;

	private final static String ALIPAY_PACKAGENAME = "com.eg.android.AlipayGphone";

	public final static int MSG_FILE_DELETE_DELAY = 1;
	public final static int MSG_TOAST_LONG = 2;
	public final static int MSG_TOAST_SHORT = 3;

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_FILE_DELETE_DELAY:
				deleteFile((String) msg.obj);
				XToast.xtShort(getActivity(), getString(R.string.ui_alipay_clear_data_success));
				break;
			case MSG_TOAST_LONG:
				XToast.xtLong(getActivity(), (String) msg.obj);
				break;
			case MSG_TOAST_SHORT:
				XToast.xtShort(getActivity(), (String) msg.obj);
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_alipay, container, false);
		initView(rootView);
		return rootView;
	}
	

	void initView(View view) {
		// find views
		mRootStateTv = (TextView) view.findViewById(R.id.alipay_root_state_tv);
		mAutoCleanTv = (TextView) view.findViewById(R.id.alipay_auto_clean_data_tv);
		mSelfCleanTv = (TextView) view.findViewById(R.id.alipay_self_clean_data_tv);
		mOneKeyCleanTv = (TextView) view.findViewById(R.id.alipay_onekey_clean_data_tv);
		mOpenAlipayTv = (TextView) view.findViewById(R.id.alipay_openapp_tv);

		// set listeners
		mAutoCleanTv.setOnClickListener(this);
		mSelfCleanTv.setOnClickListener(this);
		mOneKeyCleanTv.setOnClickListener(this);
		mOpenAlipayTv.setOnClickListener(this);

		// set values
		boolean isRoot = RootTools.isRoot();
		mRootStateTv.setText(isRoot ? getString(R.string.ui_alipay_rooted) : getString(R.string.ui_alipay_unroot));
		mRootStateTv.setTextColor(isRoot ? Color.BLUE : Color.RED);
		if (isRoot) {
			mAutoCleanTv.setVisibility(View.VISIBLE);
			mSelfCleanTv.setVisibility(View.VISIBLE);
			mOneKeyCleanTv.setVisibility(View.VISIBLE);
		} else {
			mAutoCleanTv.setVisibility(View.GONE);
			mSelfCleanTv.setVisibility(View.GONE);
			mOneKeyCleanTv.setVisibility(View.VISIBLE);
		}
		if(PackageUtils.isAppInstalled(getActivity(), ALIPAY_PACKAGENAME)){
			mOpenAlipayTv.setVisibility(View.VISIBLE);
		}else{
			mOpenAlipayTv.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alipay_auto_clean_data_tv:
			autoClean();
			break;
		case R.id.alipay_self_clean_data_tv:
			selfClean();
			break;
		case R.id.alipay_onekey_clean_data_tv:
			oneKeyClean();
			break;
		case R.id.alipay_openapp_tv:
			PackageUtils.openApp(getActivity(), ALIPAY_PACKAGENAME);
			break;
		}
	}

	void autoClean() {
		boolean isRoot = RootTools.isRoot();
//		String path = "/data/data/" + ALIPAY_PACKAGENAME + "/shared_prefs";
		if (RootTools.isAccessGiven()) {
			Message msg = mHandler.obtainMessage(MSG_FILE_DELETE_DELAY);
			msg.what = MSG_FILE_DELETE_DELAY;
			msg.obj = ALIPAY_PACKAGENAME;
			mHandler.sendMessage(msg);
		} else {
			Toast.makeText(getActivity(), getString(R.string.ui_alipay_not_able_to_get_root), Toast.LENGTH_SHORT).show();
		}
	}

	void deleteFile(final String packageName) {
		if (packageName == null)
			return;
		// delete file
		try {
			Shell shellDeleteFile = RootTools.getShell(true);
			//"rm -rf  " + path
			CommandCapture cmdDeleteFile = new CommandCapture(0, "pm clear " + packageName) {
				@Override
				public void commandOutput(int id, String line) {
					super.commandOutput(id, line);
					XLog.i("delete file finish");
				}
			};
			shellDeleteFile.add(cmdDeleteFile);
		} catch (IOException | TimeoutException | RootDeniedException e) {
			e.printStackTrace();
			XLog.i("delete file error");
		}
	}

	void selfClean() {
		openAlipayDetails(ALIPAY_PACKAGENAME);
		Toast.makeText(getActivity(), getString(R.string.ui_alipay_clear_data_tip), Toast.LENGTH_SHORT).show();
	}

	void oneKeyClean() {
		boolean isRoot = RootTools.isRoot();
		if (isRoot) {
			autoClean();
		} else {
			selfClean();
		}
	}

	void openAlipayDetails(String pkgname) {
		Uri u = Uri.parse("package:" + pkgname);
		Intent i = new Intent();
		i.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		i.setData(u);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setComponent(new ComponentName("com.android.settings", "com.android.settings.applications.InstalledAppDetails"));
		startActivity(i);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((HomeActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}

	public static void log(String msg) {
		XLog.i("alipay", msg);
	}
}
