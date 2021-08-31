package com.oppo.marketdemo.fragments.performance;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.interpolator.MyInterpolator;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:8+128G
 */
public class BigMemoryFragment extends BaseFragment {
    private TextureVideoView mVideoViewMain;
    private FrameLayout fl1, fl2, fl3, fl4;
    private ConstraintLayout cl1;
    private Handler mHandler;
    private ObjectAnimator animatorLide1,animatorLide2,animatorLide3,animatorLide4;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_big_memory;
    }

    @Override
    protected void initViews(View mRootView) {

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {

                    case 0:
                        animatorLide2.start();
                        mHandler.sendEmptyMessageDelayed(1, 100);
                        break;
                    case 1:
                        animatorLide3.start();
                        mHandler.sendEmptyMessageDelayed(2, 100);
                        break;

                    case 2:
                        animatorLide4.start();
                        mHandler.sendEmptyMessageDelayed(3, 1800);
                        break;
                    case 3:
                        initFragmentViews();
                        startThread();
                        break;
                    default:
                }
            }
        };

        mVideoViewMain = mRootView.findViewById(R.id.texture_video_view);
        cl1 = mRootView.findViewById(R.id.cl);
        fl1 = mRootView.findViewById(R.id.fl_bg1);
        fl2 = mRootView.findViewById(R.id.fl_bg2);
        fl3 = mRootView.findViewById(R.id.fl_bg3);
        fl4 = mRootView.findViewById(R.id.fl_bg4);
        setVoocAnimationMethods(fl1, fl2, fl3, fl4);

        mVideoViewMain.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                cl1.setVisibility(View.VISIBLE);
                animatorLide1.start();
                mHandler.sendEmptyMessageDelayed(0,100);
            }
        });
    }

    @Override
    protected void start() {
        startThread();
    }

    private void startThread() {
        //设置主页面视频
        if (mVideoViewMain != null) {
            mVideoViewMain.setVideoId(R.raw.vv_memory);
            mVideoViewMain.setVisibility(View.VISIBLE);
            mVideoViewMain.start();
        }


    }

    @Override
    protected void pause() {
        initFragmentViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);


    }


    /**
     * 初始化状态
     */
    private void initFragmentViews() {
        if (mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        if (cl1 != null) {
            cl1.setVisibility(View.GONE);
        }
        if (mVideoViewMain != null) {
            mVideoViewMain.seekTo(0);
            mVideoViewMain.pause();
            mVideoViewMain.setVisibility(View.INVISIBLE);
        }
        if (fl1 != null){
            fl1.setScaleX(0f);
            fl1.setScaleY(0f);
        }
        if (fl2 != null){
            fl2.setScaleX(0f);
            fl2.setScaleY(0f);
        }
        if (fl3 != null){
            fl3.setScaleX(0f);
            fl3.setScaleY(0f);
        }
        if (fl4 != null){
            fl4.setScaleX(0f);
            fl4.setScaleY(0f);
        }
    }

    @Override
    protected void destroyFragmentViews() {
        if (mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mVideoViewMain != null) {
            mVideoViewMain.stopPlayback();
            mVideoViewMain = null;
        }
    }

    /**
     * Vooc动画
     */
    public  void setVoocAnimationMethods(View... views) {

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        /*
         *  设置动画的持续时间
         */

        animatorLide1 = ObjectAnimator.ofPropertyValuesHolder(views[0], scaleX, scaleY);
        animatorLide1.setInterpolator(new MyInterpolator());
        animatorLide1.setDuration(800);

        animatorLide2 = ObjectAnimator.ofPropertyValuesHolder(views[1], scaleX, scaleY);
        animatorLide2.setInterpolator(new MyInterpolator());
        animatorLide2.setDuration(800);

        animatorLide3 = ObjectAnimator.ofPropertyValuesHolder(views[2], scaleX, scaleY);
        animatorLide3.setInterpolator(new MyInterpolator());
        animatorLide3.setDuration(800);

        animatorLide4 = ObjectAnimator.ofPropertyValuesHolder(views[3], scaleX, scaleY);
        animatorLide4.setInterpolator(new MyInterpolator());
        animatorLide4.setDuration(800);




    }


}