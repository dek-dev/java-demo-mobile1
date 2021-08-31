package com.oppo.marketdemo.globle;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.oppo.marketdemo.service.ServiceManager;
import com.oppo.marketdemo.utils.PermissionUtil;

import androidx.annotation.NonNull;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2018/8/23
 * Description: ActivityLifecycleListener
 */

public class ActivityLifecycleListener implements ActivityLifecycleCallbacks {

    private int refCount = 0;

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        refCount++;
        Log.e(getClass().getName(), "onActivityStarted refCount = " + refCount);
        if (refCount == 1){
            PermissionUtil.checkPermission(activity, C.permissions);
            if (!Settings.System.canWrite(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        }
        if (!C.SERVICE_IS_RUNNING && activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE).getBoolean(C.STAT_UNLOCK_SCREEN_PLAY, false)){
            VApplication.getInstance().startService(ServiceManager.getInstance(VApplication.getInstance()));
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        refCount--;
        Log.e(getClass().getName(), "onActivityStopped refCount = " + refCount);
        if(refCount == 0){
            C.SubPageActivityCreate = true;
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }
}
