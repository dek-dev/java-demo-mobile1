package com.oppo.marketdemo.service;

import android.content.Context;
import android.content.Intent;

/**
 * 创建唯一Service的Intent对象，用于启动和停止
 *
 */
public class ServiceManager {
	private static volatile Intent ScreenServiceIntent = null;
	public static Intent getInstance(Context context) {
		Intent inst = ScreenServiceIntent;
	    if (inst == null) {
	        synchronized (Intent.class) {
	            inst = ScreenServiceIntent;
	            if (inst == null) {
	                inst = new Intent(context, ScreenService.class);
	                inst.setAction(Intent.ACTION_SCREEN_OFF);
	                ScreenServiceIntent = inst;
	            }
	        }
	    }
	    return inst; 
	}
}
