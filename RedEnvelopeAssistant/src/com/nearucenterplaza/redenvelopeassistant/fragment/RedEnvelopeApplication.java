package com.nearucenterplaza.redenvelopeassistant.fragment;

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
