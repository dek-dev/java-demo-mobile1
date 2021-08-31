package com.oppo.marketdemo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.reflect.Method;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2019/8/6
 * Description: 屏幕参数工具类
 */

public class DisplayUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue, float density) {
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(Context context, float spValue, float density) {
        return (int) (spValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue, float density) {
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Context context, float pxValue, float density) {
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 获取手机分辨率
     */
    public static int[] getDisplay(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new int[]{dm.widthPixels, dm.heightPixels};
    }

    /**
     * 系统设置"显示大小"时原有UI样式保持不变：
     *
     * 1、当调节手机系统"显示大小"【调大】的时候，相应的dpi会变大【dp = (dpi/160) * px】,此时dp就会变大，所以相应的UI布局就会变大。
     * 2、当调节手机系统"分辨率"【调小】的时候，相应的dpi会变小【比如由480-->320】。如果此时使用技术手段使dpi保持不变，那么相同的dp就会占用更多的px，所以UI布局就会变大。
     *
     * @param context
     */
    public static void setDefaultDisplay(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Configuration origConfig = context.getResources().getConfiguration();
            //获取手机出厂时默认的densityDpi【注释1】
            origConfig.densityDpi = getDefaultDisplayDensity();
            context.getResources().updateConfiguration(origConfig, context.getResources().getDisplayMetrics());
        }
    }

    public static int getDefaultDisplayDensity() {
        try {
            Class clazz = Class.forName("android.view.WindowManagerGlobal");
            Method method = clazz.getMethod("getWindowManagerService");
            method.setAccessible(true);
            Object iwm = method.invoke(clazz);
            Method getInitialDisplayDensity = iwm.getClass().getMethod("getInitialDisplayDensity", int.class);
            getInitialDisplayDensity.setAccessible(true);
            Object densityDpi = getInitialDisplayDensity.invoke(iwm, Display.DEFAULT_DISPLAY);
            return (int)densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}