package com.oppo.marketdemo.fragments.camera;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;
import com.oppo.marketdemo.utils.CameraUtil;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:夜景霓虹人像
 */
public class NightFlarePortraitFragment extends BaseFragment {

    private Thread thread;
    private TextureVideoView mVideo, mVideo1;

    private TypefaceTextView GoCompare, GoCompare1, GoStep1, GoStep;
    private ImageView GoBack, GoBack1, mBg, bgCamera, bt1, bt2, ivCamera, ivCover;
    private ConstraintLayout ll_Compare, llStep, cl_camera_bt1, cl_camera_bt2;
    private boolean isShow = true;
    private View shade;
    private ObjectAnimator animator;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_night_camera;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideo = mRootView.findViewById(R.id.texture_video_view);
        mVideo1 = mRootView.findViewById(R.id.texture_video_view1);
        GoCompare = mRootView.findViewById(R.id.bt_compare);
        GoCompare1 = mRootView.findViewById(R.id.bt_compare1);
        GoBack = mRootView.findViewById(R.id.iv_upper_back);
        GoBack1 = mRootView.findViewById(R.id.iv_upper_back1);
        GoStep = mRootView.findViewById(R.id.bt_step);
        GoStep1 = mRootView.findViewById(R.id.bt_step1);
        ll_Compare = mRootView.findViewById(R.id.ll_button_two);
        llStep = mRootView.findViewById(R.id.cl_step);
        mBg = mRootView.findViewById(R.id.iv_night_bg);
        ivCover = mRootView.findViewById(R.id.iv_cover);

        bgCamera = mRootView.findViewById(R.id.iv_camera_bg);
        shade = mRootView.findViewById(R.id.view_shade);
        bt1 = mRootView.findViewById(R.id.btn_ai1);
        bt2 = mRootView.findViewById(R.id.btn_ai2);
        ivCamera = mRootView.findViewById(R.id.iv_camera);

