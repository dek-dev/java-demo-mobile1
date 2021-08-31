package com.oppo.marketdemo.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.oppo.marketdemo.globle.VApplication;
import com.oppo.marketdemo.utils.DisplayUtil;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.pm.ApplicationInfo.FLAG_SUPPORTS_RTL;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2018/2/26
 * Description: Activity基类
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int SYSTEM_UI_FLAG = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    private Thread mThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        VApplication.addActivity(this);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                hideSystemUi();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mThread != null){
            mThread.interrupt();
            mThread = null;
        }
        VApplication.removeActivity(this);
    }

    public void showIcon(){}
    public void hideIcon(){}
    public void setProgressViewWhite(){}
    public void setProgressViewBlack(){}
    public void setIconWhite(){}
    public void setIconBlack(){}
    public void setArrowsWhite(){}
    public void setArrowsHide(){}
    public void setArrowsDispaly(){}
    public void setArrowsBlack(){}
    public int getCurPosition(){
        return -1;
    }


    public void setTitleText(int resid){}
    public void setSmallTitleText(int resid){}
    public void setContentText(int resid){}
    public void setSmallTitleGone(){}
    public void setTitleRemake(int resid){}

    public void setTextVISIBLE(){}
    public void setTextINVISIBLE(){}
    public void setTextWhiteColor(){}
    public void setTextBlackColor(){}


    /**
     * 隐藏系统虚拟按键等
     */
    @SuppressLint("NewApi")
    public void hideSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void attachBaseContext(Context base) {
        Configuration defaultConfig = new Configuration();
        defaultConfig.setToDefaults();
        defaultConfig.densityDpi = DisplayUtil.getDefaultDisplayDensity();
        super.attachBaseContext(base.createConfigurationContext(defaultConfig));
    }

    @Override
    public void onClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK, HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING);
    }

    /**
     * 判断是否进行阿拉伯镜像，判断取决于两个条件：<br />
     * 一丶Androidmanifest.xml中的supportsRtl<br />
     * 二、当前系统为阿拉伯语
     *
     * @return 是否为rtl
     */
    public boolean isRTL() {
        ApplicationInfo applicationInfo = VApplication.getInstance().getApplicationInfo();
        boolean hasRtlSupport = (applicationInfo.flags & FLAG_SUPPORTS_RTL) == FLAG_SUPPORTS_RTL;
        boolean isRtl = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
        return hasRtlSupport && isRtl;
    }

    /**
     * 获取SharedPreferences
     */
    public SharedPreferences getShearedPreferences(){
        return getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }
}
