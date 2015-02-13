package com.nearucenterplaza.redenvelopeassistant.ui;

import android.app.Application;

public class RedEnvelopeApplication extends Application {
	private static RedEnvelopeApplication mInstance;
	
	public static Application getInstance(){
		return mInstance;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		mInstance=this;
	}
	
	public void onTerminate(){
		super.onTerminate();
		mInstance=null;
	}
}
