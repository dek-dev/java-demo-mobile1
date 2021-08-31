package com.oppo.marketdemo.fragments.exterior;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.viewpager.widget.ViewPager;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.adapter.ImageAdapter;
import com.oppo.marketdemo.adapter.ViewsAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;
import com.oppo.marketdemo.utils.ValueAnimUtil;

import java.util.ArrayList;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:颜色
 */
public class PhoneColorFragment extends BaseFragment {
    private ViewPager mViewPager;
    private RadioGroup mViewPagerIndicator;
    private int curPosition;
    private TextureVideoView mVideoBlack, mVideoBlue;

    private ImageView ivDetails;
    private FrameLayout detailsLayout;
    private ViewPager mRecyclerView;
    private ImageAdapter mImageAdapter;

    private LinearLayout msgLayout;
    private TypefaceTextView msgTitle, msgContent, mTvButton;
    private RadioGroup mRadioGroup;
    private ImageView fragmentBack, ivTip;
    private FrameLayout flTip;

    private ObjectAnimator objectTip;
    private boolean isStartAnimation;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_color;
    }

    @Override
    protected void initViews(View mRootView) {
        mViewPager = mRootView.findViewById(R.id.view_pager);
        mViewPagerIndicator = mRootView.findViewById(R.id.view_pager_indicator);

        ivDetails = mRootView.findViewById(R.id.iv_details);
        detailsLayout = mRootView.findViewById(R.id.details_layout);
        mRecyclerView = mRootView.findViewById(R.id.ry_details);
        msgLayout = mRootView.findViewById(R.id.msg_layout);
        msgTitle = mRootView.findViewById(R.id.msg_title);
        ivTip = mRootView.findViewById(R.id.iv_hand);
        flTip = mRootView.findViewById(R.id.fl_tip);
        msgContent = mRootView.findViewById(R.id.msg_content);
        mRadioGroup = mRootView.findViewById(R.id.radio_group);
        fragmentBack = mRootView.findViewById(R.id.fragment_back);
        mTvButton = mRootView.findViewById(R.id.tv_button);


    }

    @Override
    protected void init() {
        initImages();
        ArrayList<View> views = new ArrayList<>();
        views.add(mActivity.getLayoutInflater().inflate(R.layout.video_layout1, null));
        views.add(mActivity.getLayoutInflater().inflate(R.layout.video_layout, null));
        mVideoBlack = views.get(1).findViewById(R.id.texture_video_view);
        mVideoBlue = views.get(0).findViewById(R.id.texture_video_view1);
        mViewPager.setAdapter(new ViewsAdapter(views));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mViewPagerIndicator.check(R.id.radio1);
                        if (mActivity != null && mActivity.getCurPosition() == 10) {
                            mActivity.setTitleText(R.string.phone_color_Blue);
                            mActivity.setContentText(R.string.phone_color_content_blue);
                        }
                        if (null != mVideoBlack) {
                            mVideoBlack.pause();
                            mVideoBlack.seekTo(0);
                            mVideoBlack.setVisibility(View.INVISIBLE);
                        }
                        if (null != mVideoBlue) {
                            mVideoBlue.setVisibility(View.VISIBLE);
                            mVideoBlue.seekTo(0);
                            mVideoBlue.start();
                        }
                        break;
                    case 1:
                        mViewPagerIndicator.check(R.id.radio2);
                        if (mActivity != null && mActivity.getCurPosition() == 10) {
                            mActivity.setTitleText(R.string.phone_color_Black);
                            mActivity.setContentText(R.string.phone_color_content_black);
                        }
                        if (null != mVideoBlue) {
                            mVideoBlue.pause();
                            mVideoBlack.seekTo(0);
                            mVideoBlue.setVisibility(View.INVISIBLE);
                        }
                        if (null != mVideoBlack) {
                            mVideoBlack.setVisibility(View.VISIBLE);
                            mVideoBlack.seekTo(0);
                            mVideoBlack.start();
                        }
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPagerIndicator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1) {
                    curPosition = 0;
                } else {
                    curPosition = 1;
                }
                mViewPager.setCurrentItem(curPosition);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mActivity != null) {
                    int position = curPosition;
                    switch (checkedId) {
                        case R.id.radio_button1:
                            curPosition = 0;
                            break;
                        case R.id.radio_button2:
                            curPosition = 1;
                            break;
                        default:
                    }
                    if (detailsLayout.getVisibility() == View.VISIBLE) {
                        mImageAdapter = new ImageAdapter(mActivity, images[curPosition]);
                        mRecyclerView.setAdapter(mImageAdapter);
                    }
                    if (mViewPager != null && position != curPosition && mViewPager.getCurrentItem() != curPosition) {
                        mViewPager.setCurrentItem(curPosition);
                    }
                    switch (curPosition) {
                        case 0:
                            if (msgTitle != null) {
                                msgTitle.setText(R.string.phone_color_Blue);
                            }
                            if (msgContent != null) {
                                msgContent.setText(R.string.phone_color_blue_content);
                            }
                            break;
                        case 1:

                            if (msgTitle != null) {
                                msgTitle.setText(R.string.phone_color_Black);
                            }
                            if (msgContent != null) {
                                msgContent.setText(R.string.phone_color_Black_content);
                            }
                            break;
                        default:
                    }
                }
            }
        });


        setVideoCompletIon(mVideoBlack, true);
        setVideoCompletIon(mVideoBlue, false);
        mTvButton.setOnClickListener(this);
        fragmentBack.setOnClickListener(this);
        ivDetails.setOnClickListener(this);
    }

    private void setVideoCompletIon(final TextureVideoView videoView, final boolean isWrite) {
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (videoView != null && videoView.getVisibility() == View.VISIBLE) {
                    videoView.seekTo(0);
                    videoView.start();
                }
            }
        });
    }

    @Override
    protected void start() {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(curPosition);
        }
        if (mVideoBlack != null) {
            mVideoBlack.setVideoId(R.raw.vv_phone_black);

        }
        if (mVideoBlue != null) {
            mVideoBlue.setVideoId(R.raw.vv_phone_blue);
            mVideoBlue.setVisibility(View.VISIBLE);
            mVideoBlue.start();
        }
    }

    @Override
    protected void pause() {
        initFragmentView();
    }

    private void initFragmentView() {
        isStartAnimation = false;
        if (objectTip != null) {
            objectTip.cancel();
        }
        if (null != mActivity&&mActivity.getCurPosition() == 10) {
            mActivity.setTextVISIBLE();
            mActivity.setArrowsDispaly();
            mActivity.showIcon();
            mActivity.setTextVISIBLE();
        }
        if (null != flTip) {
            flTip.setVisibility(View.GONE);
        }


        if (mViewPagerIndicator != null && mViewPager != null) {
            mViewPagerIndicator.check(R.id.radio1);
        }

        if (mVideoBlack != null) {
            mVideoBlack.seekTo(0);
            mVideoBlack.pause();
            mVideoBlack.setVisibility(View.INVISIBLE);
        }
        if (mVideoBlue != null) {
            mVideoBlue.seekTo(0);
            mVideoBlue.pause();
            mVideoBlue.setVisibility(View.INVISIBLE);
        }

        if (null != mTvButton) {
            mTvButton.setVisibility(View.VISIBLE);
        }

        if (mRecyclerView != null) {
            mRecyclerView.scrollTo(0, 0);
        }
        if (msgLayout != null) {
            msgLayout.setTranslationX(0f);
        }
        if (detailsLayout != null) {
            detailsLayout.setAlpha(0f);
            detailsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void destroyFragmentViews() {
        if (mVideoBlack != null) {
            mVideoBlack.stopPlayback();
            mVideoBlack = null;
        }
        if (mVideoBlue != null) {
            mVideoBlue.stopPlayback();
            mVideoBlue = null;
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.fragment_back:
                if (null != mTvButton) {
                    mTvButton.setVisibility(View.VISIBLE);
                }
                if (mRecyclerView != null) {
                    mRecyclerView.scrollTo(0, 0);
                }
                if (msgLayout != null) {
                    msgLayout.setTranslationX(0f);
                }
                if (detailsLayout != null) {
                    detailsLayout.setAlpha(0f);
                    detailsLayout.setVisibility(View.GONE);
                }
                switch (curPosition) {
                    case 0:
                        if (mVideoBlack != null) {
                            mVideoBlack.setVisibility(View.VISIBLE);
                            mVideoBlack.start();
                        }
                        break;
                    case 1:
                        if (mVideoBlue != null) {
                            mVideoBlue.setVisibility(View.VISIBLE);
                            mVideoBlue.start();
                        }
                        break;

                }
                if (mActivity != null) {
                    mActivity.showIcon();
                    mActivity.setTextVISIBLE();
                }
                break;
            case R.id.iv_details:
            case R.id.tv_button:
                if (null != mTvButton) {
                    mTvButton.setVisibility(View.GONE);
                }
                mRadioGroup.check(curPosition==0?R.id.radio_button1:R.id.radio_button2);
                mImageAdapter = new ImageAdapter(mActivity, images[curPosition]);
                mRecyclerView.setAdapter(mImageAdapter);
                mRecyclerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        int left = position * mRecyclerView.getMeasuredWidth() + positionOffsetPixels;
                        if (msgLayout != null) {
                            msgLayout.setTranslationX(-left);
                        }
                    }

                    @Override
                    public void onPageSelected(int position) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
                switch (curPosition) {
                    case 0:
                        if (msgTitle != null) {
                            msgTitle.setText(R.string.phone_color_Blue);
                        }
                        if (msgContent != null) {
                            msgContent.setText(R.string.phone_color_blue_content);
                        }
                        if (mVideoBlue != null) {
                            mVideoBlue.seekTo(0);
                            mVideoBlue.pause();
                            mVideoBlue.setVisibility(View.INVISIBLE);
                        }

                        break;
                    case 1:

                        if (msgTitle != null) {
                            msgTitle.setText(R.string.phone_color_Black);
                        }
                        if (msgContent != null) {
                            msgContent.setText(R.string.phone_color_Black_content);
                        }
                        if (mVideoBlack != null) {
                            mVideoBlack.seekTo(0);
                            mVideoBlack.pause();
                            mVideoBlack.setVisibility(View.INVISIBLE);
                        }
                        break;


                }
                ValueAnimUtil.showViews(getClass().getName(), detailsLayout);

                if (null != flTip && !isStartAnimation) {
                    isStartAnimation = true;
                    flTip.setVisibility(View.VISIBLE);
                    objectTip = AnimationOppoUtils.setColorTipAnimationMethods(ivTip, flTip);
                    objectTip.start();
                }

                if (mActivity != null) {
                    mActivity.hideIcon();
                    mActivity.setTextINVISIBLE();
                }
                break;
            default:

        }
    }

    private int[][] images;

    private void initImages() {
        images = new int[2][9];
        Resources res = getResources();
        for (int i = 0; i < 9; i++) {
            images[0][i] = res.getIdentifier("phone_color_details_b" + (i + 1), "mipmap", mActivity.getPackageName());
        }
        for (int i = 0; i < 9; i++) {
            images[1][i] = res.getIdentifier("phone_color_details_a" + (i + 1), "mipmap", mActivity.getPackageName());
        }


    }
}