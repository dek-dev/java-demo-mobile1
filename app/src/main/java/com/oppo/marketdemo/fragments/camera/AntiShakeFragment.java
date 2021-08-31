package com.oppo.marketdemo.fragments.camera;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.adapter.ViewsAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.ViewPagerIndicator;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;
import com.oppo.marketdemo.utils.CameraUtil;

import java.util.ArrayList;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:超级防抖 3.0
 */
public class AntiShakeFragment extends BaseFragment {

    private TextureVideoView mVideoView, mVideoViewBefore, mVideoViewAfter, mVideoViewPro, mVideoViewDelayed, mVideoStep;
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPager viewPager;
    private View viewCompared;
    private View view1, view2, view3, view4;
    private ViewsAdapter mAdapter;
    private ImageView ivBack, ivStepTip;
    private TypefaceTextView tvStep, tvCompare, tvNext;
    private LinearLayout liButtonOne, liButtonTwo;
    private int oldPosition, curPosition;
    private boolean viewPagerIsChange;
    private FrameLayout flStepAi;
    private ConstraintLayout clStepAll, clStepAi;
    private Thread mThreadStep;
    private ImageView GoCamera1, GoCamera2, GoCamera3, GoCamera4;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anti_shake;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideoView = mRootView.findViewById(R.id.texture_video_view);
        mVideoStep = mRootView.findViewById(R.id.texture_video_view5);
        viewCompared = mRootView.findViewById(R.id.include_compared);
        viewPagerIndicator = mRootView.findViewById(R.id.dot_horizontal_more);
        viewPager = mRootView.findViewById(R.id.view_page);

        ivBack = mRootView.findViewById(R.id.iv_upper_back);
        tvStep = mRootView.findViewById(R.id.bt_step);
        tvCompare = mRootView.findViewById(R.id.bt_compare);
        tvNext = mRootView.findViewById(R.id.bt_intext);
        liButtonOne = mRootView.findViewById(R.id.ll_button_one);
        liButtonTwo = mRootView.findViewById(R.id.ll_button_two);

        clStepAll = mRootView.findViewById(R.id.cl_step);
        clStepAi = mRootView.findViewById(R.id.cl_ai);
        flStepAi = mRootView.findViewById(R.id.fl_ai);
        ivStepTip = mRootView.findViewById(R.id.btn_ai1);
    }

    @Override
    protected void init() {
        setMoreMethods();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVideoView.setVisibility(View.VISIBLE);
                        mVideoView.seekTo(0);
                        mVideoView.start();
                    }
                }, 800);
            }
        });
        mVideoStep.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoStep.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVideoStep.setVisibility(View.VISIBLE);
                        mVideoStep.seekTo(0);
                        mVideoStep.start();
                        isThreadStep = false;
                        openThreadMain();
                    }
                }, 800);
            }
        });
    }

    private void setCompletionMethods(final TextureVideoView videoView) {
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        videoView.seekTo(0);
                        videoView.start();
                    }
                }, 1000);
            }
        });
    }

    /**
     * 查看对比
     */
    private void setMoreMethods() {
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.fragment_anti_shake_layout1, null);
        view2 = lf.inflate(R.layout.fragment_anti_shake_layout2, null);
        view3 = lf.inflate(R.layout.fragment_anti_shake_layout3, null);
//        view4 = lf.inflate(R.layout.fragment_anti_shake_layout4, null);

        mVideoViewBefore = view1.findViewById(R.id.texture_video_view1);
        mVideoViewAfter = view2.findViewById(R.id.texture_video_view2);
        mVideoViewPro = view3.findViewById(R.id.texture_video_view3);
//        mVideoViewDelayed = view4.findViewById(R.id.texture_video_view4);

        GoCamera1 = view1.findViewById(R.id.iv_camera1);
        GoCamera2 = view2.findViewById(R.id.iv_camera2);
        GoCamera3 = view3.findViewById(R.id.iv_camera3);
//        GoCamera4 = view4.findViewById(R.id.iv_camera4);

        setCompletionMethods(mVideoViewBefore);
        setCompletionMethods(mVideoViewAfter);
        setCompletionMethods(mVideoViewPro);
