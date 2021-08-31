package com.oppo.marketdemo.broadcast;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.util.Log;

import com.oppo.marketdemo.ProductVideoActivity;
import com.oppo.marketdemo.globle.C;

import java.util.Objects;

/**
 * Created by mycoder on 2017/11/17.
 * 熄屏广播
 */

public class ScreenReceive extends BroadcastReceiver {

    private SharedPreferences sp;

    public ScreenReceive(){
    }

    @SuppressLint({"Wakelock", "InvalidWakeLockTag", "MissingPermission", "WakelockTimeout"})
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("Wakelock", "onReceive " + action);
        sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        if (!sp.getBoolean(C.STAT_UNLOCK_SCREEN_PLAY, false)){
            return;
        }
        if (action != null && action.equals(Intent.ACTION_SCREEN_OFF)){
            Log.e("Wakelock", "unlock");
            // 获取PowerManager的实例
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            // 得到一个WakeLock唤醒锁
            PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.ON_AFTER_RELEASE, "bright");
            if (mWakelock.isHeld()) {
                // 释放屏幕,是屏幕休眠
                mWakelock.release();
            }
            if (!mWakelock.isHeld()) {
                Log.e("Wakelock", "!mWakelock.isHeld()");
                // 唤醒屏幕
                mWakelock.acquire();
                mStartActivity(context, ProductVideoActivity.class);
                mWakelock.release();
            }
            // 获得一个KeyguardManager的实例
            KeyguardManager keyguardManager = (KeyguardManager) context
                    .getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = Objects.requireNonNull(keyguardManager).newKeyguardLock("unLock");
            if (keyguardManager.inKeyguardRestrictedInputMode()) {
                // 解锁键盘
                keyguardLock.disableKeyguard();
            }
        }
    }

    private synchronized void mStartActivity(Context context, Class<?> cls) {
        Log.e("Wakelock", "startActivity");
        C.SERVICE_IS_RUNNING = false;
        Intent it = new Intent(context, cls);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }
}