package com.oppo.marketdemo.utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;

import java.util.ArrayList;

/**
 * Created by szm on 2018/11/6.
 */

public class DeviceUtils {

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 设置休眠时间 毫秒
     */
    public static void setScreenOffTime(Context context, int paramInt) {
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,
                    paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 获取系统休眠时间
     * @param context context
     * @return 休眠时间，单位秒
     */
    public static int getScreenOffTime(Context context) {
        try {
            return Settings.System.getInt(context.getApplicationContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT) / 1000;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 点亮并解锁屏幕
     * @param context
     */
    public static void unlockScreen(Context context) {
        // 获取PowerManager的实例
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        // 得到一个WakeLock唤醒锁
        PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "bright");
        if (!mWakelock.isHeld()) {
            // 唤醒屏幕
            mWakelock.acquire();
            mWakelock.release();
        }
        // 获得一个KeyguardManager的实例
        KeyguardManager km = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        // 得到一个键盘锁KeyguardLock
        KeyguardManager.KeyguardLock mKeyguardLock = km.newKeyguardLock("unLock");
        if (km.inKeyguardRestrictedInputMode()) {
            // 解锁键盘
            mKeyguardLock.disableKeyguard();
        }
    }

    public static void setBrightness(Context context, int brightness) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
    }

    public static int getBrightness(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
    }

    public static int getBrightnessMode(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0);
    }

    public static void setBrightnessMode(Context context, int mode) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        try {
            if (("").equals(ServiceName) || ServiceName == null){
                return false;
            }
            ActivityManager myManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                    .getRunningServices(30);
            for (int i = 0; i < runningService.size(); i++) {
                if (runningService.get(i).service.getClassName()
                        .equals(ServiceName)) {
                    return true;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }

}
