package com.android.ui.learing.utils;

import android.util.Log;

public class CLog {
	public static final String TAG = "lilong";
	
	public static void i(String log){
		if(log == null)return;
		Log.i(TAG, log);
	}
}