//        setCompletionMethods(mVideoViewDelayed);
        ArrayList<View> mList = new ArrayList<>();
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
//        mList.add(view4);
        mAdapter = new ViewsAdapter(mList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPagerIndicator.setLength(mList.size());
        viewPagerIndicator.setSelected(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                viewPagerIndicator.setSelected(position);
                curPosition = position;
                if (oldPosition != curPosition) {
                    viewPagerIsChange = true;
                    switch (position) {
                        case 0:
                            if (mActivity != null && mActivity.getCurPosition() == 14) {
                                mActivity.setTitleText(R.string.anti_shake_title_before);
                                mActivity.setContentText(R.string.anti_shake_content_before);
                            }
                            break;
                        case 1:
                            if (mActivity != null) {
                                mActivity.setTitleText(R.string.anti_shake_title_after);
                                mActivity.setContentText(R.string.anti_shake_content_after);
                            }
                            break;
                        case 2:
                            if (mActivity != null) {
                                mActivity.setTitleText(R.string.anti_shake_title_pro);
                                mActivity.setContentText(R.string.anti_shake_content_pro);
                            }
                            break;
                        case 3:
                            if (mActivity != null) {
                                mActivity.setTitleText(R.string.anti_shake_title_del);
                                mActivity.setContentText(R.string.anti_shake_content_del);
                            }
                            break;
                        default:
                    }
                    if (oldPosition == 0 && mVideoViewBefore != null) {
                        mVideoViewBefore.seekTo(0);
                        mVideoViewBefore.pause();
                        mVideoViewBefore.setVisibility(View.INVISIBLE);
                    }
                    if (oldPosition == 1 && mVideoViewAfter != null) {
                        mVideoViewAfter.seekTo(0);
                        mVideoViewAfter.pause();
                        mVideoViewAfter.setVisibility(View.INVISIBLE);
                    }
                    if (oldPosition == 2 && mVideoViewPro != null) {
                        mVideoViewPro.seekTo(0);
                        mVideoViewPro.pause();
                        mVideoViewPro.setVisibility(View.INVISIBLE);
                    }
                    if (oldPosition == 3 && mVideoViewDelayed != null) {
                        mVideoViewDelayed.seekTo(0);
                        mVideoViewDelayed.pause();
                        mVideoViewDelayed.setVisibility(View.INVISIBLE);
                    }
                } else {
                    viewPagerIsChange = false;
                }
                oldPosition = curPosition;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (arg0 == ViewPager.SCROLL_STATE_IDLE && viewPagerIsChange) {
                    viewPagerIsChange = false;
                    switch (curPosition) {
                        case 0:
                            if (mVideoViewBefore != null) {
                                mVideoViewBefore.setVideoId(R.raw.vv_anti_shake_before_compared);
                                mVideoViewBefore.setVisibility(View.VISIBLE);
                                mVideoViewBefore.start();
                            }
                            if (mVideoViewAfter != null) {
                                mVideoViewAfter.suspend();
                            }
                            if (mVideoViewPro != null) {
                                mVideoViewPro.suspend();
                            }
                            if (mVideoViewDelayed != null) {
                                mVideoViewDelayed.suspend();
                            }
                            break;
                        case 1:
                            if (mVideoViewAfter != null) {
                                mVideoViewAfter.setVideoId(R.raw.vv_anti_shake_after_compared);
                                mVideoViewAfter.setVisibility(View.VISIBLE);
                                mVideoViewAfter.start();
                            }
                            if (mVideoViewBefore != null) {
                                mVideoViewBefore.suspend();
                            }
                            if (mVideoViewPro != null) {
                                mVideoViewPro.suspend();
                            }
                            if (mVideoViewDelayed != null) {
                                mVideoViewDelayed.suspend();
                            }
                            break;
                        case 2:
                            if (mVideoViewPro != null) {
                                mVideoViewPro.setVideoId(R.raw.vv_anti_shake_pro_compared);
                                mVideoViewPro.setVisibility(View.VISIBLE);
                                mVideoViewPro.start();
                            }
                            if (mVideoViewBefore != null) {
                                mVideoViewBefore.suspend();
                            }
                            if (mVideoViewAfter != null) {
                                mVideoViewAfter.suspend();
                            }
                            if (mVideoViewDelayed != null) {
                                mVideoViewDelayed.suspend();
                            }
                            break;
                        case 3:
                            if (mVideoViewDelayed != null) {
                                mVideoViewDelayed.setVideoId(R.raw.vv_anti_shake_pro_delayed);
                                mVideoViewDelayed.setVisibility(View.VISIBLE);
                                mVideoViewDelayed.start();
                            }
                            if (mVideoViewBefore != null) {
                                mVideoViewBefore.suspend();
                            }
                            if (mVideoViewAfter != null) {
                                mVideoViewAfter.suspend();
                            }
                            if (mVideoViewPro != null) {
                                mVideoViewPro.suspend();
                            }
                            break;
                    }
                }
            }
        });
        ivBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvStep.setOnClickListener(this);
        tvCompare.setOnClickListener(this);
        flStepAi.setOnClickListener(this);

        GoCamera1.setOnClickListener(this);
        GoCamera2.setOnClickListener(this);
        GoCamera3.setOnClickListener(this);
