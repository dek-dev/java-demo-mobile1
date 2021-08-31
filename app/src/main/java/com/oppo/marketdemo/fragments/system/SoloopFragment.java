package com.oppo.marketdemo.fragments.system;

import android.media.MediaPlayer;
import android.view.View;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:即录
 */
public class SoloopFragment extends BaseFragment {
    private TextureVideoView mVideoView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_soloop;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideoView = mRootView.findViewById(R.id.texture_video_view);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mVideoView != null) {
                    mVideoView.seekTo(0);
                    mVideoView.start();
                }
            }
        });
    }

    @Override
    protected void start() {
        startThread();
    }

    private void startThread() {
        if (mVideoView != null) {
//            mVideoView.setVideoId(R.raw.vv_game_barrage);
//            mVideoView.start();
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
            mVideoView.pause();
            mVideoView.seekTo(0);
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