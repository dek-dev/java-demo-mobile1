package com.oppo.marketdemo;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.oppo.marketdemo.base.BaseActivity;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.globle.C;
import com.oppo.marketdemo.globle.VApplication;
import com.oppo.marketdemo.utils.DeviceUtils;

import androidx.annotation.Nullable;

/**
 * Created by szm on 2018/5/10.
 */

public class ProductVideoActivity extends BaseActivity {

    private int mCurrentBrightness;
    private int mCurrentBrightnessMode;
    private int sleepTime;

    private TextureVideoView mVideoView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        super.onCreate(savedInstanceState);
        setShowWhenLocked(true);
        setContentView(R.layout.activity_product_video);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(){
        mVideoView = findViewById(R.id.texture_video_view);

        mVideoView.setKeepScreenOn(true);
        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        goHome();
                        break;
                }
                return true;
            }
        });
        sleepTime = DeviceUtils.getScreenOffTime(ProductVideoActivity.this);
        changeScreenOffTime(Integer.MAX_VALUE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playById();
        mCurrentBrightness = DeviceUtils.getBrightness(getApplicationContext());
        mCurrentBrightnessMode = DeviceUtils.getBrightnessMode(getApplicationContext());
        //改变亮度
        changeBrightness(C.DEFAULT_BRIGHTNESS, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    private void playById(){
//        mVideoView.setVideoId(R.raw.vv_product_video);
//        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                mVideoView.start();
//            }
//        });
//        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null && mVideoView.isPlaying()){
            mVideoView.suspend();
        }
    }

    private void changeScreenOffTime(int sleepTime){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)){
                DeviceUtils.setScreenOffTime(ProductVideoActivity.this, sleepTime);
            }
        }else{
            DeviceUtils.setScreenOffTime(ProductVideoActivity.this, sleepTime);
        }
    }

    private void changeBrightness(int brightness, int mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                DeviceUtils.setBrightnessMode(getApplicationContext(), mode);
                DeviceUtils.setBrightness(getApplicationContext(), brightness);
            }
        } else {
            DeviceUtils.setBrightnessMode(getApplicationContext(), mode);
            DeviceUtils.setBrightness(getApplicationContext(), brightness);
        }
    }

    @Override
    public void onBackPressed() {
        goHome();
    }

    private void goHome() {
        changeScreenOffTime(sleepTime);
        changeBrightness(mCurrentBrightness, mCurrentBrightnessMode);
        VApplication.restartApp(this);
    }
}