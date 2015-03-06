package com.nearucenterplaza.redenvelopeassistant.ui.activity;

import com.nearucenterplaza.redenvelopeassistant.R;
import com.nearucenterplaza.redenvelopeassistant.service.core.SettingHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SettingActivity extends ActionBarActivity{
	RadioGroup mModeRg;
	RadioButton mAutoModeRb;
	RadioButton mSafeModeRb;
	
	public static void actionTo(Context context){
		Intent intent=new Intent(context, SettingActivity.class);
		context.startActivity(intent);
	}
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_settings);
		initView();
	}
	
	void initView() {
		// find views
		mModeRg=(RadioGroup) findViewById(R.id.settings_re_mode_rg);
		mAutoModeRb = (RadioButton) findViewById(R.id.settings_re_auto_mode_rb);
		mSafeModeRb=(RadioButton)findViewById(R.id.settings_re_safe_mode_rb);

		// set listeners
		mModeRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.settings_re_auto_mode_rb:
					SettingHelper.setREAutoMode(mAutoModeRb.isChecked());
					SettingHelper.setRESafeMode(!mAutoModeRb.isChecked());
					break;
				case R.id.settings_re_safe_mode_rb:
					SettingHelper.setREAutoMode(!mSafeModeRb.isChecked());
					SettingHelper.setRESafeMode(mSafeModeRb.isChecked());
					break;
				default:
					break;
				}
			}
		});
		
		// set values
		setTitle("设置");
		mAutoModeRb.setChecked(SettingHelper.getREAutoMode());
		mSafeModeRb.setChecked(SettingHelper.getRESafeMode());
	}

}
