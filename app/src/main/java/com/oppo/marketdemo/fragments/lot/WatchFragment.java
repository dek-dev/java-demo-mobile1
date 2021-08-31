package com.oppo.marketdemo.fragments.lot;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.adapter.ImageAdapter;
import com.oppo.marketdemo.adapter.ViewsAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.ViewPagerIndicator;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.ArrayList;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:手表
 */
public class WatchFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private final int WATCH_VIDEO = 0;
    private final int WATCH_ONE = 1;
    private final int WATCH_TWO = 2;
    private final int WATCH_THREE = 3;
    private final int WATCH_FOUR = 4;
    private int watchType = WATCH_VIDEO;
    /**
     * 第二页面
     * 设置现在APP界面类型
     */
    private final int INTELLIGENT_NULL = 0;
    private final int INTELLIGENT_PAY = 1;
    private final int INTELLIGENT_MUSIC = 2;
    private final int INTELLIGENT_IFLY = 3;
    private final int INTELLIGENT_FRUIT = 4;
    private int intelligentType = INTELLIGENT_NULL;
    private ImageAdapter mImageAdapter;
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPager viewPager;
    private View view1, view2, view3, view4;
    private ViewsAdapter mAdapter;
    private TypefaceTextView tvBackAiPage;
    private TextureVideoView mEsiVideo, mEnduranceVideo, mWatchAiVideo, mWearVideo;
    //view1 的控件
    private View viewAi;
    private RadioGroup mRadioGroup;
    private boolean viewPagerIsChange;
    private ImageView mPointColor, mArrowLeft, mArrowRight;
    private ViewPager mCantTouchViewPager;
    private TypefaceTextView mWatchColor;
    private boolean is46 = true;
    private int mCurrentPos = 1;
    private int[] Watch46Pic = {R.mipmap.ic_watch46_black};
    private int[] Watch46Color = {R.string.watch_46_black};
    private int[] Watch46point = {R.mipmap.ic_wtach46_point_black};

//    private int[] Watch46Pic = {R.mipmap.ic_watch46_gold, R.mipmap.ic_watch46_black, R.mipmap.ic_watch46_gold, R.mipmap.ic_watch46_black};
//    private int[] Watch46Color = {R.string.watch_46_black, R.string.watch_46_gold};
//    private int[] Watch46point = {R.mipmap.ic_wtach46_point_black, R.mipmap.ic_wtach46_point_glod};

