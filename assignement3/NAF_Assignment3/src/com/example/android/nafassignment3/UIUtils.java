package com.example.android.nafassignment3;

import android.os.Handler;
import android.os.Looper;

public class UIUtils {
	
	private static final Handler uiHandler = new Handler(Looper.getMainLooper());
	
	
	public static void runOnUiThread(Runnable r) {
		uiHandler.post(r);
	}
}