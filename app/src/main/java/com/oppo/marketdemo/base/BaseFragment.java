package com.oppo.marketdemo.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.globle.VApplication;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.pm.ApplicationInfo.FLAG_SUPPORTS_RTL;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2018/2/26
 * Description: Fragment基类
 */

public class BaseFragment extends Fragment implements View.OnClickListener {

    protected static final int FRAGMENT_STYLE_ATTACH = 0;
    protected static final int FRAGMENT_STYLE_CREATE = 1;
    protected static final int FRAGMENT_STYLE_CREATE_VIEW = 2;
    protected static final int FRAGMENT_STYLE_ACTIVITY_CREATE = 3;
    protected static final int FRAGMENT_STYLE_START = 4;
    protected static final int FRAGMENT_STYLE_RESUME = 5;
    protected static final int FRAGMENT_STYLE_PAUSE = 6;
    protected static final int FRAGMENT_STYLE_STOP = 7;
    protected static final int FRAGMENT_STYLE_DESTROY_VIEW = 8;
    protected static final int FRAGMENT_STYLE_DESTROY = 9;
    protected static final int FRAGMENT_STYLE_DETACH = 10;

    public static final int buttonWidth = 540;
    public static final int buttonHeight = 138;
    protected BaseActivity mActivity;

    public boolean canChange = true;
    private int fragmentStyle;

    protected int getLayoutId() {
        return 0;
    }

    protected void initViews(View mRootView) {
    }

    protected void init() {
    }

    protected void start() {
    }

    protected void pause() {
    }