        cl_camera_bt1 = mRootView.findViewById(R.id.cl_bt1);
        cl_camera_bt2 = mRootView.findViewById(R.id.cl_bt2);
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
        mVideo1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideo1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideo1 != null && mVideo1.getVisibility() == View.VISIBLE) {
                            mVideo1.seekTo(0);
                            mVideo1.start();
                            isShow = true;
                            mActivity.setTextVISIBLE();
                            mActivity.setArrowsDispaly();
                            if (GoCompare1 != null) {
                                GoCompare1.setVisibility(View.VISIBLE);
                            }
                            if (GoBack1 != null) {
                                GoBack1.setVisibility(View.VISIBLE);
                            }
                        }


                    }
                }, 1000);
            }
        });

        GoCompare.setOnClickListener(this);
        GoCompare1.setOnClickListener(this);
        GoBack.setOnClickListener(this);
        GoBack1.setOnClickListener(this);
        GoStep.setOnClickListener(this);
        GoStep1.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        initFragmentViews();

    }

    @Override
    protected void start() {
        if (mVideo != null) {
//            mVideo.setVideoId(R.raw.vv_night);
//            mVideo.start();
//            mVideo.setVisibility(View.VISIBLE);
        }
        if (mVideo1 != null) {
            mVideo1.setVideoId(R.raw.vv_night);
        }
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
                                    if (mVideo1 != null && mVideo1.getCurrentPosition() > 1250 && isShow) {
                                        mVideo1.pause();
                                        if (mActivity != null){
                                            mActivity.setTextINVISIBLE();
                                            mActivity.setArrowsHide();
                                        }

                                        isShow = false;
//                                        if (GoCompare1 != null) {
//                                            GoCompare1.setVisibility(View.GONE);
//                                        }
//                                        if (GoBack1 != null) {
//                                            GoBack1.setVisibility(View.GONE);
//                                        }
                                        bgCamera.setVisibility(View.VISIBLE);
                                        shade.setVisibility(View.VISIBLE);
                                        cl_camera_bt1.setVisibility(View.VISIBLE);
                                        if (animator != null) {
                                            animator.cancel();
                                        }
                                        animator = AnimationOppoUtils.setBreatheMethods(bt1);
                                        animator.start();
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
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_compare1:
            case R.id.bt_compare:
                if (null != ivCover){
                    ivCover.setVisibility(View.GONE);
                }
                stopThread();
                if (mVideo != null) {
                    mVideo.seekTo(0);
                    mVideo.pause();
                    mVideo.setVisibility(View.INVISIBLE);
                }
                if (mVideo1 != null) {
                    mVideo1.seekTo(0);
                    mVideo1.pause();
                    mVideo1.setVisibility(View.INVISIBLE);
                }
                if (mBg != null) {
                    mBg.setImageResource(R.mipmap.bg_night_compare);
                }
                GoCompare.setVisibility(View.GONE);
                GoStep.setVisibility(View.GONE);
                ll_Compare.setVisibility(View.VISIBLE);
                ivCamera.setVisibility(View.VISIBLE);
                llStep.setVisibility(View.GONE);
                if (mActivity != null && mActivity.getCurPosition() == 15) {
                    mActivity.setTextVISIBLE();
                    mActivity.setTextBlackColor();
                    mActivity.setIconBlack();
                    mActivity.setArrowsDispaly();
                }
                break;

            case R.id.iv_upper_back1:
            case R.id.iv_upper_back:
                initFragmentViews();
                if (null != ivCover){
                    ivCover.setVisibility(View.VISIBLE);
                }
                if (mVideo1 != null) {
                    mVideo1.seekTo(0);
                    mVideo1.pause();
                    mVideo1.setVisibility(View.INVISIBLE);
                }
//                if (mVideo != null) {
//                    mVideo.seekTo(0);
//                    mVideo.start();
//                    mVideo.setVisibility(View.VISIBLE);
//                }
                isShow = true;
                if (mActivity != null && mActivity.getCurPosition() == 15) {
                    mActivity.setTextVISIBLE();
                    mActivity.setTextWhiteColor();
                    mActivity.setIconWhite();
                    mActivity.setArrowsDispaly();
                }
                break;
            case R.id.bt_step1:
            case R.id.bt_step:
                if (null != ivCover){
                    ivCover.setVisibility(View.GONE);
                }
                mBg.setImageResource(R.mipmap.bg_night_bg);

                openThread();
                isShow = true;
                if (mVideo != null) {
                    mVideo.seekTo(0);
                    mVideo.pause();
                    mVideo.setVisibility(View.INVISIBLE);
                }
                ivCamera.setVisibility(View.GONE);

                GoCompare.setVisibility(View.GONE);
                GoStep.setVisibility(View.GONE);
                ll_Compare.setVisibility(View.GONE);
                cl_camera_bt1.setVisibility(View.GONE);
                cl_camera_bt2.setVisibility(View.GONE);
                shade.setVisibility(View.GONE);
                bgCamera.setVisibility(View.GONE);
                if (mVideo1 != null) {
                    mVideo1.seekTo(0);
                    mVideo1.start();
                    mVideo1.setVisibility(View.VISIBLE);
                }
                if (mBg != null) {
                    mBg.setImageResource(R.mipmap.bg_night_bg);
                }
                llStep.setVisibility(View.VISIBLE);
                if (mActivity != null && mActivity.getCurPosition() == 15) {
                    mActivity.setTextWhiteColor();
                    mActivity.setIconWhite();

                }
                break;
            case R.id.btn_ai1:
                cl_camera_bt1.setVisibility(View.GONE);
                cl_camera_bt2.setVisibility(View.VISIBLE);
                if (animator != null) {
                    animator.cancel();
                }
                animator = AnimationOppoUtils.setBreatheMethods(bt2);
                animator.start();
                break;
            case R.id.btn_ai2:
                cl_camera_bt2.setVisibility(View.GONE);
                shade.setVisibility(View.GONE);
                if (animator != null) {
                    animator.cancel();
                }
                bgCamera.setVisibility(View.GONE);
                mVideo1.start();
                break;
            case R.id.iv_camera:
                startActivity(CameraUtil.getPortalStyle());

                break;
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
        stopThread();
        if (animator != null) {
            animator.cancel();
        }
        isShow = true;
        if (null != ivCover){
            ivCover.setVisibility(View.VISIBLE);
        }
        if (mBg != null) {
            mBg.setImageResource(R.mipmap.bg_night_end_bg);
        }
        if (mVideo != null) {
            mVideo.seekTo(0);
            mVideo.pause();
            mVideo.setVisibility(View.INVISIBLE);
        }
        if (mVideo1 != null) {
            mVideo1.seekTo(0);
            mVideo1.pause();
            mVideo1.setVisibility(View.INVISIBLE);
        }
        if (ivCamera != null) {
            ivCamera.setVisibility(View.GONE);
        }
        if (ll_Compare != null) {
            ll_Compare.setVisibility(View.GONE);
        }

        if (llStep != null) {
            llStep.setVisibility(View.GONE);
        }
        if (GoCompare != null) {
            GoCompare.setVisibility(View.VISIBLE);
        }
        if (GoStep != null) {
            GoStep.setVisibility(View.VISIBLE);
        }

        if (GoCompare1 != null) {
            GoCompare1.setVisibility(View.VISIBLE);
        }
        if (GoBack1 != null) {
            GoBack1.setVisibility(View.VISIBLE);
        }

        if (cl_camera_bt1 != null) {
            cl_camera_bt1.setVisibility(View.GONE);
        }
        if (cl_camera_bt2 != null) {
            cl_camera_bt2.setVisibility(View.GONE);
        }
        if (bgCamera != null) {
            bgCamera.setVisibility(View.GONE);
        }
        if (shade != null) {
            shade.setVisibility(View.GONE);
        }
    }

    @Override
    protected void destroyFragmentViews() {
        stopThread();
        if (mVideo != null) {
            mVideo.stopPlayback();
            mVideo = null;
        }
        if (mVideo1 != null) {
            mVideo1.stopPlayback();
            mVideo1 = null;
        }
        ll_Compare = null;
        llStep = null;
        GoStep = null;
        GoCompare = null;
    }
}