package com.oppo.marketdemo.globle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.oppo.marketdemo.MainActivity;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2019/2/21 14:08
 * Description: OPPO Compass Application
 */

public class VApplication extends Application {
    public static int CHANGE_NULL = -1;
    /**
     * 第一次进去
     */
    public static boolean isFirst = true;
    public static boolean sideIsWhite = true;
    private SharedPreferences sp;
    public static int changeInt = CHANGE_NULL;

    private static ArrayList<Activity> list = new ArrayList<>();
    private static VApplication application;

    public static VApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        Fresco.initialize(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
        /** 异常处理器初始化 */
        ReStartExceptionHandler.getInstance().initReStartExceptionHandler(this);
//        initLeakCanary();
    }

//    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
//    }

    /**
     * 向Activity列表中添加Activity对象*/
    public static void addActivity(Activity a){
        list.add(a);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象*/
    public static void removeActivity(Activity a){
        list.remove(a);
    }

    public static Activity getTopActivity(){
        if (list != null && list.size() > 0){
            return list.get(list.size() - 1);
        }
        return null;
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public static void finishActivity(){
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void restartApp(Context context){
        for (Activity activity : list){
            if (activity != null && !activity.getClass().equals(MainActivity.class)){
                activity.finish();
            }
        }
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