    protected void destroyFragmentViews() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentStyle = FRAGMENT_STYLE_ATTACH;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentStyle = FRAGMENT_STYLE_CREATE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = null;
        fragmentStyle = FRAGMENT_STYLE_CREATE_VIEW;
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            mRootView = inflater.inflate(layoutId, null, false);
        }
        if (mRootView != null) {
            initViews(mRootView);
            return mRootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentStyle = FRAGMENT_STYLE_ACTIVITY_CREATE;
        mActivity = (BaseActivity) getActivity();
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentStyle = FRAGMENT_STYLE_START;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentStyle = FRAGMENT_STYLE_RESUME;
        if (canChange) {
            start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
        if (mShowValueAnimator != null) {
            mShowValueAnimator.cancel();
        }
        if (mHideValueAnimator != null) {
            mHideValueAnimator.cancel();
        }
        fragmentStyle = FRAGMENT_STYLE_PAUSE;
    }

    @Override
    public void onStop() {
        super.onStop();
        fragmentStyle = FRAGMENT_STYLE_STOP;
    }

    private static final long AnimatorDuration = 300;

    protected void hideViews(View... views) {
        hideViews(AnimatorDuration, null, true, views);
    }

    protected void hideViews(long duration, Interpolator interpolator, final boolean needGone, final View... views) {
        if (views != null && views.length > 0) {
            if (mHideValueAnimator != null) {
                mHideValueAnimator.cancel();
                mHideValueAnimator = null;
            }
            final ArrayList<Float> floats = new ArrayList<>();
            for (View view : views) {
                if (view != null) {
                    floats.add(view.getAlpha());
                }
            }
            mHideValueAnimator = ValueAnimator.ofFloat(1f, 0f);
            mHideValueAnimator.setDuration(duration);
            if (interpolator != null) {
                mHideValueAnimator.setInterpolator(interpolator);
            } else {
                mHideValueAnimator.setInterpolator(new LinearInterpolator());
            }
            mHideValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    for (int i = 0; i < views.length; i++) {
                        View view = views[i];
                        if (view != null) {
                            view.setAlpha(value * floats.get(i));
                            if (value == 0f && needGone) {
                                view.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
            mHideValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    hideViewsEnd(views);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            mHideValueAnimator.start();
        }
    }

    protected void hideViewsEnd(View... views) {
    }

    protected void hideCancel() {
        if (mHideValueAnimator != null) {
            mHideValueAnimator.cancel();
            mHideValueAnimator = null;
        }
    }


    private ValueAnimator mShowValueAnimator, mHideValueAnimator;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyFragmentViews();
        hideCancel();
        showCancel();
        fragmentStyle = FRAGMENT_STYLE_DESTROY_VIEW;
    }

    public void startAnimList(boolean isAnimList) {
    }

    protected void showCancel() {
        if (mShowValueAnimator != null) {
            mShowValueAnimator.cancel();
            mShowValueAnimator = null;
        }
    }

    protected void showViews(View... views) {
        showViews(AnimatorDuration, null, views);
    }

    protected void showViews(long duration, Interpolator interpolator, final View... views) {
        if (views != null && views.length > 0) {
            if (mShowValueAnimator != null) {
                mShowValueAnimator.cancel();
                mShowValueAnimator = null;
            }
            final ArrayList<Float> floats = new ArrayList<>();
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                    floats.add(view.getAlpha());
                }
            }
            mShowValueAnimator = ValueAnimator.ofFloat(0f, 1f);
            mShowValueAnimator.setDuration(duration);
            if (interpolator != null) {
                mShowValueAnimator.setInterpolator(interpolator);
            } else {
                mShowValueAnimator.setInterpolator(new LinearInterpolator());
            }
            mShowValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    for (int i = 0; i < views.length; i++) {
                        View view = views[i];
                        if (view != null) {
                            view.setVisibility(View.VISIBLE);
                            view.setAlpha(floats.get(i) + value * (1 - floats.get(i)));
                        }
                    }
                }
            });
            mShowValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    showViewsEnd(views);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            mShowValueAnimator.start();
        }
    }

    protected void showViewsEnd(View... views) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        fragmentStyle = FRAGMENT_STYLE_DESTROY;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentStyle = FRAGMENT_STYLE_DETACH;
    }

    public int getFragmentStyle() {
        return fragmentStyle;
    }

    public void setCanChange(boolean canChange) {
        this.canChange = canChange;
    }

    public boolean isCanChange() {
        return canChange;
    }

    /**
     * 判断是否进行阿拉伯镜像，判断取决于两个条件：<br />
     * 一丶Androidmanifest.xml中的supportsRtl<br />
     * 二、当前系统为阿拉伯语
     *
     * @return 是否为rtl
     */
    public boolean isRTL() {
        ApplicationInfo applicationInfo = VApplication.getInstance().getApplicationInfo();
        boolean hasRtlSupport = (applicationInfo.flags & FLAG_SUPPORTS_RTL) == FLAG_SUPPORTS_RTL;
        boolean isRtl = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
        return hasRtlSupport && isRtl;
    }

    @Override
    public void onClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK, HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING);
    }

    public boolean onActivityClick(View view) {
        return false;
    }

    public void releaseVideo() {
        releaseVideo(R.id.texture_video_view);
        releaseVideo(R.id.texture_video_view1);
        releaseVideo(R.id.texture_video_view2);
        releaseVideo(R.id.texture_video_view3);
    }

    public void releaseVideo(@NonNull int viewId) {
        if (getView() != null) {
            TextureVideoView textureVideoView = getView().findViewById(viewId);
            if (textureVideoView != null) {
                textureVideoView.suspend();
            }
        }
    }

    public void pauseVideo() {
        pauseVideo(R.id.texture_video_view);
        pauseVideo(R.id.texture_video_view1);
        pauseVideo(R.id.texture_video_view2);
        pauseVideo(R.id.texture_video_view3);
    }

    public void pauseVideo(@NonNull int viewId) {
        if (getView() != null) {
            TextureVideoView textureVideoView = getView().findViewById(viewId);
            if (textureVideoView != null) {
                textureVideoView.pause();
            }
        }
    }

    public void videoRefresh() {
        videoRefresh(R.id.texture_video_view);
        videoRefresh(R.id.texture_video_view1);
        videoRefresh(R.id.texture_video_view2);
        videoRefresh(R.id.texture_video_view3);
    }

    public void videoRefresh(@NonNull int viewId) {
        if (getView() != null) {
            TextureVideoView textureVideoView = getView().findViewById(viewId);
            if (textureVideoView != null) {
                textureVideoView.seekTo(0);
            }
        }
    }
}
