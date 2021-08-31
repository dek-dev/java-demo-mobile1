package com.oppo.marketdemo.fragments.performance;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.CameraUtil;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:防偷窥
 */
public class PreventionFragment extends BaseFragment {
    private TextureVideoView mVideoView, mVideoView1;
    private TypefaceTextView GoCompare, GoExperience, GoExperience1;
    private ImageView GoBack, mBg, mCover;
    private ConstraintLayout ll_bt;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_prevent;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideoView = mRootView.findViewById(R.id.texture_video_view);
        mVideoView1 = mRootView.findViewById(R.id.texture_video_view1);
        GoCompare = mRootView.findViewById(R.id.bt_compare);
        GoExperience = mRootView.findViewById(R.id.bt_experience);
        GoExperience1 = mRootView.findViewById(R.id.bt_experience1);
        GoBack = mRootView.findViewById(R.id.iv_upper_back);
        ll_bt = mRootView.findViewById(R.id.cl_compare);
        mBg = mRootView.findViewById(R.id.iv_bg);
        mCover = mRootView.findViewById(R.id.iv_cover);
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

        mVideoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideoView1 != null && mVideoView1.getVisibility() == View.VISIBLE) {
                            mVideoView1.seekTo(0);
                            mVideoView1.start();
                        }

                    }
                }, 1000);
            }
        });
        GoCompare.setOnClickListener(this);
        GoExperience.setOnClickListener(this);
        GoExperience1.setOnClickListener(this);
        GoBack.setOnClickListener(this);
    }

    @Override
    protected void start() {
        startThread();
    }

    private void startThread() {
        if (mVideoView != null) {
            mVideoView.setVideoId(R.raw.vv_prevent);
            mVideoView.setVisibility(View.VISIBLE);
            mVideoView.start();
        }
        if (mVideoView1 != null) {
            mVideoView1.setVideoId(R.raw.vv_prevent_compare);

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_compare:
                if (mBg != null) {
                    mBg.setImageResource(R.mipmap.bg_prevent_compare);
                }
                mCover.setVisibility(View.GONE);
                GoCompare.setVisibility(View.GONE);
                GoExperience1.setVisibility(View.GONE);
                if (mVideoView != null) {
                    mVideoView.seekTo(0);
                    mVideoView.pause();
                    mVideoView.setVisibility(View.INVISIBLE);
                }
                if (mVideoView1 != null) {
                    mVideoView1.seekTo(0);
                    mVideoView1.start();
                    mVideoView1.setVisibility(View.VISIBLE);
                }
                if (mActivity != null) {
                    mActivity.setTextBlackColor();
                    mActivity.setIconBlack();
                }
                ll_bt.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_experience:
            case R.id.bt_experience1:
                Intent intent = CameraUtil.Prevent();
                mActivity.startActivity(intent);
                break;
            case R.id.iv_upper_back:
                initFragmentViews();
                if (mVideoView != null) {
                    mVideoView.seekTo(0);
                    mVideoView.start();
                    mVideoView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;

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
         }
        if (mVideoView1 != null) {
            mVideoView1.seekTo(0);
            mVideoView1.pause();
            mVideoView1.setVisibility(View.INVISIBLE);
        }
        if (mActivity != null && mActivity.getCurPosition() == 2) {
            mActivity.setTextWhiteColor();
            mActivity.setIconWhite();
        }
        if (mCover != null) {
            mCover.setVisibility(View.VISIBLE);
        }

        if (mBg != null) {
            mBg.setImageResource(R.mipmap.bg_prevent);

        }
        if (ll_bt != null) {
            ll_bt.setVisibility(View.GONE);
        }
        if (GoCompare != null) {
            GoCompare.setVisibility(View.GONE);
        }
        if (GoExperience1 != null) {
            GoExperience1.setVisibility(View.GONE);
        }
    }

    @Override
    protected void destroyFragmentViews() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView = null;
        }
        if (mVideoView1 != null) {
            mVideoView1.stopPlayback();
            mVideoView1 = null;
        }
    }
}