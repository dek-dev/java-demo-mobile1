package com.oppo.marketdemo.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.oppo.marketdemo.broadcast.ScreenReceive;
import com.oppo.marketdemo.globle.C;

import androidx.annotation.Nullable;

/**
 * Created by mycoder on 2017/11/17.
 */

public class ScreenService extends Service {

    private IntentFilter filter = null;
    private ScreenReceive broadcast = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Wakelock", "onStartCommand");
        C.SERVICE_IS_RUNNING = true;
        registerBroadcast();
        if (intent == null) {
            return START_NOT_STICKY;
        }
        if(intent.getAction() == null){
            return START_NOT_STICKY;
        }
        return START_STICKY;
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }

    /**
     * 注册广播
     */
    private void registerBroadcast() {
        Log.e("Wakelock", "registerBroadcast");
        if (broadcast == null) {
            broadcast = new ScreenReceive();
        }
        if (filter == null) {
            filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.setPriority(2147483647);// 提升广播接收器优先级
        }
        registerReceiver(broadcast, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Wakelock", "onDestroy");
        C.SERVICE_IS_RUNNING = false;
        try{
            if (broadcast != null){
                unregisterReceiver(broadcast);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}