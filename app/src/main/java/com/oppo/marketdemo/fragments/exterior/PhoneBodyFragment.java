package com.oppo.marketdemo.fragments.exterior;

import android.media.MediaPlayer;
import android.view.View;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:轻薄机身
 */
public class PhoneBodyFragment extends BaseFragment {

    private TextureVideoView mVideoView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_body;
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
            mVideoView.setVideoId(R.raw.vv_phone_body);
            mVideoView.setVisibility(View.VISIBLE);
            mVideoView.start();
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
    protected void destroyFragmentViews() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView = null;
        }
    }

}