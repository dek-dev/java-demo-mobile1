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
 * Description:闪回键
 */
public class FlashbackKeyFragment extends BaseFragment {
    private TextureVideoView mVideo;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_flashback_key;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideo = mRootView.findViewById(R.id.texture_video_view);
    }

    @Override
    protected void init() {
        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideo != null && mVideo.getVisibility() == View.VISIBLE) {
                            mVideo.seekTo(0);
                            mVideo.start();
                        }

                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void start() {
        if (mVideo != null) {
            mVideo.setVideoId(R.raw.vv_os_quick);
            mVideo.start();
            mVideo.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            default:
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

        if (mVideo != null) {
            mVideo.seekTo(0);
            mVideo.pause();
            mVideo.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    protected void destroyFragmentViews() {
        if (mVideo != null) {
            mVideo.stopPlayback();
            mVideo = null;
        }
    }

}