//    private int[] Watch41Pic = {R.mipmap.ic_watch41_pink, R.mipmap.ic_watch41_black, R.mipmap.ic_watch41_pink, R.mipmap.ic_watch41_black};
//    private int[] Watch41Color = {R.string.watch_41_black, R.string.watch_41_pink,R.string.watch_41_black};
//    private int[] Watch41point = {R.mipmap.ic_wtach41_point_black, R.mipmap.ic_wtach41_point_pink,R.mipmap.ic_wtach41_point_black};

    private int[] Watch41Pic = {R.mipmap.ic_watch41_pink, R.mipmap.ic_watch41_black, R.mipmap.ic_watch41_pink, R.mipmap.ic_watch41_black};
    private int[] Watch41Color = {R.string.watch_41_black, R.string.watch_41_pink};
    private int[] Watch41point = {R.mipmap.ic_wtach41_point_black, R.mipmap.ic_wtach41_point_pink};
    //智能
    private ImageView ivIntelligentPlay, ivIntelligentCool, ivIntelligentIfly, ivIntelligentFruit, ivIntelligentBg;
    private ObjectAnimator watchAiAnimator;
    private boolean isAppPlay;
    private final static int APP_TIMETASK = 1;
    private final static int APP_NEXTTIME = 1000;
    private final static int APP_FIRST = 00;
    //续航
    private ImageView mEnduranceBt;
    private ObjectAnimator EnduranceAnimator;
    private FrameLayout llText;
    //AI表盘
    /**
     * true为进入AI表盘
     */
    private boolean isGOAi;
    private boolean isAiTip;
    private ImageView ivWatchBg, ivWatchOne, ivWatchTwo, ivWatchThree, ivWatchFour, ivWatchCover, ivSecondBlack;
    private FrameLayout flWatchOne;
    private TypefaceTextView tvWatchPlay, tvWatchTip;
    private Handler mHandler;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_watch;
    }

    @Override
    protected void initViews(View mRootView) {
        viewPagerIndicator = mRootView.findViewById(R.id.dot_horizontal_more);
        viewPager = mRootView.findViewById(R.id.view_page);
        viewAi = mRootView.findViewById(R.id.view_ai);

        //AI表盘
        mCantTouchViewPager = mRootView.findViewById(R.id.view_pager);
        mArrowLeft = mRootView.findViewById(R.id.iv_arrows_left);
        mArrowRight = mRootView.findViewById(R.id.iv_arrows_right);
        mRadioGroup = mRootView.findViewById(R.id.radio_group);
        mWatchColor = mRootView.findViewById(R.id.tv_watch_color);
        mPointColor = mRootView.findViewById(R.id.iv_watch_point);
        tvBackAiPage = mRootView.findViewById(R.id.bt_compare_ai_back);
        tvBackAiPage.setOnClickListener(this);
    }

    @Override
    protected void init() {
        mArrowLeft.setVisibility(View.INVISIBLE);
        mArrowRight.setVisibility(View.INVISIBLE);

        setMoreMethods();
        initFragmentViews();
        mCantTouchViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCantTouchViewPager.removeCallbacks(null);
                viewPagerIsChange = true;
                mCurrentPos = position;
                setRecourse(is46, mCurrentPos);
                if (is46) {
                    if (mCurrentPos == 0) {
                        mCurrentPos = 2;
                    }
                    if (mCurrentPos == 3) {
                        mCurrentPos = 1;
                    }
                } else {
                    if (mCurrentPos == 0) {
                        mCurrentPos = 2;
                    }
                    if (mCurrentPos == 3) {
                        mCurrentPos = 1;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE && viewPagerIsChange) {
                    viewPagerIsChange = false;
                    if (mCantTouchViewPager.getCurrentItem() != mCurrentPos) {
                        mCantTouchViewPager.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewPagerIsChange = false;
                            }
                        }, 50);
                        mCantTouchViewPager.setCurrentItem(mCurrentPos, false);
                    }
                }
            }
        });
        mArrowRight.setOnClickListener(this);
        mArrowLeft.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);

        mWatchAiVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (null != mWatchAiVideo) {
//                    if (!isAiTip) {
//                        isAiTip = true;
//                        tvWatchTip.setVisibility(View.VISIBLE);
//                        ivWatchCover.setVisibility(View.VISIBLE);
//                    }
                    mWatchAiVideo.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (null == mWatchAiVideo) {
                                return;
                            }
                            mWatchAiVideo.seekTo(0);
                            mWatchAiVideo.start();
                        }
                    }, 1000);
                }

            }
        });
        mCantTouchViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPagerIsChange = false;
            }
        }, 300);

        /**
         * 多款APP循环
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case APP_TIMETASK:
                        if (intelligentType == INTELLIGENT_FRUIT) {
                            intelligentType = INTELLIGENT_PAY;
                        } else if (intelligentType < INTELLIGENT_FRUIT) {
                            intelligentType = ++intelligentType;
                        }
                        setSecondPageMethods(intelligentType);
                        break;
                }
            }
        };
    }

    /**
     * 布局加入
     */
    private void setMoreMethods() {
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.fragment_more_watch_layout1, null);
        view2 = lf.inflate(R.layout.fragment_more_watch_layout2, null);
        view3 = lf.inflate(R.layout.fragment_more_watch_layout3, null);
        view4 = lf.inflate(R.layout.fragment_more_watch_layout4, null);
        initView1(view1);
        initView2(view2);
        mWearVideo = view2.findViewById(R.id.texture_video_view);
        mEsiVideo = view3.findViewById(R.id.texture_video_view1);
        mEnduranceVideo = view4.findViewById(R.id.texture_video_view2);
        mEnduranceBt = view4.findViewById(R.id.iv_endurance_bt);
        llText = view4.findViewById(R.id.ll_text);
        ArrayList<View> mList = new ArrayList<>();
        mList.add(view1);
        mList.add(view4);
        mList.add(view2);
        mList.add(view3);
        mAdapter = new ViewsAdapter(mList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        viewPagerIndicator.setLength(mList.size());
        viewPagerIndicator.setSelected(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (!isAppPlay) {
                    intelligentType = INTELLIGENT_NULL;
                    setSecondPageMethods(intelligentType);
                }
                viewPagerIndicator.setSelected(position);
                switch (position) {
                    case 0:
                        if (null != mActivity && mActivity.getCurPosition() == 22) {
                            mActivity.setTitleText(R.string.watch_title_ai);
                            mActivity.setContentText(R.string.watch_title_ai_content);
                            mActivity.setTitleRemake(R.string.string_null);
                            if (mWearVideo != null) {
                                mWearVideo.seekTo(0);
                                mWearVideo.pause();
                                mWearVideo.setVisibility(View.INVISIBLE);
                            }
                        }
                        break;
                    case 1:
                        if (null != mActivity) {
                            mActivity.setTitleText(R.string.watch_title_endurance);
                            mActivity.setContentText(R.string.watch_title_endurance_content);
                            mActivity.setTitleRemake(R.string.watch_title_endurance_remake);

                        }
                        if (mEsiVideo != null) {
                            mEsiVideo.seekTo(0);
                            mEsiVideo.pause();
                            mEsiVideo.setVisibility(View.INVISIBLE);
                        }
                        if (mEnduranceVideo != null) {
                            mEnduranceVideo.setVideoId(R.raw.vv_enduranceback);
                            mEnduranceVideo.seekTo(0);
                            mEnduranceVideo.start();
                            mEnduranceVideo.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        if (null != mActivity) {
                            mActivity.setTitleText(R.string.watch_title_full_intelligence);
                            mActivity.setContentText(R.string.watch_title_full_intelligence_content);
                            mActivity.setTitleRemake(R.string.string_null);

                        }
                        if (mEsiVideo != null) {
                            mEsiVideo.seekTo(0);
                            mEsiVideo.pause();
                            mEsiVideo.setVisibility(View.INVISIBLE);
                        }
                        if (mWearVideo != null) {
                            if (!isAppPlay) {
                                ivSecondBlack.setVisibility(View.VISIBLE);
                                intelligentType = INTELLIGENT_PAY;
                                ivIntelligentPlay.setTranslationX(-45);
                                mWearVideo.setVideoId(R.raw.vv_weate_second);
                                mWearVideo.seekTo(0);
                                mWearVideo.setVisibility(View.VISIBLE);
                                mWearVideo.start();
                            }
                            if (isAppPlay && intelligentType == INTELLIGENT_PAY) {
                                mWearVideo.setVisibility(View.VISIBLE);
                                mWearVideo.start();
                            }
                        }
                        break;
                    case 3:

                        if (null != mActivity) {
                            mActivity.setTitleText(R.string.watch_title_esim);
                            mActivity.setContentText(R.string.watch_title_esim_content);
                            mActivity.setTitleRemake(R.string.string_null);
                        }
                        if (mEnduranceVideo != null) {
                            mEnduranceVideo.seekTo(0);
                            mEnduranceVideo.pause();
                            mEnduranceVideo.setVisibility(View.INVISIBLE);
                        }
                        if (mWearVideo != null) {
                            mWearVideo.seekTo(0);
                            mWearVideo.pause();
                            mWearVideo.setVisibility(View.INVISIBLE);
                        }
                        if (mEsiVideo != null) {
                            mEsiVideo.setVideoId(R.raw.vv_esim);
                            mEsiVideo.seekTo(0);
                            mEsiVideo.start();
                            mEsiVideo.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
        });

        mEsiVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (null != mEsiVideo) {
                    mEsiVideo.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (null == mEsiVideo) {
                                return;
                            }
                            mEsiVideo.seekTo(0);
                            mEsiVideo.start();
                        }
                    }, 1000);
                }
            }
        });
        mEnduranceVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mEnduranceVideo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mEnduranceVideo != null && mEnduranceVideo.getVisibility() == View.VISIBLE) {
                            mEnduranceVideo.seekTo(0);
                            mEnduranceVideo.start();
                        }
                    }
                }, 1000);
            }
        });
        mWearVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                delayMillis = (isAppPlay ? 1000 : 0);
                mWearVideo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isAppPlay) {
                            if (mWearVideo != null && mWearVideo.getVisibility() == View.VISIBLE) {
                                mWearVideo.seekTo(0);
                                mWearVideo.start();
                            }
                        } else {
                            if (null != mHandler) {
                                mWearVideo.pause();
                                mWearVideo.seekTo(0);
                                mWearVideo.setVisibility(View.INVISIBLE);
                                ivSecondBlack.setVisibility(View.GONE);
                                mHandler.removeMessages(APP_TIMETASK);
                                mHandler.sendEmptyMessageDelayed(APP_TIMETASK, APP_FIRST);
                            }
                        }
                    }
                }, delayMillis);
            }
        });
        mEnduranceBt.setOnClickListener(this);
    }

    private long delayMillis = 0;

    @SuppressLint("ClickableViewAccessibility")
    private void initView1(View view) {
        mWatchAiVideo = view.findViewById(R.id.texture_video_view3);
        ivWatchBg = view.findViewById(R.id.iv_watch_ai_bg);
        tvWatchPlay = view.findViewById(R.id.watch_ai_bt_video);
        flWatchOne = view.findViewById(R.id.fl_watch_one);
        ivWatchOne = view.findViewById(R.id.watch_ai_bt_one);
        ivWatchTwo = view.findViewById(R.id.watch_ai_bt_two);
        ivWatchThree = view.findViewById(R.id.watch_ai_bt_three);
        ivWatchFour = view.findViewById(R.id.watch_ai_bt_four);
        ivWatchCover = view.findViewById(R.id.watch_ai_bt_cover);
        tvWatchTip = view.findViewById(R.id.tv_watch_tip);

        tvWatchPlay.setOnClickListener(this);
        flWatchOne.setOnClickListener(this);
        ivWatchTwo.setOnClickListener(this);
        ivWatchThree.setOnClickListener(this);
        ivWatchFour.setOnClickListener(this);
    }

    /**
     * 智能手表
     *
     * @param view
     */
    private void initView2(View view) {
        ivSecondBlack = view.findViewById(R.id.iv_black);
        ivIntelligentBg = view.findViewById(R.id.iv_intelligent_bg);
        ivIntelligentPlay = view.findViewById(R.id.iv_watch_pay);
        ivIntelligentCool = view.findViewById(R.id.iv_watch_cool);
        ivIntelligentIfly = view.findViewById(R.id.iv_watch_ifly);
        ivIntelligentFruit = view.findViewById(R.id.iv_watch_fruit);
        ivIntelligentPlay.setOnClickListener(this);
        ivIntelligentCool.setOnClickListener(this);
        ivIntelligentIfly.setOnClickListener(this);
        ivIntelligentFruit.setOnClickListener(this);
    }


    @Override
    protected void start() {
        if (mEsiVideo != null) {
            mEsiVideo.setVideoId(R.raw.vv_esim);
        }
    }

    /**
     * 设置进入AI表盘
     */
    private void setWatchAiMethods() {
        watchType = WATCH_VIDEO;
        viewAi.setVisibility(isGOAi ? View.GONE : View.VISIBLE);
        viewPager.setVisibility(isGOAi ? View.VISIBLE : View.INVISIBLE);
        ivWatchBg.setVisibility(View.GONE);
        if (!isAiTip) {
            isAiTip = true;
            tvWatchTip.setVisibility(View.VISIBLE);
            ivWatchCover.setVisibility(View.VISIBLE);
        }
        if (isGOAi) {
            if (null != mActivity) {
                mActivity.setTitleText(R.string.watch_title_ai);
                mActivity.setContentText(R.string.watch_title_ai_content);
                mActivity.setTitleRemake(R.string.string_null);
            }
            watchAiAnimator = AnimationOppoUtils.setWatchAiAnimation(tvWatchTip, true);
            watchAiAnimator.start();
        } else {
            if (null != watchAiAnimator) {
                watchAiAnimator.cancel();
                watchAiAnimator = null;
            }
            if (null != mActivity) {
                mActivity.setTitleText(R.string.watch_title);
                mActivity.setContentText(R.string.watch_content);
                mActivity.setTitleRemake(R.string.watch_remake);
            }
        }
        ivWatchOne.setAlpha(1f);
        ivWatchTwo.setAlpha(1f);
        ivWatchThree.setAlpha(1f);
        ivWatchFour.setAlpha(1f);
        if (isGOAi) {
            if (mWatchAiVideo != null) {
                mWatchAiVideo.setVideoId(R.raw.vv_watch_ai);
                mWatchAiVideo.seekTo(0);
                mWatchAiVideo.start();
                mWatchAiVideo.setVisibility(View.VISIBLE);
            }
        } else {
            if (mWatchAiVideo != null) {
                mWatchAiVideo.seekTo(0);
                mWatchAiVideo.pause();
                mWatchAiVideo.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 第二页面
     * 点击更换界面
     */
    private void setSecondPageMethods(int type) {
        ImageView ivNow = null;
        int intBg = 0;
        ivIntelligentPlay.setTranslationX(0);
        ivIntelligentCool.setTranslationX(0);
        ivIntelligentIfly.setTranslationX(0);
        ivIntelligentFruit.setTranslationX(0);
        switch (type) {
            case INTELLIGENT_PAY:
                ivNow = ivIntelligentPlay;
                intBg = R.mipmap.wate_cool_main_bg;
                break;
            case INTELLIGENT_MUSIC:
                ivNow = ivIntelligentCool;
                intBg = R.mipmap.wate_cool_main_bg;
                break;
            case INTELLIGENT_IFLY:
                ivNow = ivIntelligentIfly;
                intBg = R.mipmap.wate_ifly_main_bg;
                break;
            case INTELLIGENT_FRUIT:
                ivNow = ivIntelligentFruit;
                intBg = R.mipmap.wate_fruit_main_bg;
                break;
            default:
        }
        if (null != ivNow && intelligentType != INTELLIGENT_PAY && mWearVideo.getVisibility() == View.VISIBLE) {
            ivSecondBlack.setVisibility(View.GONE);
            mWearVideo.pause();
            mWearVideo.seekTo(0);
            mWearVideo.setVisibility(View.INVISIBLE);
        }
        if (null != ivNow && intBg != 0) {
            ivNow.setTranslationX(-45);
            ivIntelligentBg.setImageResource(intBg);
        }
        if (type == INTELLIGENT_PAY) {
            ivSecondBlack.setVisibility(View.VISIBLE);
            mHandler.removeMessages(APP_TIMETASK);
            mWearVideo.seekTo(0);
            mWearVideo.setVisibility(View.VISIBLE);
            mWearVideo.start();
            return;
        }
        if (null != mHandler && type != INTELLIGENT_NULL && !isAppPlay) {
            mHandler.removeMessages(APP_TIMETASK);
            mHandler.sendEmptyMessageDelayed(APP_TIMETASK, APP_NEXTTIME);
        }
    }

    private boolean isNext;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //搜索原理或返回穿搭
            case R.id.bt_compare_ai_back:
                if (null == viewPager) {
                    return;
                }
                isNext = !isNext;
                tvBackAiPage.setText(isNext ? R.string.bt_tap_back : R.string.bt_tap_more);
                viewPager.setVisibility(isNext ? View.VISIBLE : View.GONE);
                mActivity.setTitleText(isNext ? R.string.watch_title_ai : R.string.watch_title);
                mActivity.setContentText(isNext ? R.string.watch_title_ai_content : R.string.watch_content);
                mActivity.setTitleRemake(isNext ? R.string.string_null: R.string.watch_remake);
                if (isNext) {
                    isGOAi = true;
                    isAiTip = false;
                } else {
                    viewPager.setCurrentItem(0);
                    isGOAi = false;
                }
                setWatchAiMethods();
                break;


            //AI表盘视频
            case R.id.watch_ai_bt_video:
                if (watchType == WATCH_VIDEO) {
                    return;
                }
                if (null != watchAiAnimator) {
                    watchAiAnimator.cancel();
                    watchAiAnimator = null;
                }
                watchType = WATCH_VIDEO;
                ivWatchBg.setVisibility(View.GONE);
                ivWatchOne.setAlpha(1f);
                ivWatchTwo.setAlpha(1f);
                ivWatchThree.setAlpha(1f);
                ivWatchFour.setAlpha(1f);
                if (mWatchAiVideo != null) {
                    mWatchAiVideo.setVideoId(R.raw.vv_watch_ai);
                    mWatchAiVideo.seekTo(0);
                    mWatchAiVideo.start();
                    mWatchAiVideo.setVisibility(View.VISIBLE);
                }
                break;

            //支付
            case R.id.iv_watch_pay:
                if (intelligentType == INTELLIGENT_PAY) {
                    return;
                }
                if (null != mHandler) {
                    isAppPlay = true;
                    mHandler.removeMessages(APP_TIMETASK);
                }
                intelligentType = INTELLIGENT_PAY;
                setSecondPageMethods(intelligentType);
                break;
            //音乐
            case R.id.iv_watch_cool:
                if (intelligentType == INTELLIGENT_MUSIC) {
                    return;
                }
                if (null != mHandler) {
                    isAppPlay = true;
                    mHandler.removeMessages(APP_TIMETASK);
                }
                intelligentType = INTELLIGENT_MUSIC;
                setSecondPageMethods(intelligentType);
                break;
            //ifly
            case R.id.iv_watch_ifly:
                if (intelligentType == INTELLIGENT_IFLY) {
                    return;
                }
                if (null != mHandler) {
                    isAppPlay = true;
                    mHandler.removeMessages(APP_TIMETASK);
                }
                intelligentType = INTELLIGENT_IFLY;
                setSecondPageMethods(intelligentType);
                break;
            //柚子
            case R.id.iv_watch_fruit:
                if (intelligentType == INTELLIGENT_FRUIT) {
                    return;
                }
                if (null != mHandler) {
                    isAppPlay = true;
                    mHandler.removeMessages(APP_TIMETASK);
                }
                intelligentType = INTELLIGENT_FRUIT;
                setSecondPageMethods(intelligentType);
                break;

            //第一个表盘
            case R.id.fl_watch_one:
                if (watchType == WATCH_ONE) {
                    return;
                }
                if (null != watchAiAnimator) {
                    watchAiAnimator.cancel();
                    watchAiAnimator = null;
                }
                watchType = WATCH_ONE;
                isAiTip = true;
                if (mWatchAiVideo != null) {
                    mWatchAiVideo.seekTo(0);
                    mWatchAiVideo.pause();
                    mWatchAiVideo.setVisibility(View.INVISIBLE);
                }
                ivWatchCover.setVisibility(View.GONE);
                tvWatchTip.setVisibility(View.GONE);
                ivWatchBg.setVisibility(View.VISIBLE);
                ivWatchBg.setImageResource(R.mipmap.watch_ai_bg_one);
                ivWatchOne.setAlpha(1f);
                ivWatchTwo.setAlpha(0.5f);
                ivWatchThree.setAlpha(0.5f);
                ivWatchFour.setAlpha(0.5f);

                break;
            //第二个表盘
            case R.id.watch_ai_bt_two:
                if (watchType == WATCH_TWO) {
                    return;
                }
                if (null != watchAiAnimator) {
                    watchAiAnimator.cancel();
                    watchAiAnimator = null;
                }
                watchType = WATCH_TWO;
                isAiTip = true;
                if (mWatchAiVideo != null) {
                    mWatchAiVideo.seekTo(0);
                    mWatchAiVideo.pause();
                    mWatchAiVideo.setVisibility(View.INVISIBLE);
                }
                ivWatchCover.setVisibility(View.GONE);
                tvWatchTip.setVisibility(View.GONE);
                ivWatchBg.setVisibility(View.VISIBLE);
                ivWatchBg.setImageResource(R.mipmap.watch_ai_bg_two);
                ivWatchOne.setAlpha(0.5f);
                ivWatchTwo.setAlpha(1f);
                ivWatchThree.setAlpha(0.5f);
                ivWatchFour.setAlpha(0.5f);
                break;
            //第三个表盘
            case R.id.watch_ai_bt_three:
                if (watchType == WATCH_THREE) {
                    return;
                }
                if (null != watchAiAnimator) {
                    watchAiAnimator.cancel();
                    watchAiAnimator = null;
                }
                watchType = WATCH_THREE;
                isAiTip = true;
                if (mWatchAiVideo != null) {
                    mWatchAiVideo.seekTo(0);
                    mWatchAiVideo.pause();
                    mWatchAiVideo.setVisibility(View.INVISIBLE);
                }
                ivWatchCover.setVisibility(View.GONE);
                tvWatchTip.setVisibility(View.GONE);
                ivWatchBg.setVisibility(View.VISIBLE);
                ivWatchBg.setImageResource(R.mipmap.watch_ai_bg_three);
                ivWatchOne.setAlpha(0.5f);
                ivWatchTwo.setAlpha(0.5f);
                ivWatchThree.setAlpha(1f);
                ivWatchFour.setAlpha(0.5f);
                break;
            //第四个表盘
            case R.id.watch_ai_bt_four:
                if (watchType == WATCH_FOUR) {
                    return;
                }
                if (null != watchAiAnimator) {
                    watchAiAnimator.cancel();
                    watchAiAnimator = null;
                }
                watchType = WATCH_FOUR;
                isAiTip = true;
                if (mWatchAiVideo != null) {
                    mWatchAiVideo.seekTo(0);
                    mWatchAiVideo.pause();
                    mWatchAiVideo.setVisibility(View.INVISIBLE);
                }
                ivWatchCover.setVisibility(View.GONE);
                tvWatchTip.setVisibility(View.GONE);
                ivWatchBg.setVisibility(View.VISIBLE);
                ivWatchBg.setImageResource(R.mipmap.watch_ai_bg_four);
                ivWatchOne.setAlpha(0.5f);
                ivWatchTwo.setAlpha(0.5f);
                ivWatchThree.setAlpha(0.5f);
                ivWatchFour.setAlpha(1f);
                break;
            //点击切换手表(左)
            case R.id.iv_arrows_left:
                if (viewPagerIsChange) {
                    return;
                }
                mCurrentPos--;
                setRecourse(is46, mCurrentPos);
                break;
            //点击切换手表（右）
            case R.id.iv_arrows_right:
                if (viewPagerIsChange) {
                    return;
                }
                mCurrentPos++;
                setRecourse(is46, mCurrentPos);
                break;
            default:
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_1:
                is46 = false;
                mCurrentPos = 1;
                mImageAdapter = new ImageAdapter(mActivity, Watch41Pic);
                mCantTouchViewPager.setAdapter(mImageAdapter);
                setRecourse(is46, mCurrentPos);
                mCantTouchViewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPagerIsChange = false;
                    }
                }, 100);
                break;
            case R.id.radio_button_2:
                is46 = true;
                mCurrentPos = 1;
                mImageAdapter = new ImageAdapter(mActivity, Watch46Pic);
                mCantTouchViewPager.setAdapter(mImageAdapter);
                setRecourse(is46, mCurrentPos);
                mCantTouchViewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPagerIsChange = false;
                    }
                }, 100);
                break;

            default:
        }
    }

