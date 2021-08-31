package com.oppo.marketdemo.fragments.performance;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.adapter.ViewsAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.ViewPagerIndicator;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.ArrayList;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 *
 * @author Neo
 * Date: 2020/4/7 10:50
 * Description:超级闪充
 */
public class SuperVoocFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private int typeNum = NULL_TYPE;
    /**
     * typeNum
     * 主页面
     */
    private static final int NULL_TYPE = 0;
    /**
     * typeNum
     * 进入对比页面
     */
    private static final int COMPARE_TYPE = 1;
    /**
     * typeNum
     * 进入探索页面
     */
    private static final int MORE_TYPE = 2;
    /**
     * 设置字体与进度条颜色
     */
    private final String IS_THEME_WHITE = "is_theme_white";
    private final String IS_THEME_BLACK = "is_theme_black";

    private TextureVideoView mVideoViewMain, mVideoCompare;
    private TypefaceTextView btCompare, btMore, btIntext;
    private View viewCompare;
    private ConstraintLayout viewMore;
    private RadioGroup radioGroup;
    private ImageView ivCompareBg, ivUpperBack;
    private LinearLayout llOne, llTwo, llLeftText, llRightText;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_super_vooc;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideoViewMain = mRootView.findViewById(R.id.texture_video_view);
        btCompare = mRootView.findViewById(R.id.bt_compare);
        btMore = mRootView.findViewById(R.id.bt_more);
        btIntext = mRootView.findViewById(R.id.bt_intext);
        ivUpperBack = mRootView.findViewById(R.id.iv_upper_back);
        llOne = mRootView.findViewById(R.id.ll_button_one);
        llTwo = mRootView.findViewById(R.id.ll_button_two);


        //查看对比
        viewCompare = mRootView.findViewById(R.id.include_compare);
        mVideoCompare = mRootView.findViewById(R.id.texture_video_view1);
        radioGroup = mRootView.findViewById(R.id.radio_group);
        ivCompareBg = mRootView.findViewById(R.id.iv_compare);
        llLeftText = mRootView.findViewById(R.id.ll_vooc_text_left);
        llRightText = mRootView.findViewById(R.id.ll_vooc_text_right);

        //探索更多
        viewMore = mRootView.findViewById(R.id.include_more);

    }

    @Override
    protected void init() {
        mVideoViewMain.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoViewMain.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideoViewMain != null && mVideoViewMain.getVisibility() == View.VISIBLE) {
                            mVideoViewMain.seekTo(0);
                            mVideoViewMain.start();
                        }
                    }
                }, 1000);
            }
        });
        radioGroup.setOnCheckedChangeListener(this);
        btCompare.setOnClickListener(this);
        btMore.setOnClickListener(this);
        ivUpperBack.setOnClickListener(this);
        btIntext.setOnClickListener(this);

    }

    @Override
    protected void start() {
        startThread();
    }


    private void startThread() {
        //设置主页面视频
        if (mVideoViewMain != null) {
            mVideoViewMain.setVideoId(R.raw.vv_super_vooc_main);
            mVideoViewMain.setVisibility(View.VISIBLE);
            mVideoViewMain.start();
        }
        //设置对比第一个视频
        if (null != mVideoCompare) {
            mVideoCompare.setVideoId(R.raw.vv_vooc_5min);
        }

    }

    @Override
    protected void pause() {
        initFragmentViews();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
        if (null != llLeftText) {
            llLeftText.setVisibility(View.GONE);
        }
        if (null != llRightText) {
            llRightText.setVisibility(View.GONE);
        }
        switch (checkedId) {
            case R.id.radio_button_1:
                ivCompareBg.setImageResource(R.mipmap.vooc_5min_bg);
                if (mVideoCompare != null) {
                    mVideoCompare.setVisibility(View.VISIBLE);
                    mVideoCompare.seekTo(0);
                    mVideoCompare.setVideoId(R.raw.vv_vooc_5min);
                    mVideoCompare.start();
                }
                break;

            case R.id.radio_button_2:
                if (mVideoCompare != null) {
                    mVideoCompare.seekTo(0);
                    mVideoCompare.setVideoId(R.raw.vv_vooc_15min);
                    mVideoCompare.start();
                }

                break;

            case R.id.radio_button_3:
                if (mVideoCompare != null) {
                    mVideoCompare.seekTo(0);
                    mVideoCompare.setVideoId(R.raw.vv_vooc_30min);
                    mVideoCompare.start();
                }
                openThread();
                break;
            default:
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //返回上一级
            case R.id.iv_upper_back:
                typeNum = NULL_TYPE;
                setBackMainMethods();
                setTitleIconMethods(IS_THEME_BLACK, R.string.super_vooc_title, R.string.super_vooc_content);
                break;

            //查看对比或探索更多
            case R.id.bt_intext:
                setMoreOrCompare(typeNum);
                break;

            //查看对比
            case R.id.bt_compare:
                typeNum = COMPARE_TYPE;
                setFirstMethods(typeNum);
                setTitleIconMethods(IS_THEME_BLACK, R.string.super_vooc_title, R.string.super_vooc_contentc_compare);
                break;

            //探索更多
            case R.id.bt_more:
                typeNum = MORE_TYPE;
                setFirstMethods(typeNum);
                setTitleIconMethods(IS_THEME_BLACK, R.string.super_vooc_title2, R.string.super_vooc_book);
                break;
            default:
        }
    }

    private Thread mThread;
    private boolean isFirstDisplay, isSecondDisplay;
    private ObjectAnimator objectAnimator;

    private void openThread() {
        isFirstDisplay = false;
        isSecondDisplay = false;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (mActivity != null) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mVideoCompare != null && mVideoCompare.isPlaying() && null != mActivity) {
                                        if (mVideoCompare.getCurrentPosition() > 4500 && !isFirstDisplay) {
                                            isFirstDisplay = true;
                                            objectAnimator = AnimationOppoUtils.setVoocMethods(llLeftText);
                                            objectAnimator.start();
                                        }
                                        if (mVideoCompare.getCurrentPosition() > 7500 && !isSecondDisplay) {
                                            if (objectAnimator != null) {
                                                objectAnimator.cancel();
                                            }
                                            isSecondDisplay = true;
                                            objectAnimator = AnimationOppoUtils.setVoocMethods(llRightText);
                                            objectAnimator.start();
                                        }
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
        mThread.start();
    }

    /**
     * 设置第一次点击对比或探索按钮
     */
    private void setFirstMethods(int typeNum) {
        if (mVideoViewMain != null) {
            mVideoViewMain.pause();
            mVideoViewMain.seekTo(0);
            mVideoViewMain.setVisibility(View.INVISIBLE);
        }
        llOne.setVisibility(View.GONE);
        llTwo.setVisibility(View.VISIBLE);
        ivUpperBack.setVisibility(View.VISIBLE);

        mActivity.setTextBlackColor();
        mActivity.setIconBlack();

        ivUpperBack.setImageResource(R.mipmap.upper_black_back);
        viewCompare.setVisibility(typeNum == COMPARE_TYPE ? View.VISIBLE : View.INVISIBLE);
        viewMore.setVisibility(typeNum == COMPARE_TYPE ? View.GONE : View.VISIBLE);
        btIntext.setText(typeNum == COMPARE_TYPE ? R.string.bt_tap_more : R.string.bt_tap_compare);
        //对比页面时要播放视频
        if (typeNum == COMPARE_TYPE) {
            mVideoCompare.setVisibility(View.VISIBLE);
            mVideoCompare.start();
        }
    }

    /**
     * 点击二级页面返回到主页面
     */
    private void setBackMainMethods() {
        if (mActivity != null) {
            mActivity.setTextWhiteColor();
            mActivity.setIconWhite();
        }

        if (mVideoCompare != null) {
            mVideoCompare.seekTo(0);
            mVideoCompare.pause();
        }

        llOne.setVisibility(View.VISIBLE);
        llTwo.setVisibility(View.GONE);
        viewCompare.setVisibility(View.INVISIBLE);
        viewMore.setVisibility(View.GONE);
        radioGroup.check(R.id.radio_button_1);

        mVideoViewMain.setVisibility(View.VISIBLE);
        mVideoViewMain.start();
    }

    /**
     * 设置更多与对比间的切换
     */
    private void setMoreOrCompare(int typeInt) {
        typeNum = (typeInt == COMPARE_TYPE ? MORE_TYPE : COMPARE_TYPE);
        btIntext.setText(typeNum == MORE_TYPE ? R.string.bt_tap_compare : R.string.bt_tap_more);

        viewCompare.setVisibility(typeNum == MORE_TYPE ? View.INVISIBLE : View.VISIBLE);
        viewMore.setVisibility(typeNum == MORE_TYPE ? View.VISIBLE : View.GONE);


        if (typeNum == MORE_TYPE) {
            ivCompareBg.setImageResource(R.mipmap.vooc_5min_bg);

            if (mVideoCompare != null) {
                mVideoCompare.seekTo(0);
                mVideoCompare.pause();
            }

            radioGroup.check(R.id.radio_button_1);
            setTitleIconMethods(IS_THEME_WHITE, R.string.super_vooc_title2, R.string.super_vooc_book);
        } else {
            mVideoCompare.setVisibility(View.VISIBLE);
            ivCompareBg.setImageResource(R.mipmap.vooc_5min_bg);
            if (mVideoCompare != null) {
                mVideoCompare.seekTo(0);
                mVideoCompare.start();
                mVideoCompare.setVisibility(View.VISIBLE);

            }
            setTitleIconMethods(IS_THEME_BLACK, R.string.super_vooc_title, R.string.super_vooc_contentc_compare);
        }

    }


    /**
     * 设置字体与进度条颜色
     *
     * @param themeType
     * @param titleId
     * @param contentId
     */
    private void setTitleIconMethods(String themeType, int titleId, int contentId) {
        if (null == mActivity || mActivity.getCurPosition() != 5) {
            return;
        }
        mActivity.setTitleText(titleId);
        mActivity.setContentText(contentId);
    }

    /**
     * 初始化状态
     */
    private void initFragmentViews() {
        typeNum = NULL_TYPE;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        //查看对比
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
        if (null != llLeftText) {
            llLeftText.setVisibility(View.GONE);
        }
        if (null != llRightText) {
            llRightText.setVisibility(View.GONE);
        }
        if (null != viewCompare) {
            viewCompare.setVisibility(View.INVISIBLE);
        }
        if (null != ivCompareBg) {
            ivCompareBg.setImageResource(R.mipmap.vooc_5min_bg);
        }
        if (null != radioGroup) {
            radioGroup.setVisibility(View.VISIBLE);
            radioGroup.check(R.id.radio_button_1);
        }
        if (mVideoCompare != null) {
            mVideoCompare.seekTo(0);
            mVideoCompare.pause();
            mVideoCompare.suspend();
            mVideoCompare.setVisibility(View.INVISIBLE);
        }

        //探索更多
        if (null != viewMore) {
            viewMore.setVisibility(View.GONE);
        }


        //主页面设置
        if (null != llOne) {
            llOne.setVisibility(View.VISIBLE);
        }
        if (null != llTwo) {
            llTwo.setVisibility(View.GONE);
        }
        if (mVideoViewMain != null) {
            mVideoViewMain.seekTo(0);
            mVideoViewMain.pause();
            mVideoViewMain.setVisibility(View.INVISIBLE);
        }
        setTitleIconMethods(IS_THEME_BLACK, R.string.super_vooc_title, R.string.super_vooc_content);

    }

    @Override
    protected void destroyFragmentViews() {
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
        if (mVideoViewMain != null) {
            mVideoViewMain.stopPlayback();
            mVideoViewMain = null;
        }
        if (mVideoCompare != null) {
            mVideoCompare.stopPlayback();
            mVideoCompare = null;
        }

        btCompare = null;
        btMore = null;
        viewMore = null;

    }

    @Override
    public void releaseVideo() {
        super.releaseVideo();
        if (mVideoViewMain != null) {
            mVideoViewMain.suspend();
        }
        if (mVideoCompare != null) {
            mVideoCompare.suspend();
        }

    }

    @Override
    public void pauseVideo() {
        super.pauseVideo();
        if (mVideoViewMain != null) {
            mVideoViewMain.pause();
        }
        if (mVideoCompare != null) {
            mVideoCompare.pause();
        }


    }

    @Override
    public void videoRefresh() {
        super.videoRefresh();
        if (mVideoViewMain != null) {
            mVideoViewMain.seekTo(0);
        }
        if (mVideoCompare != null) {
            mVideoCompare.seekTo(0);
        }


    }
}