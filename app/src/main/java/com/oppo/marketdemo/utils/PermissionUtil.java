package com.oppo.marketdemo.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2019/7/3
 * Description: Fragment
 */

public class PermissionUtil {

    public static final int PERMISSIONS_REQUEST_CODE = 200;

    /**
     * 检查并申请权限
     * @param activity
     */
    public static boolean checkPermission(Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (!isPermissionGranted(activity, permissions[i])) {
                    ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST_CODE);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否已经拥有权限
     * @param permission
     * @return
     */
    public static boolean isPermissionGranted(Activity activity, String permission){
        int state = ContextCompat.checkSelfPermission(activity, permission);
        return state == PackageManager.PERMISSION_GRANTED;
    }
}