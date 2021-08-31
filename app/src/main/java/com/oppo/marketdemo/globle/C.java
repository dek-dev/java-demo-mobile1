package com.oppo.marketdemo.globle;

import android.Manifest;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2019/3/20
 * Description: APP常量类
 */

public class C {

    public static boolean SubPageActivityCreate = true;

    public static final String STAT_UNLOCK_SCREEN_PLAY = "STAT_UNLOCK_SCREEN_PLAY";
    public static boolean SERVICE_IS_RUNNING = false;
    public static final int DEFAULT_BRIGHTNESS = 78;//默认息屏视频亮度
    public static final float DEFAULT_VOLUME = 0.3f;//默认息屏视频音量

    public static String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
    };
}