//    private int[] Watch41Pic = {R.mipmap.ic_watch41_pink, R.mipmap.ic_watch41_black, R.mipmap.ic_watch41_pink, R.mipmap.ic_watch41_black};
//    private int[] Watch41Color = {R.string.watch_41_black, R.string.watch_41_pink};
//    private int[] Watch41point = {R.mipmap.ic_wtach41_point_black, R.mipmap.ic_wtach41_point_pink};

    private void setRecourse(boolean is46, int mCurrentPos) {
        if (mCantTouchViewPager != null) {
            mCantTouchViewPager.setCurrentItem(is46 ? mCurrentPos % 4 : mCurrentPos % 4);
        }
        
        int i = mCurrentPos == 0 ? 1 : mCurrentPos == 3 ? 0 : mCurrentPos - 1;
        int i1 = mCurrentPos == 0 ? 1 : mCurrentPos == 3 ? 0 : mCurrentPos - 1;

//        int i = mCurrentPos == 0 ? 1 : mCurrentPos == 3 ? 0 : mCurrentPos - 1;
//        int i1 = mCurrentPos == 0 ? 2 : mCurrentPos == 2 ? 1 : mCurrentPos - 1;

        Log.d("insidea_i",String.valueOf(i));
        Log.d("insidea_i1",String.valueOf(i1));

        if (mPointColor != null) {
            mPointColor.setImageResource(is46 ? Watch46point[i] : Watch41point[i1]);
        }
        if (mWatchColor != null) {
            mWatchColor.setText(is46 ? Watch46Color[i] : Watch41Color[i1]);
        }
    }

    private void initFragmentViews() {
        //重置AI表
        isGOAi = false;
        isAiTip = false;
        isNext = false;
        if (null != tvBackAiPage) {
            tvBackAiPage.setText(R.string.bt_tap_more);
        }
        if (null != viewAi) {
            viewAi.setVisibility(View.VISIBLE);
        }

        //重置多款APP
        isAppPlay = false;
        intelligentType = INTELLIGENT_NULL;
        setSecondPageMethods(intelligentType);

        if (null != ivSecondBlack) {
            ivSecondBlack.setVisibility(View.VISIBLE);
        }
        if (null != watchAiAnimator) {
            watchAiAnimator.cancel();
            watchAiAnimator = null;
        }
        if (null != EnduranceAnimator) {
            EnduranceAnimator.cancel();
            EnduranceAnimator = null;
        }
        if (viewPager != null) {
            viewPager.setVisibility(View.GONE);
            viewPager.setCurrentItem(0);
        }
        if (mEnduranceVideo != null) {
            mEnduranceVideo.seekTo(0);
            mEnduranceVideo.pause();
            mEnduranceVideo.setVisibility(View.INVISIBLE);
        }
        if (mWatchAiVideo != null) {
            mWatchAiVideo.seekTo(0);
            mWatchAiVideo.pause();
            mWatchAiVideo.setVisibility(View.INVISIBLE);
        }
        if (mEsiVideo != null) {
            mEsiVideo.seekTo(0);
            mEsiVideo.pause();
            mEsiVideo.setVisibility(View.INVISIBLE);
        }
        if (mWearVideo != null) {
            mWearVideo.seekTo(0);
            mWearVideo.pause();
            mWearVideo.setVisibility(View.INVISIBLE);
        }
        is46 = true;
        mCurrentPos = 1;
        if (mRadioGroup != null) {
            mRadioGroup.check(R.id.radio_button_2);
        }
        if (mCantTouchViewPager != null && mActivity != null) {
            mImageAdapter = new ImageAdapter(mActivity, Watch46Pic);
            mCantTouchViewPager.setAdapter(mImageAdapter);
        }
        setRecourse(is46, mCurrentPos);
        viewPagerIsChange = false;
        if (null != mActivity && mActivity.getCurPosition() == 22) {
            mActivity.setTitleText(R.string.watch_title);
            mActivity.setContentText(R.string.watch_content);
            mActivity.setTitleRemake(R.string.watch_remake);

        }
    }

    @Override
    protected void pause() {
        initFragmentViews();
    }

    @Override
    public void releaseVideo() {
        super.releaseVideo();
        if (mEsiVideo != null) {
            mEsiVideo.suspend();
        }
        if (mEnduranceVideo != null) {
            mEnduranceVideo.suspend();
        }
        if (mWatchAiVideo != null) {
            mWatchAiVideo.suspend();
        }
        if (mWearVideo != null) {
            mWearVideo.suspend();
        }
    }

    @Override
    public void pauseVideo() {
        super.pauseVideo();
        if (mEsiVideo != null) {
            mEsiVideo.pause();
        }
        if (mWearVideo != null) {
            mWearVideo.pause();
        }
        if (mEnduranceVideo != null) {
            mEnduranceVideo.pause();
        }
        if (mWatchAiVideo != null) {
            mWatchAiVideo.pause();
        }
    }

    @Override
    public void videoRefresh() {
        super.videoRefresh();
        if (mEsiVideo != null) {
            mEsiVideo.seekTo(0);
        }
        if (mWearVideo != null) {
            mWearVideo.seekTo(0);
        }
        if (mWatchAiVideo != null) {
            mWatchAiVideo.seekTo(0);
        }
        if (mEnduranceVideo != null) {
            mEnduranceVideo.seekTo(0);
        }
    }

    @Override
    protected void destroyFragmentViews() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (null != watchAiAnimator) {
            watchAiAnimator.cancel();
            watchAiAnimator = null;
        }
        if (null != EnduranceAnimator) {
            EnduranceAnimator.cancel();
            EnduranceAnimator = null;
        }
        viewPagerIndicator = null;
        viewPager = null;
        view1 = null;
        view2 = null;
        view3 = null;
        view4 = null;
        mPointColor = null;
        mArrowLeft = null;
        mArrowRight = null;
    }
}