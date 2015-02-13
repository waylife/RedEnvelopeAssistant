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

public class SettingActivity extends ActionBarActivity implements android.view.View.OnClickListener{
	CheckBox mAutoClickChatCb;
	CheckBox mAutoBackWhenGetLuckMoneyCb;
	
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
		mAutoClickChatCb = (CheckBox) findViewById(R.id.settings_auto_click_on_chat_cb);
		mAutoBackWhenGetLuckMoneyCb=(CheckBox)findViewById(R.id.settings_auto_back_when_get_lucky_money_cb);

		// set listeners
		mAutoClickChatCb.setOnClickListener(this);
		mAutoBackWhenGetLuckMoneyCb.setOnClickListener(this);
		
		// set values
		setTitle("设置");
		mAutoClickChatCb.setChecked(SettingHelper.getAutoClickOnChatPage());
		mAutoBackWhenGetLuckMoneyCb.setChecked(SettingHelper.getAutoBackWhenGetLuckMoney());
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settings_auto_click_on_chat_cb:
			SettingHelper.setAutoClickOnChatPage(mAutoClickChatCb.isChecked());
			break;
		case R.id.settings_auto_back_when_get_lucky_money_cb:
			SettingHelper.setAutoBackWhenGetLuckMoney(mAutoBackWhenGetLuckMoneyCb.isChecked());
			break;
		}
	}
}
