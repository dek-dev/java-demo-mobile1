package com.oppo.marketdemo.fragments.camera;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
 * Description:高帧智能慢动作
 */
public class AISlowMotionFragment extends BaseFragment {
    private Thread thread;
    private TextureVideoView mVideo, mVideo1, mVideoCompare;
    private TypefaceTextView mStep, GoCamera, GoCamera1;
    private LinearLayout ll_bt1, ll_bt2;
    private int type;
    private ImageView bt1, bt2, mBack, bgCamera, ivCompare, ivGoCamera;
    private View shade;
    private ObjectAnimator animator;
    private ConstraintLayout cl_Step, cl_camera_bt1, cl_camera_bt2, cl_bg_compare;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ai_slow_motion;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideo = mRootView.findViewById(R.id.texture_video_view);
        mVideo1 = mRootView.findViewById(R.id.texture_video_view1);
        mVideoCompare = mRootView.findViewById(R.id.texture_video_view2);
        mStep = mRootView.findViewById(R.id.bt_step);
        ll_bt1 = mRootView.findViewById(R.id.ll_button_one);
        ll_bt2 = mRootView.findViewById(R.id.ll_button_two);
        bt1 = mRootView.findViewById(R.id.btn_ai1);
        bt2 = mRootView.findViewById(R.id.btn_ai2);
        GoCamera = mRootView.findViewById(R.id.bt_camera);
        GoCamera1 = mRootView.findViewById(R.id.bt_camera1);
        mBack = mRootView.findViewById(R.id.iv_upper_back);
        ivCompare = mRootView.findViewById(R.id.iv_compare);

