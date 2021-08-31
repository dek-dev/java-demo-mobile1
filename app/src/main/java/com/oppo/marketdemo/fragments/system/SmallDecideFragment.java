package com.oppo.marketdemo.fragments.system;

import android.media.MediaPlayer;
import android.view.View;

import com.google.android.exoplayer2.Player;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.ExoVideoView;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:小决定
 */
public class SmallDecideFragment extends BaseFragment {
    private ExoVideoView mVideo;
    private TypefaceTextView mTip1, mTip2, mTip3;
    private int type = 1;
    private boolean isTap = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_small_decide;
    }


    @Override
    protected void initViews(View mRootView) {
        mVideo = mRootView.findViewById(R.id.texture_video_view8);
        mTip1 = mRootView.findViewById(R.id.iv_tip1);
        mTip2 = mRootView.findViewById(R.id.iv_tip2);
        mTip3 = mRootView.findViewById(R.id.iv_tip3);
    }

    @Override
    protected void init() {
        mVideo.setResource(R.mipmap.bg_samll_decide, R.raw.vv_small_decide1);
        mVideo.addEventListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if (playWhenReady && playbackState == Player.STATE_ENDED) {
                    mVideo.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mVideo != null && null != mActivity && mActivity.getCurPosition() == 20) {
                                if (isTap) {
                                    mVideo.start();
                                    return;
                                }

                                if (type == 1) {
                                    mVideo.setVideoId(R.raw.vv_small_decide2);
                                    mVideo.start();
                                    refTip(2);
                                } else if (type == 2) {
                                    mVideo.setVideoId(R.raw.vv_small_decide3);
                                    mVideo.start();
                                    refTip(3);
                                } else {
                                    mVideo.setVideoId(R.raw.vv_small_decide1);
                                    mVideo.start();
                                    refTip(1);

                                }

                            }

                        }
                    }, 1000);

                }
            }
        });
        mTip1.setOnClickListener(this);
        mTip2.setOnClickListener(this);
        mTip3.setOnClickListener(this);
        initFragmentViews();
    }

    @Override
    protected void start() {
        if (mVideo != null) {
            mVideo.setResource(R.mipmap.bg_samll_decide, R.raw.vv_small_decide1);
            mVideo.start();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        isTap = true;
        switch (view.getId()) {

            case R.id.iv_tip1:
                if (type != 1) {
                    refTip(1);
                    mVideo.setVideoId(R.raw.vv_small_decide1);
                    mVideo.start();
                }

                break;
            case R.id.iv_tip2:
                if (type != 2) {
                    refTip(2);
                    mVideo.setVideoId(R.raw.vv_small_decide2);
                    mVideo.start();
                }
                break;
            case R.id.iv_tip3:
                if (type != 3) {
                    refTip(3);
                    mVideo.setVideoId(R.raw.vv_small_decide3);
                    mVideo.start();
                }

                break;
            default:
        }
    }

    protected void refTip(int type1) {
        type = type1;
        if (mActivity != null) {
            mTip1.setBackground(mActivity.getDrawable(R.mipmap.decide_tip1));
            mTip2.setBackground(mActivity.getDrawable(R.mipmap.decide_tip3));
            mTip3.setBackground(mActivity.getDrawable(R.mipmap.decide_tip2));
            switch (type1) {
                case 1:
                    mTip1.setBackground(mActivity.getDrawable(R.mipmap.decide_tip1_p));
                    break;
                case 2:
                    mTip2.setBackground(mActivity.getDrawable(R.mipmap.decide_tip3_p));

                    break;
                case 3:
                    mTip3.setBackground(mActivity.getDrawable(R.mipmap.decide_tip2_p));
                    break;

            }

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
        type = 1;
        isTap = false;

        if (mVideo != null) {
            mVideo.releasePlayer();
        }
        type = 1;
        if (mTip1 != null) {
            mTip1.setBackground(mActivity.getDrawable(R.mipmap.decide_tip1_p));
        }
        if (mTip2 != null) {
            mTip2.setBackground(mActivity.getDrawable(R.mipmap.decide_tip3));
        }
        if (mTip3 != null) {
            mTip3.setBackground(mActivity.getDrawable(R.mipmap.decide_tip2));
        }
    }


    @Override
    protected void destroyFragmentViews() {
        if (mVideo != null) {
            mVideo.releasePlayer();
            mVideo = null;
        }
    }
}