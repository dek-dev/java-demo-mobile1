package com.oppo.marketdemo.fragments.exterior;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.SlowScrollView;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:90Hz
 */
public class FullScreenFragment extends BaseFragment {
    private Thread thread;
    private TextureVideoView mVideoView;
    private boolean isShow = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_full_screen;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideoView = mRootView.findViewById(R.id.texture_video_view);

    }

    @Override
    protected void init() {

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideoView != null && mVideoView.getVisibility() == View.VISIBLE) {
                            mVideoView.seekTo(0);
                            mVideoView.start();
                            mActivity.setTextBlackColor();
                            mActivity.setIconBlack();
                            isShow = true;
                        }

                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void start() {
        startThread();
    }

    private void startThread() {
        if (mVideoView != null) {
            mVideoView.setVideoId(R.raw.vv_full_screen);
            mVideoView.start();
            mVideoView.setVisibility(View.VISIBLE);
        }
        openThread();
    }

    private void openThread() {
        stopThread();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (mActivity != null) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mVideoView != null && mVideoView.getCurrentPosition() > 2400 && isShow ) {
                                        mActivity.setTextWhiteColor();
                                        mActivity.setIconWhite();
                                        isShow = false;
                                        return;
                                    }


                                }
                            });
                        }
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                }
            }
        });
        thread.start();
    }

    private void stopThread() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }

    }

    @Override
    protected void pause() {
        initFragmentViews();
    }

    /**
     * 初始化状态
     */
    private void initFragmentViews() {
        stopThread();
        isShow = true;
        if (mVideoView != null) {
            mVideoView.seekTo(0);
            mVideoView.pause();
            mVideoView.setVisibility(View.INVISIBLE);
        }
        if (mActivity != null&&mActivity.getCurPosition() == 8) {
            mActivity.setTextBlackColor();
            mActivity.setIconBlack();
        }
    }

    @Override
    protected void destroyFragmentViews() {
        stopThread();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView = null;
        }
    }
}