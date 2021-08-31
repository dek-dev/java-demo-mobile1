package com.oppo.marketdemo.fragments.system;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.adapter.ViewsAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.TextureVideoView;
import com.oppo.marketdemo.custom.ViewPagerIndicator;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.CameraUtil;


import java.util.ArrayList;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/7 10:50
 * Description:超省电模式
 */
public class PowerSavingFragment extends BaseFragment {

    private Thread mThread;

    private ConstraintLayout textlayout;
    private ViewPager viewPager;
    private View view1, view2;

    private ViewPagerIndicator viewPagerIndicator;
    private ViewsAdapter mAdapter;
    private TextureVideoView mVideo0, mVideo1, mVideo2;
    private TypefaceTextView mOpenButton, mOpenButton1, mCompare, mChatEnd;
    private ImageView mBack;
    private LinearLayout llback, ll_main;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_power_saving;
    }

    @Override
    protected void initViews(View mRootView) {

        textlayout = mRootView.findViewById(R.id.power_text);
        viewPager = mRootView.findViewById(R.id.view_page);
        viewPagerIndicator = mRootView.findViewById(R.id.view_pager_indicator);
        mOpenButton = mRootView.findViewById(R.id.open_button);
        mOpenButton1 = mRootView.findViewById(R.id.open_button1);
        mCompare = mRootView.findViewById(R.id.bt_compare);
        mBack = mRootView.findViewById(R.id.iv_upper_back);
        mVideo0 = mRootView.findViewById(R.id.texture_video_view2);
        llback = mRootView.findViewById(R.id.ll_button_two);
        ll_main = mRootView.findViewById(R.id.ll_button_one);


    }

    @Override
    protected void init() {
        mOpenButton.setOnClickListener(this);
        mOpenButton1.setOnClickListener(this);
        mCompare.setOnClickListener(this);
        mBack.setOnClickListener(this);
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.fragment_powersaving_layout1, null);
        view2 = lf.inflate(R.layout.fragment_powersaving_layout2, null);

        mVideo1 = view1.findViewById(R.id.texture_video_view1);
        mChatEnd = view1.findViewById(R.id.tv_chating);

        mVideo2 = view2.findViewById(R.id.texture_video_view2);

        ArrayList<View> mList = new ArrayList<>();
        mList.add(view1);
//        mList.add(view2);
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
                switch (position) {
                    case 0:
                        if (mVideo2 != null) {
                            mVideo2.seekTo(0);
                            mVideo2.pause();
                            mVideo2.setVisibility(View.INVISIBLE);

                        }
                        if (mVideo1 != null) {
                            mVideo1.start();
                            mVideo1.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 1:
                        if (mVideo1 != null) {
                            mVideo1.seekTo(0);
                            mVideo1.pause();
                            mVideo1.setVisibility(View.INVISIBLE);
                        }
                        if (mVideo2 != null) {
                            mVideo2.start();
                            mVideo2.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVideo1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mVideo1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideo1 != null && mVideo1.getVisibility() == View.VISIBLE) {
                            mChatEnd.setVisibility(View.GONE);
                            mVideo1.seekTo(0);
                            mVideo1.start();

                        }
                    }
                }, 2000);


            }
        });

        mVideo2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mVideo2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideo2 != null && mVideo1.getVisibility() == View.VISIBLE) {
                            mVideo2.seekTo(0);
                            mVideo2.start();
                        }
                    }
                }, 2000);

            }
        });

        mVideo0.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideo0.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideo0 != null && mVideo0.getVisibility() == View.VISIBLE) {
                            mVideo0.seekTo(0);
                            mVideo0.start();
                        }
                    }
                }, 1000);

            }
        });
    }

    @Override
    protected void start() {
        startThread();
    }

    private void startThread() {
        //设置主页面视频
        if (mVideo1 != null) {
            mVideo1.setVideoId(R.raw.vv_power_saving_chat);

        }
//        if (mVideo2 != null) {
//            mVideo2.setVideoId(R.raw.vv_power_saving_navigation);
//        }
        if (mVideo0 != null) {
            mVideo0.setVideoId(R.raw.vv_power_saving);
            mVideo0.start();
            mVideo0.setVisibility(View.VISIBLE);
        }

    }

    private void openThread() {
        stopThread();
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (mActivity != null) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mVideo1 != null && mVideo1.getCurrentPosition() > 5900 && mVideo1.getCurrentPosition() < 6000) {
                                        mChatEnd.setVisibility(View.VISIBLE);
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

    private void stopThread() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
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
        if (viewPager != null) {
            viewPager.setCurrentItem(0);
            viewPager.setVisibility(View.INVISIBLE);
        }

        if (llback != null) {
            llback.setVisibility(View.GONE);
        }
        if (ll_main != null) {
            ll_main.setVisibility(View.VISIBLE);
        }
        if (mVideo0 != null) {
            mVideo0.seekTo(0);
            mVideo0.pause();
            mVideo0.setVisibility(View.INVISIBLE);
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
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //超级省电(开/关)
            case R.id.bt_compare:
                if (mVideo0 != null) {
                    mVideo0.seekTo(0);
                    mVideo0.pause();
                    mVideo0.setVisibility(View.INVISIBLE);
                }
                ll_main.setVisibility(View.GONE);
                llback.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                mChatEnd.setVisibility(View.GONE);
                if (mVideo1 != null) {
                    mVideo1.seekTo(0);
                    mVideo1.start();
                    mVideo1.setVisibility(View.VISIBLE);
                }
                openThread();
                break;
            case R.id.iv_upper_back:
                initFragmentViews();
                if (mVideo0 != null) {
                    mVideo0.seekTo(0);
                    mVideo0.start();
                    mVideo0.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.open_button:
            case R.id.open_button1:

                Intent intent = CameraUtil.Saving();
                mActivity.startActivity(intent);
                break;
            default:
        }
    }

    @Override
    protected void destroyFragmentViews() {

        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }

        if (mVideo1 != null) {
            mVideo1.stopPlayback();
            mVideo1 = null;
        }
        if (mVideo2 != null) {
            mVideo2.stopPlayback();
            mVideo2 = null;
        }

    }


}