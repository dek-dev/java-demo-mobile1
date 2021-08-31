package com.oppo.marketdemo.globle;

import android.content.Intent;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2017/9/8 16:42
 * Description: 应用闪退异常捕捉重启类
 */

public class ReStartExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "ReStartExceptionHandler";

    private static ReStartExceptionHandler mReStartExceptionHandler;

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private VApplication mAppContext;

    public static ReStartExceptionHandler getInstance(){
        if (mReStartExceptionHandler == null) {
            mReStartExceptionHandler = new ReStartExceptionHandler();
        }
        return mReStartExceptionHandler;
    }

    public void initReStartExceptionHandler(VApplication application){
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mAppContext = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mDefaultHandler != null){
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }else{
            Intent intent = mAppContext.getPackageManager().getLaunchIntentForPackage(mAppContext.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mAppContext.startActivity(intent);
            VApplication.finishActivity();
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        return ex != null;
    }
}