        cl_camera_bt1 = mRootView.findViewById(R.id.cl_bt1);
        cl_camera_bt2 = mRootView.findViewById(R.id.cl_bt2);
        cl_Step = mRootView.findViewById(R.id.cl_step);
        bgCamera = mRootView.findViewById(R.id.iv_camera_bg);
        shade = mRootView.findViewById(R.id.view_shade);
        cl_bg_compare = mRootView.findViewById(R.id.cl_compare_bg);
        ivGoCamera = mRootView.findViewById(R.id.iv_camera);

    }

    @Override
    protected void init() {
        initFragmentViews();
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
                            mActivity.setTextINVISIBLE();
                            type = 1;
                            bgCamera.setImageResource(R.mipmap.bg_camera_slow0);

                        }

                    }
                }, 1000);
            }
        });

        mVideoCompare.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoCompare.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (null != mVideoCompare) {
                            mVideoCompare.seekTo(0);
                            mVideoCompare.setVisibility(View.VISIBLE);
                            mVideoCompare.start();
                        }
                    }
                }, 1000);
            }
        });
        mStep.setOnClickListener(this);
        mBack.setOnClickListener(this);
        GoCamera.setOnClickListener(this);
        GoCamera1.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        ivGoCamera.setOnClickListener(this);
        initFragmentViews();
    }

    @Override
    protected void start() {
        if (mVideo != null) {
            mVideo.setVideoId(R.raw.vv_ai_slow);
            mVideo.start();
            mVideo.setVisibility(View.VISIBLE);
        }
        if (mVideo1 != null) {
            mVideo1.setVideoId(R.raw.vv_ai_slow_step);
        }
        if (null != mVideoCompare) {
            mVideoCompare.setVideoId(R.raw.vv_ai_slow_compare);
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
                                    if (mVideo1 != null && mVideo1.getCurrentPosition() > 700 && type == 1) {
                                        type = 3;
                                        mVideo1.pause();
                                        bgCamera.setAlpha(1f);
                                        bgCamera.setVisibility(View.VISIBLE);
                                        shade.setVisibility(View.VISIBLE);
                                        cl_camera_bt1.setVisibility(View.VISIBLE);
                                        if (animator != null) {
                                            animator.cancel();
                                        }
                                        animator = AnimationOppoUtils.setBreatheMethods(bt1);
                                        animator.start();
                                    }

                                    if ((mVideo1 != null && mVideo1.getCurrentPosition() > 2180 && type == 3)) {
                                        mActivity.setTextVISIBLE();
//                                        ll_bt2.setVisibility(View.VISIBLE);
                                        type = 2;
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
            case R.id.iv_upper_back:
                initFragmentViews();
                if (mVideo != null) {
                    mVideo.start();
                    mVideo.setVisibility(View.VISIBLE);
                }
                if (null != mActivity) {
                    mActivity.setTextVISIBLE();
                    mActivity.setTextWhiteColor();
                    mActivity.setIconWhite();
                    mActivity.setProgressViewWhite();
                    mActivity.setArrowsWhite();
                }
                break;

            case R.id.btn_ai1:
                cl_camera_bt1.setVisibility(View.GONE);
                if (animator != null) {
                    animator.cancel();
                }
                bgCamera.setImageResource(R.mipmap.bg_camera_slow);
                cl_camera_bt2.setVisibility(View.VISIBLE);
                animator = AnimationOppoUtils.setBreatheMethods(bt2);
                animator.start();
                break;

            case R.id.btn_ai2:
                cl_camera_bt2.setVisibility(View.GONE);
                shade.setVisibility(View.GONE);
                if (animator != null) {
                    animator.cancel();
                }
                if (animator != null) {
                    animator.cancel();
                }
                animator = AnimationOppoUtils.setIn(bgCamera);
                animator.start();
                mVideo1.start();
                break;

            //步骤
            case R.id.bt_step:
                setNextMethods(true);
                break;

            //对比
            case R.id.bt_camera:
                setNextMethods(false);
                break;

            //步骤对比切换
            case R.id.bt_camera1:
                setNextMethods(!isStep);
                break;
            case R.id.iv_camera:
                startActivity(CameraUtil.getVideoStyle());
                break;
            default:
        }
    }

    /**
     * 步骤或对比
     *
     * @param isStep
     */
    private boolean isStep;

    private void setNextMethods(boolean isTheStep) {
        type = 1;
        isStep = isTheStep;
        bgCamera.setImageResource(R.mipmap.bg_camera_slow0);
        ll_bt1.setVisibility(View.GONE);
        cl_camera_bt1.setVisibility(View.GONE);
        cl_camera_bt2.setVisibility(View.GONE);
        bgCamera.setVisibility(View.GONE);
        shade.setVisibility(View.GONE);
//        ll_bt2.setVisibility(isStep ? View.GONE : View.VISIBLE);
        ll_bt2.setVisibility(View.VISIBLE);
        cl_Step.setVisibility(isStep ? View.VISIBLE : View.GONE);
        GoCamera1.setText(isStep ? R.string.bt_tap_compare : R.string.bt_tap_step);
        ivCompare.setVisibility(isStep ? View.GONE : View.VISIBLE);
        if (mVideo != null) {
            mVideo.seekTo(0);
            mVideo.pause();
            mVideo.setVisibility(View.INVISIBLE);
        }
        if (isStep) {
            mBack.setImageResource(R.mipmap.upper_back);
            if (null != mActivity) {
                mActivity.setTextINVISIBLE();
                mActivity.setTextWhiteColor();
                mActivity.setIconWhite();
                mActivity.setProgressViewWhite();
                mActivity.setArrowsWhite();
            }
            if (null != mVideoCompare) {
                mVideoCompare.seekTo(0);
                mVideoCompare.pause();
                mVideoCompare.setVisibility(View.INVISIBLE);
            }
            cl_bg_compare.setVisibility(View.GONE);
            if (mVideo1 != null) {
                mVideo1.start();
                mVideo1.setVisibility(View.VISIBLE);
                openThread();
            }
        } else {
            mBack.setImageResource(R.mipmap.upper_black_back);
            if (null != mActivity) {
                mActivity.setTextVISIBLE();
                mActivity.setTextBlackColor();
                mActivity.setIconBlack();
                mActivity.setProgressViewBlack();
                mActivity.setArrowsBlack();
            }
            stopThread();
            if (null != mVideo1) {
                mVideo1.seekTo(0);
                mVideo1.pause();
                mVideo1.setVisibility(View.INVISIBLE);
            }
            if (null != mVideoCompare) {
                mVideoCompare.start();
                mVideoCompare.setVisibility(View.VISIBLE);
            }
            cl_bg_compare.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void pause() {
        initFragmentViews();

        if (mVideo1 != null) {
            mVideo1.suspend();
        }
        if (null != mVideoCompare) {
            mVideoCompare.suspend();
        }
    }

    /**
     * 初始化状态
     */
    private void initFragmentViews() {
        isStep = false;
        type = 1;
        stopThread();
        if (null != ivCompare) {
            ivCompare.setVisibility(View.GONE);
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
        if (null != mVideoCompare) {
            mVideoCompare.seekTo(0);
            mVideoCompare.pause();
            mVideoCompare.setVisibility(View.INVISIBLE);
        }
        if (cl_bg_compare != null) {
            cl_bg_compare.setVisibility(View.GONE);
        }
        if (cl_Step != null) {
            cl_Step.setVisibility(View.GONE);
        }
        if (ll_bt1 != null) {
            ll_bt1.setVisibility(View.VISIBLE);
        }
        if (ll_bt2 != null) {
            ll_bt2.setVisibility(View.GONE);
        }
        if (cl_camera_bt1 != null) {
            cl_camera_bt1.setVisibility(View.GONE);
        }
        if (cl_camera_bt1 != null) {
            cl_camera_bt1.setVisibility(View.GONE);
        }
        if (bgCamera != null) {
            bgCamera.setImageResource(R.mipmap.bg_camera_slow0);
            bgCamera.setAlpha(1f);
            bgCamera.setVisibility(View.GONE);
        }
        if (shade != null) {
            shade.setVisibility(View.GONE);
        }
    }

    @Override
    public void releaseVideo() {
        super.releaseVideo();
        if (mVideo != null) {
            mVideo.suspend();
        }
        if (mVideo1 != null) {
            mVideo1.suspend();
        }
        if (null != mVideoCompare) {
            mVideoCompare.suspend();
        }
    }

    @Override
    public void pauseVideo() {
        super.pauseVideo();
        if (mVideo != null) {
            mVideo.pause();
        }
        if (null != mVideoCompare) {
            mVideoCompare.pause();
        }
    }

    @Override
    public void videoRefresh() {
        super.videoRefresh();
        if (mVideo != null) {
            mVideo.seekTo(0);
        }
        if (null != mVideoCompare) {
            mVideoCompare.seekTo(0);
        }
    }

    @Override
    protected void destroyFragmentViews() {
        stopThread();
        if (mVideo != null) {
            mVideo.stopPlayback();
            mVideo = null;
        }
        if (null != mVideoCompare) {
            mVideoCompare.stopPlayback();
            mVideoCompare = null;
        }
    }
}