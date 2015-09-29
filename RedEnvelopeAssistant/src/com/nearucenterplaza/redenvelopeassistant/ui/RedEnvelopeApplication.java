package com.nearucenterplaza.redenvelopeassistant.ui;

import com.nearucenterplaza.redenvelopeassistant.service.core.SoundHelper;

import android.app.Application;

public class RedEnvelopeApplication extends Application {
	private static RedEnvelopeApplication mInstance;
	private SoundHelper soundHelper;
	
	public static Application getInstance(){
		return mInstance;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		mInstance=this;
		soundHelper = new SoundHelper(getApplicationContext());
	}
	
	public void onTerminate(){
		super.onTerminate();
		mInstance=null;
	}
	
	public SoundHelper getSoundHelper(){
		return soundHelper;
	}
}
