package com.oppo.marketdemo.fragments.performance;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.CameraUtil;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:AON 手势控制
 */
public class AnoFragment extends BaseFragment {

    private TextureVideoView mVideoView;
    private TypefaceTextView GoExperience;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ano;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideoView = mRootView.findViewById(R.id.texture_video_view);
        GoExperience = mRootView.findViewById(R.id.bt_experience);
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
                        }

                    }
                }, 1000);
            }
        });

        GoExperience.setOnClickListener(this);
    }

    @Override
    protected void start() {
        startThread();
    }

    private void startThread() {
        if (mVideoView != null) {
            mVideoView.setVideoId(R.raw.vv_hand);
            mVideoView.start();
            mVideoView.setVisibility(View.VISIBLE);
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
        if (mVideoView != null) {
            mVideoView.seekTo(0);
            mVideoView.pause();
            mVideoView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_experience:
                try {
                    Intent intent = CameraUtil.Hand();
                    startActivity(intent);
                }catch (Exception e){


                }

                break;


        }
    }

    @Override
    protected void destroyFragmentViews() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView = null;
        }
    }
}