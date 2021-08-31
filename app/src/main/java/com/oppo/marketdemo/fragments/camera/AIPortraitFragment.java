package com.oppo.marketdemo.fragments.camera;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
 * Description:ai人像
 */
public class AIPortraitFragment extends BaseFragment {

    private Thread thread;
    private boolean isShow = true;

    private TextureVideoView mVideo, mVideo1, mVideo2, mVideo3;
    private View view1, view2, view3;
    private ViewsAdapter mAdapter;
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPager viewPager;
    private ConstraintLayout cl_compare, cl_step, cl_camera_bt1, cl_camera_bt2;
    private TypefaceTextView GoCompare, GoStep, GoCompare1, GoStep1;

    private View shade;
    private ImageView GoBack, bgCamera, bt1, bt2, GoBack1, GoCamera, GoCamera1, GoCamera2;
    private ObjectAnimator animator;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ai_portrait;
    }

    @Override
    protected void initViews(View mRootView) {
        mVideo = mRootView.findViewById(R.id.texture_video_view);
        mVideo3 = mRootView.findViewById(R.id.texture_video_view3);
        viewPager = mRootView.findViewById(R.id.view_page);
        viewPagerIndicator = mRootView.findViewById(R.id.view_pager_indicator);
        GoCompare = mRootView.findViewById(R.id.bt_compare);
        GoBack = mRootView.findViewById(R.id.iv_upper_back);
        GoStep = mRootView.findViewById(R.id.bt_step);
        cl_compare = mRootView.findViewById(R.id.cl_compare);

        cl_step = mRootView.findViewById(R.id.cl_step);
        bgCamera = mRootView.findViewById(R.id.iv_camera_bg);
        shade = mRootView.findViewById(R.id.view_shade);
        bt1 = mRootView.findViewById(R.id.btn_ai1);
        bt2 = mRootView.findViewById(R.id.btn_ai2);
        GoCompare1 = mRootView.findViewById(R.id.bt_compare1);
        GoBack1 = mRootView.findViewById(R.id.iv_upper_back1);
        GoStep1 = mRootView.findViewById(R.id.bt_step1);

        cl_camera_bt1 = mRootView.findViewById(R.id.cl_bt1);
        cl_camera_bt2 = mRootView.findViewById(R.id.cl_bt2);
    }

    private boolean viewPagerIsChange;
    private int curPosition;

    @Override
    protected void init() {

        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.fragment_ai_portrait_layout1, null);
        view2 = lf.inflate(R.layout.fragment_ai_portrait_layout2, null);
        view3 = lf.inflate(R.layout.fragment_ai_portrait_layout3, null);
        mVideo1 = view1.findViewById(R.id.texture_video_view1);
        mVideo2 = view2.findViewById(R.id.texture_video_view2);
        GoCamera = view1.findViewById(R.id.iv_camera1);
        GoCamera1 = view2.findViewById(R.id.iv_camera2);
        GoCamera2 = view3.findViewById(R.id.iv_camera3);
        ArrayList<View> mList = new ArrayList<>();
        mList.add(view1);
        mList.add(view3);
        mList.add(view2);
        mAdapter = new ViewsAdapter(mList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPagerIndicator.setLength(mList.size());
        viewPagerIndicator.setSelected(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                viewPagerIndicator.setSelected(position);
                viewPagerIsChange = true;
                curPosition = position;

                switch (position) {
                    case 0:
                        if (mActivity != null && mActivity.getCurPosition() == 12) {
                            mActivity.setTitleText(R.string.ai_portrait_title_por);
                            mActivity.setContentText(R.string.ai_portrait_content);
                        }
                        if (null != mVideo2) {
                            mVideo2.seekTo(0);
                            mVideo2.pause();
                        }
                        break;
                    case 1:
                        if (mActivity != null) {
                            mActivity.setTitleText(R.string.ai_portrait_title_photo);
                            mActivity.setContentText(R.string.ai_portrait_title_photo_content);

                        }
                        if (null != mVideo2) {
                            mVideo2.seekTo(0);
                            mVideo2.pause();
                        }
                        if (null != mVideo1) {
                            mVideo1.seekTo(0);
                            mVideo1.pause();
                        }
                        break;
                    case 2:

                        if (mActivity != null) {
                            mActivity.setTitleText(R.string.ai_portrait_title_rgb);
                            mActivity.setContentText(R.string.ai_portrait_title_rgb_content);

                        }
                        if (null != mVideo1) {
                            mVideo1.seekTo(0);
                            mVideo1.pause();
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
                            if (null != mVideo1 && isVisible()) {
                                mVideo1.setVideoId(R.raw.vv_ai_por_compare);
                                mVideo1.setVisibility(View.VISIBLE);
                                mVideo1.start();
                            }
                            if (null != mVideo2 && isVisible()) {

                                mVideo2.suspend();
                                mVideo2.setVisibility(View.INVISIBLE);
                            }
                            break;
                        case 1:
                            if (null != mVideo2 && isVisible()) {

                                mVideo2.suspend();
                                mVideo2.setVisibility(View.INVISIBLE);
                            }
                            if (null != mVideo1 && isVisible()) {
                                mVideo1.suspend();
                                mVideo1.setVisibility(View.INVISIBLE);
                            }
                            break;
                        case 2:

                            if (null != mVideo2 && isVisible()) {
                                mVideo2.setVideoId(R.raw.vv_ai_por_rgb);
                                mVideo2.setVisibility(View.VISIBLE);
                                mVideo2.start();
                            }
                            if (null != mVideo1 && isVisible()) {

                                mVideo1.suspend();
                                mVideo1.setVisibility(View.INVISIBLE);
                            }
                            break;
                        default:


                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

        });

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
                        isShow = true;

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
                        }
                    }
                }, 1000);
            }
        });

        mVideo2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideo2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideo2 != null && mVideo2.getVisibility() == View.VISIBLE) {
                            mVideo2.seekTo(0);
                            mVideo2.start();
                        }
                    }
                }, 1000);
            }
        });
        mVideo3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideo3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideo3 != null && mVideo3.getVisibility() == View.VISIBLE) {
                            mVideo3.seekTo(0);
                            mVideo3.start();
                            isShow = true;
                            if (mActivity != null) {
                                mActivity.setTextINVISIBLE();
//                            GoBack1.setVisibility(View.GONE);
//                            GoCompare1.setVisibility(View.GONE);
                            }
                        }

                    }
                }, 1000);
            }
        });

        GoCompare.setOnClickListener(this);
        GoBack.setOnClickListener(this);
        GoStep.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        GoCompare1.setOnClickListener(this);
        GoBack1.setOnClickListener(this);
        GoStep1.setOnClickListener(this);
        GoCamera.setOnClickListener(this);
        GoCamera1.setOnClickListener(this);
        GoCamera2.setOnClickListener(this);
        initFragmentViews();

    }

    @Override
    protected void start() {
        if (mVideo != null) {
            mVideo.setVideoId(R.raw.vv_ai_por);
            mVideo.start();
            mVideo.setVisibility(View.VISIBLE);
        }
        if (mVideo1 != null) {
            mVideo1.setVideoId(R.raw.vv_ai_por_compare);
        }
        if (mVideo2 != null) {
            mVideo2.setVideoId(R.raw.vv_ai_por_rgb);
        }
        if (mVideo3 != null) {
            mVideo3.setVideoId(R.raw.vv_ai_por_step);
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
                                    if (mVideo3 != null && mVideo3.getCurrentPosition() > 1000 && isShow) {
                                        mVideo3.pause();
                                        bgCamera.setAlpha(1f);
                                        bgCamera.setVisibility(View.VISIBLE);
                                        shade.setVisibility(View.VISIBLE);
                                        cl_camera_bt1.setVisibility(View.VISIBLE);
                                        if (animator != null) {
                                            animator.cancel();
                                        }
                                        animator = AnimationOppoUtils.setBreatheMethods(bt1);
                                        animator.start();
                                        isShow = false;
                                    }
                                    if (mVideo3 != null && mVideo3.getCurrentPosition() > 1800 && !isShow) {
//                                        GoCompare1.setVisibility(View.VISIBLE);
//                                        GoBack1.setVisibility(View.VISIBLE);
                                        if (mActivity != null) {
                                            mActivity.setTextVISIBLE();
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
            case R.id.bt_compare:
            case R.id.bt_compare1:
                compare();
                break;
            case R.id.iv_upper_back1:
            case R.id.iv_upper_back:
                back();
                break;
            case R.id.bt_step1:
                step1();
                break;
            case R.id.bt_step:
                step();
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
                animator = AnimationOppoUtils.setIn(bgCamera);
                animator.start();
                mVideo3.start();
                break;

            case R.id.iv_camera1:
            case R.id.iv_camera2:
                startActivity(CameraUtil.getVideoStyle());
                break;
            case R.id.iv_camera3:
                startActivity(CameraUtil.getPhotoStyle());
                break;

            default:
        }
    }


    protected void compare() {
        stopThread();
        if (mVideo != null) {
            mVideo.seekTo(0);
            mVideo.pause();
            mVideo.setVisibility(View.VISIBLE);
        }
        if (mVideo3 != null) {
            mVideo3.seekTo(0);
            mVideo3.pause();
            mVideo3.setVisibility(View.INVISIBLE);
        }
        cl_step.setVisibility(View.GONE);
        cl_camera_bt1.setVisibility(View.GONE);
        cl_camera_bt2.setVisibility(View.GONE);
        bgCamera.setVisibility(View.GONE);
        shade.setVisibility(View.GONE);

        GoCompare.setVisibility(View.GONE);
        GoStep.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        cl_compare.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(0);
        if (mVideo1 != null) {
            mVideo1.setVideoId(R.raw.vv_ai_por_compare);
            mVideo1.start();
            mVideo1.setVisibility(View.VISIBLE);
        }
        if (mActivity != null) {
            mActivity.setTextVISIBLE();
            mActivity.setTextBlackColor();
            mActivity.setTitleText(R.string.ai_portrait_title_por);
            mActivity.setIconBlack();
        }
    }

    protected void back() {
        stopThread();
        initFragmentViews();
        if (mVideo != null) {
            mVideo.seekTo(0);
            mVideo.start();
            mVideo.setVisibility(View.VISIBLE);
        }
    }

    protected void step() {
        if (mActivity != null) {
            mActivity.setTextINVISIBLE();
            GoCompare.setVisibility(View.GONE);
            GoStep.setVisibility(View.GONE);

            GoCompare1.setVisibility(View.VISIBLE);
            GoBack1.setVisibility(View.VISIBLE);
        }
        if (mVideo != null) {
            mVideo.seekTo(0);
            mVideo.pause();
            mVideo.setVisibility(View.VISIBLE);
        }
        cl_step.setVisibility(View.VISIBLE);
        if (mVideo3 != null) {
            mVideo3.setVideoId(R.raw.vv_ai_por_step);
            mVideo3.resume();
            mVideo3.start();
            mVideo3.setVisibility(View.VISIBLE);
        }
        openThread();
    }

    protected void step1() {
        if (mActivity != null) {
            isShow = true;
            mActivity.setTextWhiteColor();
            mActivity.setTitleText(R.string.ai_portrait_title);
            mActivity.setIconWhite();
            mActivity.setTextINVISIBLE();
            GoCompare.setVisibility(View.GONE);
            GoStep.setVisibility(View.GONE);

            GoCompare1.setVisibility(View.VISIBLE);
            GoBack1.setVisibility(View.VISIBLE);
        }
        if (mVideo2 != null) {
            mVideo2.seekTo(0);
            mVideo2.pause();
            mVideo2.setVisibility(View.INVISIBLE);
        }
        cl_step.setVisibility(View.VISIBLE);
        if (mVideo3 != null) {
            mVideo3.setVideoId(R.raw.vv_ai_por_step);
            mVideo3.resume();
            mVideo3.start();
            mVideo3.setVisibility(View.VISIBLE);
        }
        openThread();
    }

    @Override
    protected void pause() {
        initFragmentViews();
        if (mVideo1 != null) {
            mVideo1.suspend();
        }
        if (mVideo2 != null) {
            mVideo2.suspend();
        }

    }

    /**
     * 初始化状态
     */
    private void initFragmentViews() {
        stopThread();
        isShow = true;
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
        if (mVideo2 != null) {
            mVideo2.seekTo(0);
            mVideo2.pause();
            mVideo2.setVisibility(View.INVISIBLE);

        }
        if (mVideo3 != null) {
            mVideo3.seekTo(0);
            mVideo3.pause();
            mVideo3.setVisibility(View.INVISIBLE);

        }
        if (viewPager != null) {
            viewPager.setVisibility(View.GONE);
            viewPager.setCurrentItem(0, false);
        }
        if (cl_step != null) {
            cl_step.setVisibility(View.GONE);
        }
        if (cl_camera_bt1 != null) {
            cl_camera_bt1.setVisibility(View.GONE);
        }
        if (cl_camera_bt2 != null) {
            cl_camera_bt2.setVisibility(View.GONE);
        }
        if (bgCamera != null) {
            bgCamera.setAlpha(1f);
            bgCamera.setVisibility(View.GONE);
        }
        if (shade != null) {
            shade.setVisibility(View.GONE);
        }
        if (cl_compare != null) {
            cl_compare.setVisibility(View.GONE);
        }
        if (GoCompare != null) {
            GoCompare.setVisibility(View.VISIBLE);
        }
        if (GoStep != null) {
            GoStep.setVisibility(View.VISIBLE);
        }
        if (mActivity != null && mActivity.getCurPosition() == 12) {
            mActivity.setTextVISIBLE();
            mActivity.setTextWhiteColor();
            mActivity.setTitleText(R.string.ai_portrait_title);
            mActivity.setIconWhite();

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
        if (mVideo2 != null) {
            mVideo2.suspend();
        }

    }

    @Override
    public void pauseVideo() {
        super.pauseVideo();
        if (mVideo != null) {
            mVideo.pause();
        }
        if (mVideo1 != null) {
            mVideo1.pause();
        }
        if (mVideo2 != null) {
            mVideo2.pause();
        }

    }

    @Override
    public void videoRefresh() {
        super.videoRefresh();
        if (mVideo != null) {
            mVideo.seekTo(0);
        }
        if (mVideo1 != null) {
            mVideo1.seekTo(0);
        }
        if (mVideo2 != null) {
            mVideo2.seekTo(0);
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
        if (mVideo2 != null) {
            mVideo2.stopPlayback();
            mVideo2 = null;
        }
        mAdapter = null;
        viewPager = null;
        viewPagerIndicator = null;
        cl_compare = null;
        GoCompare = null;
        GoBack = null;
    }
}