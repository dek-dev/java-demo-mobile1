package com.oppo.marketdemo.fragments.lot;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:W51 耳机
 */
public class HeadsetFragment extends BaseFragment {
    private Thread mThread;
    private ViewsAdapter mAdapter;
    private ObjectAnimator objectAnimatorOpen, objectAnimatorClose;
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPager viewPager, viewPagerW51;
    private View view1, view2, view3, view4;
    private TypefaceTextView tvButton;
    private TextureVideoView mVideoView, mVideoView2, mVideoView4, mVideoView5;
    private ImageView mOpenCircle, mCloseCircle;
    private ConstraintLayout mAniLayout, mAniLayout1;
    private FrameLayout rippleLayout, flNext;
    private boolean isOpen = true;
    private int curPosition;
    private boolean viewPagerIsChange;
    private float scale;
    private RadioGroup mViewPagerIndicator;
    private int[] imagesW51 = {R.mipmap.heads_write, R.mipmap.heads_black, R.mipmap.heads_blue};
    private ImageView videoW51Bg1, videoW51Bg2, videoW51Bg3;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_headset;
    }

    @Override
    protected void initViews(View mRootView) {
        viewPagerIndicator = mRootView.findViewById(R.id.dot_horizontal_more);
        viewPager = mRootView.findViewById(R.id.view_page);
        viewPagerW51 = mRootView.findViewById(R.id.view_page_w51);
        mViewPagerIndicator = mRootView.findViewById(R.id.view_pager_indicator);
        flNext = mRootView.findViewById(R.id.fl_next);
        tvButton = mRootView.findViewById(R.id.tv_button);
    }

    @Override
    protected void init() {
        //设置w51产品
        ArrayList<View> views = new ArrayList<>();
        views.add(mActivity.getLayoutInflater().inflate(R.layout.fragment_w51_layout, null));
        views.add(mActivity.getLayoutInflater().inflate(R.layout.fragment_w51_layout, null));
        views.add(mActivity.getLayoutInflater().inflate(R.layout.fragment_w51_layout, null));
        videoW51Bg1 = views.get(0).findViewById(R.id.iv_w51_bg);
        videoW51Bg2 = views.get(1).findViewById(R.id.iv_w51_bg);
        videoW51Bg3 = views.get(2).findViewById(R.id.iv_w51_bg);
        videoW51Bg1.setImageResource(imagesW51[0]);
        videoW51Bg2.setImageResource(imagesW51[1]);
        videoW51Bg3.setImageResource(imagesW51[2]);
        viewPagerW51.setAdapter(new ViewsAdapter(views));
        mViewPagerIndicator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (null == viewPagerW51) {
                    return;
                }
                switch (checkedId) {
                    case R.id.radio1:
                        viewPagerW51.setCurrentItem(0);
                        break;
                    case R.id.radio2:
                        viewPagerW51.setCurrentItem(1);
                        break;
                    case R.id.radio3:
                        viewPagerW51.setCurrentItem(2);
                        break;
                    default:
                }
            }
        });
        viewPagerW51.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (mViewPagerIndicator != null && mViewPagerIndicator.getCheckedRadioButtonId() != R.id.radio1) {
                            mViewPagerIndicator.check(R.id.radio1);
                        }
                        break;
                    case 1:
                        if (mViewPagerIndicator != null && mViewPagerIndicator.getCheckedRadioButtonId() != R.id.radio2) {
                            mViewPagerIndicator.check(R.id.radio2);
                        }
                        break;
                    case 2:
                        if (mViewPagerIndicator != null && mViewPagerIndicator.getCheckedRadioButtonId() != R.id.radio3) {
                            mViewPagerIndicator.check(R.id.radio3);
                        }
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvButton.setOnClickListener(this);

        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.fragment_w51_layout1, null);
        view2 = lf.inflate(R.layout.fragment_w51_layout2, null);
        view3 = lf.inflate(R.layout.fragment_w51_layout3, null);
        view4 = lf.inflate(R.layout.fragment_w51_layout4, null);
        initView1(view1);
        initView2(view2);
        initView3(view3);

        rippleLayout = view4.findViewById(R.id.ripple_layout);
        ArrayList<View> mList = new ArrayList<>();
        mList.add(view1);
        mList.add(view2);
        mList.add(view4);
        mList.add(view3);

        mAdapter = new ViewsAdapter(mList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPagerIndicator.setLength(mList.size());
        viewPagerIndicator.setSelected(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPagerIndicator.setSelected(position);
                curPosition = position;
                viewPagerIsChange = true;
                if (objectAnimatorClose != null) {
                    objectAnimatorClose.cancel();
                }
                if (objectAnimatorOpen != null) {
                    objectAnimatorOpen.cancel();
                }
                switch (position) {
                    case 0:
                        if (mActivity != null && mActivity.getCurPosition() == 23) {
                            mActivity.setTitleText(R.string.headset_title_0);
                            mActivity.setContentText(R.string.headset_content_0);
                            mActivity.setTitleRemake(R.string.string_null);
                        }
                        if (null != mVideoView) {
                            mVideoView.seekTo(0);
                            mVideoView.pause();
                        }
                        if (null != mVideoView2) {
                            mVideoView2.seekTo(0);
                            mVideoView2.pause();
                        }
                        if (null != mVideoView4) {
                            mVideoView4.seekTo(0);
                            mVideoView4.pause();
                        }
                        if (null != mVideoView5) {
                            mVideoView5.seekTo(0);
                            mVideoView5.pause();
                        }
                        break;
                    case 1:
                        if (mActivity != null) {
                            mActivity.setTitleText(R.string.headset_title1);
                            mActivity.setContentText(R.string.headset_content1);
                            mActivity.setTitleRemake(R.string.string_null);

                        }
                        if (null != mVideoView) {
                            mVideoView.seekTo(0);
                            mVideoView.pause();
                        }
                        if (null != mVideoView4) {
                            mVideoView4.seekTo(0);
                            mVideoView4.pause();
                        }
                        if (null != mVideoView5) {
                            mVideoView5.seekTo(0);
                            mVideoView5.pause();
                        }
                        break;
                    case 2:
                        if (mActivity != null) {
                            mActivity.setTitleText(R.string.headset_title3);
                            mActivity.setContentText(R.string.headset_content3);
                            mActivity.setTitleRemake(R.string.headset_content3_remake);
                        }

                        if (null != mVideoView) {
                            mVideoView.seekTo(0);
                            mVideoView.pause();
                        }
                        if (null != mVideoView2) {
                            mVideoView2.pause();
                        }
                        if (null != mVideoView4) {
                            mVideoView4.seekTo(0);
                            mVideoView4.pause();
                        }
                        if (null != mVideoView5) {
                            mVideoView5.seekTo(0);
                            mVideoView5.pause();
                        }
                        break;
                    case 3:
                        if (mActivity != null) {
                            mActivity.setTitleText(R.string.headset_title2);
                            mActivity.setContentText(R.string.headset_content2);
                            mActivity.setTitleRemake(R.string.string_null);
                        }
                        isOpen = true;
                        mAniLayout.setVisibility(View.VISIBLE);
                        mAniLayout1.setVisibility(View.GONE);
                        if (objectAnimatorOpen != null) {
                            objectAnimatorOpen.start();
                        }
                        if (null != mVideoView) {
                            mVideoView.seekTo(0);
                            mVideoView.pause();
                        }
                        if (null != mVideoView2) {
                            mVideoView2.seekTo(0);
                            mVideoView2.pause();
                        }

                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE && viewPagerIsChange) {
                    viewPagerIsChange = false;
                    switch (curPosition) {
                        case 0:
                            if (mVideoView != null && isVisible()) {
                                mVideoView.setVideoId(R.raw.vv_w51_music_down);
                                mVideoView.setAlpha(1f);
                                mVideoView.setVisibility(View.VISIBLE);
                                mVideoView.start();
                            }
                            if (mVideoView2 != null) {
                                mVideoView2.suspend();
                                mVideoView2.setVisibility(View.INVISIBLE);
                            }
                            if (mVideoView4 != null) {
                                mVideoView4.suspend();
                                mVideoView4.setVisibility(View.INVISIBLE);
                            }
                            if (mVideoView5 != null) {
                                mVideoView5.suspend();
                                mVideoView5.setVisibility(View.INVISIBLE);
                            }
                            break;
                        case 1:
                            if (mVideoView != null && isVisible()) {
                                mVideoView.suspend();
                                mVideoView.setVisibility(View.INVISIBLE);
                            }
                            if (mVideoView2 != null) {
                                mVideoView2.setVideoId(R.raw.vv_w51_phone_down);
                                mVideoView2.setAlpha(1f);
                                mVideoView2.setVisibility(View.VISIBLE);
                                mVideoView2.start();
                            }
                            if (mVideoView4 != null) {
                                mVideoView4.suspend();
                                mVideoView4.setVisibility(View.INVISIBLE);
                            }
                            if (mVideoView5 != null) {
                                mVideoView5.suspend();
                                mVideoView5.setVisibility(View.INVISIBLE);
                            }
                            break;
                        case 2:
                            if (mVideoView != null) {
                                mVideoView.suspend();
                                mVideoView.setVisibility(View.INVISIBLE);
                            }
                            if (mVideoView2 != null) {
                                mVideoView2.suspend();
                                mVideoView2.setVisibility(View.INVISIBLE);
                            }
                            if (mVideoView4 != null) {
                                mVideoView4.suspend();
                                mVideoView4.setVisibility(View.INVISIBLE);
                            }
                            if (mVideoView5 != null) {
                                mVideoView5.suspend();
                                mVideoView5.setVisibility(View.INVISIBLE);
                            }
                            break;
                        case 3:
                            if (mVideoView != null) {
                                mVideoView.suspend();
                                mVideoView.setVisibility(View.INVISIBLE);
                            }
                            if (mVideoView2 != null) {
                                mVideoView2.suspend();
                                mVideoView2.setVisibility(View.INVISIBLE);
                            }

                            break;
                        default:
                    }
                }
            }
        });

        mVideoView4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (isOpen) {
                    isOpen = false;
                    if (mVideoView5 != null) {
                        mVideoView5.seekTo(0);
                        mVideoView5.pause();
                    }
                    mAniLayout1.setVisibility(View.VISIBLE);
                    if (objectAnimatorClose != null) {
                        objectAnimatorClose.start();
                    }
                }
            }
        });
        mVideoView5.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!isOpen) {
                    isOpen = true;
                    if (mVideoView4 != null) {
                        mVideoView4.seekTo(0);
                        mVideoView4.pause();
                    }
                    mAniLayout.setVisibility(View.VISIBLE);
                    if (objectAnimatorOpen != null) {
                        objectAnimatorOpen.start();
                    }
                }
            }
        });
        objectAnimatorOpen = AnimationOppoUtils.setEnduranceAnimation(mOpenCircle);
        objectAnimatorClose = AnimationOppoUtils.setEnduranceAnimation(mCloseCircle);
    }

    private void initView1(View view1) {
        mVideoView = view1.findViewById(R.id.texture_video_view);

        setCompletionMethods(mVideoView);
    }

    private void initView2(View view2) {
        mVideoView2 = view2.findViewById(R.id.texture_video_view2);
        setCompletionMethods(mVideoView2);
    }

    private void initView3(View view3) {
        mVideoView4 = view3.findViewById(R.id.texture_video_view4);
        mVideoView5 = view3.findViewById(R.id.texture_video_view5);
        mOpenCircle = view3.findViewById(R.id.iv_watch_circle1);
        mCloseCircle = view3.findViewById(R.id.iv_watch_close_circle1);
        mAniLayout = view3.findViewById(R.id.cl_open);
        mAniLayout1 = view3.findViewById(R.id.cl_close);
        mOpenCircle.setOnClickListener(this);
        mCloseCircle.setOnClickListener(this);
    }

    private void setCompletionMethods(final TextureVideoView videoView) {
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (null != videoView) {
                    videoView.seekTo(0);
                    videoView.start();
                }
            }
        });
    }

    @Override
    protected void start() {
        if (mVideoView != null) {
            mVideoView.setVideoId(R.raw.vv_w51_music_down);
        }
        if (mVideoView2 != null) {
            mVideoView2.setVideoId(R.raw.vv_w51_phone_down);
        }
        if (mVideoView4 != null) {
            mVideoView4.setVideoId(R.raw.vv_w51_open);
        }
        if (mVideoView5 != null) {
            mVideoView5.setVideoId(R.raw.vv_w51_close);
        }
    }

    private void startThread() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (rippleLayout != null) {
                                    if ((int) (scale / 0.25f) > rippleLayout.getChildCount()) {
                                        rippleLayout.addView(createImageView());
                                    }
                                    for (int i = 0; i < rippleLayout.getChildCount(); i++) {
                                        View view = rippleLayout.getChildAt(i);
                                        if (view != null) {
                                            if (i == 0 && scale > 0.75f) {
                                                view.setAlpha(1 - (scale - 0.75f) / 0.25f);
                                            }
                                            view.setScaleX(scale - (i * 0.25f));
                                            view.setScaleY(scale - (i * 0.25f));
                                        }
                                    }
                                    if (scale >= 1f) {
                                        rippleLayout.removeViewAt(0);
                                        scale = scale - 0.25f;
                                    }
                                }
                            }
                        });
                        scale += 0.003f;
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    if (rippleLayout != null) {
                        rippleLayout.removeAllViews();
                    }
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }

    private ImageView createImageView() {
        if (mActivity != null) {
            ImageView imageView = new ImageView(mActivity);
            FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            fl.setMargins(0, 0, 0, 0);
            fl.gravity = Gravity.CENTER;
            imageView.setLayoutParams(fl);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.mipmap.ripple_headset);
            return imageView;
        }
        return null;
    }

    @Override
    protected void pause() {
        initFragmentViews();
    }

    private void setFragmentNext() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
        if (viewPager != null) {
            viewPager.setCurrentItem(0, false);
        }
        if (null != mActivity&&mActivity.getCurPosition() ==23) {
            mActivity.setTitleText(isNext ? R.string.headset_title_0 : R.string.headset_title);
            mActivity.setContentText(isNext ? R.string.headset_content_0 : R.string.headset_content);
            mActivity.setTitleRemake(isNext ? R.string.string_null: R.string.headset_remake);
        }
        if (mVideoView != null) {
            mVideoView.seekTo(0);
            mVideoView.pause();
            mVideoView.setVisibility(View.INVISIBLE);
        }
        if (mVideoView2 != null) {
            mVideoView2.seekTo(0);
            mVideoView2.pause();
            mVideoView2.setVisibility(View.INVISIBLE);
        }
        if (mVideoView4 != null) {
            mVideoView4.seekTo(0);
            mVideoView4.pause();
            mVideoView4.setVisibility(View.INVISIBLE);
        }
        if (mVideoView5 != null) {
            mVideoView5.seekTo(0);
            mVideoView5.pause();
            mVideoView5.setVisibility(View.INVISIBLE);
        }
        if (objectAnimatorClose != null) {
            objectAnimatorClose.cancel();
        }
        if (objectAnimatorOpen != null) {
            objectAnimatorOpen.cancel();
        }
        if (mAniLayout1 != null) {
            mAniLayout1.setVisibility(View.GONE);
        }
        if (mAniLayout != null) {
            mAniLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化状态
     */
    private void initFragmentViews() {
        isNext = false;
        if (null != flNext) {
            flNext.setVisibility(View.INVISIBLE);
        }
        if (null != viewPagerW51) {
            viewPagerW51.setVisibility(View.VISIBLE);
            viewPagerW51.setCurrentItem(0, false);
        }
        if (mViewPagerIndicator != null) {
            mViewPagerIndicator.check(R.id.radio1);
        }
        if (null != tvButton) {
            tvButton.setText(R.string.bt_tap_more);
        }
        setFragmentNext();
    }

    private boolean isNext;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回/搜索更多
            case R.id.tv_button:
                isNext = !isNext;
                viewPagerW51.setVisibility(isNext ? View.GONE : View.VISIBLE);
                flNext.setVisibility(isNext ? View.VISIBLE : View.GONE);
                tvButton.setText(isNext ? R.string.bt_tap_back : R.string.bt_tap_more);
                if (null != mActivity) {
                    mActivity.setTitleText(isNext ? R.string.headset_title_0 : R.string.headset_title);
                    mActivity.setContentText(isNext ? R.string.headset_content_0 : R.string.headset_content);
                    mActivity.setTitleRemake(isNext ? R.string.string_null: R.string.headset_remake);

                }
                if (isNext) {
                    mVideoView.setVisibility(View.VISIBLE);
                    mVideoView.start();
                    startThread();
                } else {
                    setFragmentNext();
                }

                break;

            case R.id.iv_watch_circle1:
                if (objectAnimatorOpen != null) {
                    objectAnimatorOpen.cancel();
                }
                mAniLayout.setVisibility(View.GONE);
                if (mVideoView4 != null) {
                    mVideoView4.setVideoId(R.raw.vv_w51_open);
                    mVideoView4.setVisibility(View.VISIBLE);
                    mVideoView4.start();
                }
                if (mVideoView5 != null) {
                    mVideoView5.seekTo(0);
                    mVideoView5.pause();
                    mVideoView5.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.iv_watch_close_circle1:
                if (objectAnimatorClose != null) {
                    objectAnimatorClose.cancel();
                }
                mAniLayout1.setVisibility(View.GONE);
                if (mVideoView5 != null) {
                    mVideoView5.setVideoId(R.raw.vv_w51_close);
                    mVideoView5.setVisibility(View.VISIBLE);
                    mVideoView5.start();
                }
                break;
        }
    }

    @Override
    public void releaseVideo() {
        super.releaseVideo();
        if (mVideoView != null) {
            mVideoView.suspend();
        }
        if (mVideoView2 != null) {
            mVideoView2.suspend();
        }
        if (mVideoView4 != null) {
            mVideoView4.suspend();
        }
        if (mVideoView5 != null) {
            mVideoView5.suspend();
        }
    }

    @Override
    public void pauseVideo() {
        super.pauseVideo();
        if (mVideoView != null) {
            mVideoView.pause();
        }
        if (mVideoView2 != null) {
            mVideoView2.pause();
        }
        if (mVideoView4 != null) {
            mVideoView4.suspend();
        }
        if (mVideoView5 != null) {
            mVideoView5.suspend();
        }
    }

    @Override
    public void videoRefresh() {
        super.videoRefresh();
        if (mVideoView != null) {
            mVideoView.seekTo(0);
        }
        if (mVideoView2 != null) {
            mVideoView2.seekTo(0);
        }
        if (mVideoView4 != null) {
            mVideoView4.suspend();
        }
        if (mVideoView5 != null) {
            mVideoView5.suspend();
        }
    }

    @Override
    protected void destroyFragmentViews() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
        if (mVideoView2 != null) {
            mVideoView2.stopPlayback();
        }
        if (mVideoView4 != null) {
            mVideoView4.stopPlayback();
        }
        if (mVideoView5 != null) {
            mVideoView5.stopPlayback();
        }
        if (objectAnimatorClose != null) {
            objectAnimatorClose.cancel();
        }
        if (objectAnimatorOpen != null) {
            objectAnimatorOpen.cancel();
        }
    }
}