//        GoCamera4.setOnClickListener(this);

        initFragmentViews();
    }

    @Override
    protected void start() {
        //设置主页面视频
        if (mVideoView != null) {
            mVideoView.setVideoId(R.raw.vv_anti_shake);
            mVideoView.setVisibility(View.VISIBLE);
            mVideoView.start();
        }
        if (null != mVideoStep) {
            mVideoStep.setVideoId(R.raw.vv_anti_shake_step);
        }
    }

    @Override
    protected void pause() {
        initFragmentViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //返回上一级
            case R.id.iv_upper_back:
                viewPager.setCurrentItem(0);
                isThreadStep = false;
                clStepAi.setVisibility(View.GONE);
                if (null != mActivity) {
                    mActivity.setArrowsDispaly();
                    mActivity.setTextVISIBLE();
                    mActivity.setTextWhiteColor();
                    mActivity.setIconWhite();
                    mActivity.setProgressViewWhite();
                    mActivity.setArrowsWhite();
                    mActivity.setTitleText(R.string.anti_shake_title);
                    mActivity.setContentText(R.string.anti_shake_content);
                }
                if (mThreadStep != null) {
                    mThreadStep.interrupt();
                    mThreadStep = null;
                }
                setVideoPause(true);
                liButtonOne.setVisibility(View.VISIBLE);
                liButtonTwo.setVisibility(View.GONE);
                viewCompared.setVisibility(View.GONE);
                clStepAll.setVisibility(View.GONE);
                if (mVideoView != null) {
                    mVideoView.seekTo(0);
                    mVideoView.setVisibility(View.VISIBLE);
                    mVideoView.start();
                }
                break;

            //对比步骤切换
            case R.id.bt_intext:
                setSwitchMethods();
                break;

            //步骤
            case R.id.bt_step:
                setNextMethods(true);
                break;

            //对比
            case R.id.bt_compare:
                setNextMethods(false);
                break;

            //点击继续播放步骤视频
            case R.id.fl_ai:
                if (oaStep != null) {
                    oaStep.cancel();
                }
                if (null != mVideoStep) {
                    mVideoStep.start();
                }
                clStepAi.setVisibility(View.GONE);
                liButtonTwo.setVisibility(View.VISIBLE);
                mActivity.setArrowsDispaly();
                mActivity.setTextVISIBLE();
                break;
            case R.id.iv_camera1:
                startActivity(CameraUtil.getVideoStyle2());

                break;
            case R.id.iv_camera2:
            case R.id.iv_camera3:
            case R.id.iv_camera4:
                startActivity(CameraUtil.getVideoStyle());

                break;

            default:
        }
    }

    private boolean isThreadStep;
    private ObjectAnimator oaStep;

    private void openThreadMain() {
        if (mThreadStep != null) {
            mThreadStep.interrupt();
            mThreadStep = null;
        }
        mThreadStep = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (mActivity != null) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isThreadStep && mThreadStep != null && mVideoStep.isPlaying() && mVideoStep.getCurrentPosition() > 2133) {
                                        isThreadStep = true;
                                        mVideoStep.pause();
                                        oaStep = AnimationOppoUtils.setBreatheMethods(ivStepTip);
                                        oaStep.start();

                                        clStepAi.setVisibility(View.VISIBLE);
//                                        liButtonTwo.setVisibility(View.GONE);
                                        mActivity.setArrowsHide();
                                        mActivity.setTextINVISIBLE();
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
        mThreadStep.start();
    }


    /**
     * 对比与步骤切换
     */
    private void setSwitchMethods() {
        isAiStep = !isAiStep;
        if (null != mActivity) {
            clStepAi.setVisibility(View.GONE);
            mActivity.setArrowsDispaly();
            mActivity.setTextVISIBLE();
            if (isAiStep) {
                mActivity.setTextWhiteColor();
                mActivity.setIconWhite();
                mActivity.setProgressViewWhite();
                mActivity.setArrowsWhite();
            } else {
                mActivity.setTextBlackColor();
                mActivity.setIconBlack();
                mActivity.setProgressViewBlack();
                mActivity.setArrowsBlack();
            }
        }
        tvNext.setText(isAiStep ? R.string.bt_tap_compare : R.string.bt_tap_step);
        viewCompared.setVisibility(isAiStep ? View.GONE : View.VISIBLE);
        clStepAll.setVisibility(isAiStep ? View.VISIBLE : View.GONE);

        if (isAiStep) {
            ivBack.setImageResource(R.mipmap.upper_back);
            isThreadStep = false;
            viewPager.setCurrentItem(0);
            setVideoPause(false);
            //步骤视频
            if (null != mVideoStep) {
                mVideoStep.setVisibility(View.VISIBLE);
                mVideoStep.start();
                openThreadMain();
            }
        } else {
            ivBack.setImageResource(R.mipmap.upper_black_back);
            if (oaStep != null) {
                oaStep.cancel();
            }
            if (mThreadStep != null) {
                mThreadStep.interrupt();
                mThreadStep = null;
            }
            if (null != mVideoStep) {
                mVideoStep.seekTo(0);
                mVideoStep.pause();
                mVideoStep.setVisibility(View.INVISIBLE);
            }
            //对比视频
            if (null != mVideoViewBefore) {
                mVideoViewBefore.setVideoId(R.raw.vv_anti_shake_before_compared);
                mVideoViewBefore.setVisibility(View.VISIBLE);
                mVideoViewBefore.start();
            }
        }
    }

    /**
     * 首页跳转到二级页面
     *
     * @param isStep true步骤 false对比
     */
    private boolean isAiStep;

    private void setNextMethods(boolean isStep) {

        isAiStep = isStep;
        if (null != mActivity && !isStep) {
            mActivity.setTextVISIBLE();
            mActivity.setTextBlackColor();
            mActivity.setIconBlack();
            mActivity.setProgressViewBlack();
            mActivity.setArrowsBlack();
        }

        liButtonOne.setVisibility(View.GONE);
        liButtonTwo.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
        viewCompared.setVisibility(isStep ? View.GONE : View.VISIBLE);
        clStepAll.setVisibility(isStep ? View.VISIBLE : View.GONE);
        tvNext.setText(isStep ? R.string.bt_tap_compare : R.string.bt_tap_step);


        if (mVideoView != null) {
            mVideoView.seekTo(0);
            mVideoView.pause();
            mVideoView.setVisibility(View.INVISIBLE);
        }

        if (isStep) {
            ivBack.setImageResource(R.mipmap.upper_back);
            //步骤视频
            if (null != mVideoStep) {
                mVideoStep.setVisibility(View.VISIBLE);
                mVideoStep.start();
                openThreadMain();
            }
        } else {
            ivBack.setImageResource(R.mipmap.upper_black_back);
            //对比视频
            if (null != mVideoViewBefore) {
                mVideoViewBefore.setVideoId(R.raw.vv_anti_shake_before_compared);
                mVideoViewBefore.setVisibility(View.VISIBLE);
                mVideoViewBefore.start();
            }
        }
        mActivity.setTitleText(R.string.anti_shake_title_before);
        mActivity.setContentText(R.string.anti_shake_content_before);
    }


    /**
     * 对比页视频暂停
     */
    private void setVideoPause(boolean isBack) {
        if (mVideoStep != null && isBack) {
            mVideoStep.seekTo(0);
            mVideoStep.pause();
            mVideoStep.setVisibility(View.INVISIBLE);
        }
        if (mVideoViewBefore != null) {
            mVideoViewBefore.seekTo(0);
            mVideoViewBefore.pause();
            mVideoViewBefore.setVisibility(View.INVISIBLE);
        }
        if (mVideoViewAfter != null) {
            mVideoViewAfter.seekTo(0);
            mVideoViewAfter.pause();
            mVideoViewAfter.setVisibility(View.INVISIBLE);
        }
        if (mVideoViewPro != null) {
            mVideoViewPro.seekTo(0);
            mVideoViewPro.pause();
            mVideoViewPro.setVisibility(View.INVISIBLE);
        }
        if (mVideoViewDelayed != null) {
            mVideoViewDelayed.seekTo(0);
            mVideoViewDelayed.pause();
            mVideoViewDelayed.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 初始化状态
     */
    private void initFragmentViews() {
        if (oaStep != null) {
            oaStep.cancel();
        }
        isThreadStep = false;
        isAiStep = false;
        if (mThreadStep != null) {
            mThreadStep.interrupt();
            mThreadStep = null;
        }
        if (mVideoView != null) {
            mVideoView.pause();
            mVideoView.seekTo(0);
            mVideoView.setVisibility(View.INVISIBLE);
        }
        setVideoPause(true);
        if (null != ivBack) {
            ivBack.setImageResource(R.mipmap.upper_black_back);
        }
        if (null != mVideoViewBefore) {
            mVideoViewBefore.suspend();
            mVideoViewBefore.setVisibility(View.INVISIBLE);
        }
        if (null != mVideoViewAfter) {
            mVideoViewAfter.suspend();
            mVideoViewAfter.setVisibility(View.INVISIBLE);
        }
        if (null != mVideoViewPro) {
            mVideoViewPro.suspend();
            mVideoViewPro.setVisibility(View.INVISIBLE);
        }
        if (null != mVideoViewDelayed) {
            mVideoViewDelayed.suspend();
            mVideoViewDelayed.setVisibility(View.INVISIBLE);
        }
        if (null != mVideoStep) {
            mVideoStep.suspend();
            mVideoStep.setVisibility(View.INVISIBLE);
        }
        if (null != liButtonOne) {
            liButtonOne.setVisibility(View.VISIBLE);
        }
        if (null != liButtonTwo) {
            liButtonTwo.setVisibility(View.GONE);
        }
        if (null != clStepAll) {
            clStepAll.setVisibility(View.GONE);
        }
        if (null != clStepAi) {
            clStepAi.setVisibility(View.GONE);
        }
        if (tvNext != null) {
            tvNext.setVisibility(View.VISIBLE);
            tvNext.setText(R.string.bt_tap_compare);
            tvNext.setLayoutParams(new LinearLayout.LayoutParams(buttonWidth, buttonHeight, 0f));
        }
        if (ivBack != null) {
            ivBack.setVisibility(View.GONE);
        }
        if (viewPager != null) {
            viewPager.setCurrentItem(0);
        }
        if (viewCompared != null) {
            viewCompared.setVisibility(View.INVISIBLE);
        }

        if (mActivity != null && mActivity.getCurPosition() == 14) {
            mActivity.setTitleText(R.string.anti_shake_title);
            mActivity.setContentText(R.string.anti_shake_content);
        }
    }

    @Override
    public void releaseVideo() {
        super.releaseVideo();
        if (mVideoViewBefore != null) {
            mVideoViewBefore.suspend();
        }
        if (mVideoViewAfter != null) {
            mVideoViewAfter.suspend();
        }
        if (mVideoViewPro != null) {
            mVideoViewPro.suspend();
        }
        if (mVideoViewDelayed != null) {
            mVideoViewDelayed.suspend();
        }
    }

    @Override
    public void pauseVideo() {
        super.pauseVideo();
        if (mVideoStep != null) {
            mVideoStep.pause();
        }
        if (mVideoViewBefore != null) {
            mVideoViewBefore.pause();
        }
        if (mVideoViewAfter != null) {
            mVideoViewAfter.pause();
        }
        if (mVideoViewPro != null) {
            mVideoViewPro.pause();
        }
        if (mVideoViewDelayed != null) {
            mVideoViewDelayed.seekTo(0);
        }
    }

    @Override
    public void videoRefresh() {
        super.videoRefresh();
        if (mVideoStep != null) {
            mVideoStep.seekTo(0);
        }
        if (mVideoViewBefore != null) {
            mVideoViewBefore.seekTo(0);
        }
        if (mVideoViewAfter != null) {
            mVideoViewAfter.seekTo(0);
        }
        if (mVideoViewPro != null) {
            mVideoViewPro.seekTo(0);
        }
        if (mVideoViewDelayed != null) {
            mVideoViewDelayed.seekTo(0);
        }
    }

    @Override
    protected void destroyFragmentViews() {

        if (mThreadStep != null) {
            mThreadStep.interrupt();
            mThreadStep = null;
        }
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView = null;
        }
        if (mVideoStep != null) {
            mVideoStep.stopPlayback();
            mVideoStep = null;
        }
        if (mVideoViewBefore != null) {
            mVideoViewBefore.stopPlayback();
            mVideoViewBefore = null;
        }
        if (mVideoViewAfter != null) {
            mVideoViewAfter.stopPlayback();
            mVideoViewAfter = null;
        }
        if (mVideoViewPro != null) {
            mVideoViewPro.stopPlayback();
            mVideoViewPro = null;
        }
        if (mVideoViewDelayed != null) {
            mVideoViewDelayed.stopPlayback();
            mVideoViewDelayed = null;
        }
        viewPagerIndicator = null;
        viewPager = null;
        viewCompared = null;
        ivBack = null;
        view1 = null;
        view2 = null;
        view3 = null;
    }
}