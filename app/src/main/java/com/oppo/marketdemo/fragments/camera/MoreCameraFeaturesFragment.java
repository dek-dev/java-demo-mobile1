package com.oppo.marketdemo.fragments.camera;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
 * Description:更多影像功能
 */
public class MoreCameraFeaturesFragment extends BaseFragment {
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPager viewPager;
    private View view1, view2, view3, view4;
    private ViewsAdapter mAdapter;
    private TextureVideoView mVideo;
    private ImageView mBig, m1080Bg, mCircle;
    private int BigCount = 0;
    private ObjectAnimator animator;

    private TypefaceTextView GoCamera;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more_camera_features;
    }

    @Override
    protected void initViews(View mRootView) {
        viewPagerIndicator = mRootView.findViewById(R.id.dot_horizontal_more);
        viewPager = mRootView.findViewById(R.id.view_page);
        GoCamera = mRootView.findViewById(R.id.bt_camera);
    }

    @Override
    protected void init() {
        setMoreMethods();
        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mVideo != null && mVideo.getVisibility() == View.VISIBLE) {
                    mVideo.seekTo(0);
                    mVideo.start();
                }
            }
        });
        animator = AnimationOppoUtils.setBreatheMethods(mCircle);

    }

    /**
     * 切换设置
     */
    private void setMoreMethods() {
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.fragment_more_camera_layout1, null);
        view2 = lf.inflate(R.layout.fragment_more_camera_layout2, null);
        view3 = lf.inflate(R.layout.fragment_more_camera_layout3, null);
        view4 = lf.inflate(R.layout.fragment_more_camera_layout4, null);

        mBig = view1.findViewById(R.id.iv_big);
        m1080Bg = view1.findViewById(R.id.iv_bg);
        mCircle = view1.findViewById(R.id.iv_circle);
        mVideo = view4.findViewById(R.id.texture_video_view);


        ArrayList<View> mList = new ArrayList<>();
        mList.add(view1);
        mList.add(view3);
        mList.add(view2);
        mList.add(view4);

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
                BigCount = 0;
                if (animator != null) {
                    animator.cancel();
                }
                if (mVideo != null) {
                    mVideo.seekTo(0);
                    mVideo.setVisibility(View.INVISIBLE);
                    mVideo.setVisibility(View.VISIBLE);
                }
                switch (position) {
                    case 0:
                        if (mActivity != null && mActivity.getCurPosition() == 16) {
                            mActivity.setTitleText(R.string.more_camera_features_title);
                            mActivity.setContentText(R.string.more_camera_features_content);
                        }
                        if (animator != null) {
                            animator.start();
                        }
                        BigCount = 0;
                        mBig.setImageResource(R.mipmap.ic_1080_big);
                        m1080Bg.setImageResource(R.mipmap.more_camer_1080);
                        break;
                    case 1:

                        if (mActivity != null) {
                            mActivity.setTitleText(R.string.more_camera_features_title_dark);
                            mActivity.setContentText(R.string.more_camera_features_dark_content);
                        }
                        break;
                    case 2:
                        if (mActivity != null) {
                            mActivity.setTitleText(R.string.more_camera_features_title_night);
                            mActivity.setContentText(R.string.more_camera_features_night_content);
                        }
                        break;
                    case 3:


                        if (mActivity != null && mActivity.getCurPosition() == 16) {
                            mActivity.setTitleText(R.string.more_camera_features_title_bokeh);
                            mActivity.setContentText(R.string.more_camera_features_bokeh_content);
                        }
                        if (mVideo != null) {
                            mVideo.start();
                            mVideo.setVisibility(View.VISIBLE);
                        }
                        break;

                    default:
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BigCount == 0) {
                    BigCount++;
                    mBig.setImageResource(R.mipmap.ic_1080_small);
                    m1080Bg.setImageResource(R.mipmap.more_camer_1080_big);

                }  else {
                    BigCount =0;
                    mBig.setImageResource(R.mipmap.ic_1080_big);
                    m1080Bg.setImageResource(R.mipmap.more_camer_1080);
                }
            }
        });

        GoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewPager.getCurrentItem();
                switch (pos) {

                    case 0:
                        startActivity(CameraUtil.getMajorStyle());

                        break;
                    case 1:
                        startActivity(CameraUtil.getNightStyle());
                        break;
                    case 2:
                        startActivity(CameraUtil.getNightStyleFront());

                        break;
                    case 3:

                        startActivity(CameraUtil.getVideoStyle());
                        break;
                }

            }
        });
    }

    @Override
    protected void start() {
        if (mVideo != null) {
            mVideo.setVideoId(R.raw.vv_bokeh);
            mVideo.start();
            mVideo.setVisibility(View.VISIBLE);
        }
        if (animator != null) {
            animator.start();
        }
        viewPager.setCurrentItem(0, false);
    }

    @Override
    protected void pause() {
        initFragmentViews();
    }

    private void initFragmentViews() {
        BigCount =0;

        if (viewPager != null) {
            viewPager.setCurrentItem(0, false);
        }
        if (mVideo != null) {
            mVideo.seekTo(0);
            mVideo.pause();
            mVideo.setVisibility(View.INVISIBLE);
        }

        if (animator != null) {
            animator.cancel();
        }
        if (mBig != null) {
            mBig.setImageResource(R.mipmap.ic_1080_big);
        }
        if (m1080Bg != null) {
            m1080Bg.setImageResource(R.mipmap.more_camer_1080);

        }
    }

    @Override
    protected void destroyFragmentViews() {
        if (mVideo != null) {
            mVideo.stopPlayback();
            mVideo = null;
        }
        mAdapter = null;
        viewPager = null;
        viewPagerIndicator = null;
        view1 = null;
        view2 = null;
        view3 = null;
        view4 = null;
    }
}