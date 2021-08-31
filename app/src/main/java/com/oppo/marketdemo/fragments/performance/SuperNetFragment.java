package com.oppo.marketdemo.fragments.performance;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.adapter.ViewsAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.ViewPagerIndicator;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;

import java.util.ArrayList;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:5G
 */
public class SuperNetFragment extends BaseFragment {
    private TextureVideoView mVideo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_super_net;
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
            mVideo.setVideoId(R.raw.vv_5g);
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