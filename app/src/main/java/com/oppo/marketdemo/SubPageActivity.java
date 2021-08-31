package com.oppo.marketdemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.oppo.marketdemo.adapter.SlideViewPagerAdapter;
import com.oppo.marketdemo.base.BaseMenuActivity;
import com.oppo.marketdemo.custom.MenuView;
import com.oppo.marketdemo.custom.PointIndicatorView;
import com.oppo.marketdemo.custom.TouchListenerViewPager;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.globle.VApplication;
import com.oppo.marketdemo.utils.AnimationOppoUtils;
import com.oppo.marketdemo.utils.MyPageTransformer;
import com.oppo.marketdemo.utils.StringsData;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2019/12/31 15:02
 * Description: Mufasa 二级页Activity
 * 修改文案颜色需要改到2个位置 setMovingTextColor 和  refreshTextColor
 */

public class SubPageActivity extends BaseMenuActivity {

    public static final String VIEW_PAGER_POSITION = "VIEW_PAGER_POSITION";
    public static final String CUR_POSITION = "CUR_POSITION";

    private Handler mHandler;
    private ValueAnimator mValueAnimator;
    private SlideViewPagerAdapter mSlideViewPagerAdapter;

    private TouchListenerViewPager mViewPager;
    private ImageView ivBack, ivArrows1, ivArrows2;
    private FrameLayout flBack;
    private PointIndicatorView mPointIndicator;

    private int[] pointPosition = new int[]{1, 7, 17, 11, 21};
    private int oldPosition;
    private int curPosition;
    private boolean viewPagerIsChange;


    private View viewText;
    private TypefaceTextView viewTitle;
    private TypefaceTextView viewSmallTitle;
    private TypefaceTextView viewContent;

    private View viewText1;
    private TypefaceTextView viewTitle1;
    private TypefaceTextView viewSmallTitle1;
    private TypefaceTextView viewContent1;

    private TypefaceTextView viewRemake;
    private TypefaceTextView viewRemake1;

    private ConstraintLayout llTip;
    private ObjectAnimator objectAnimator;
    private MenuView menuView;
    private ImageView ivTips;
    private FrameLayout flMenuView;
    private ImageView ivTrans, ivMenuBg;
    public DrawerLayout drawerLayout;
    private int mViewPagerIndex = 0;
    private boolean isFirstVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sub_page;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void init() {
        oldPosition = 0;
        curPosition = pointPosition[getIntent().getIntExtra(VIEW_PAGER_POSITION, 0)];
        curPosition = getIntent().getIntExtra(CUR_POSITION, curPosition);

        drawerLayout = findViewById(R.id.drawer_layout);
        mViewPager = findViewById(R.id.view_pager);
        ivBack = findViewById(R.id.iv_back);
        flBack = findViewById(R.id.fl_back);
        flMenuView = findViewById(R.id.fl_menuView);
        ivTrans = findViewById(R.id.iv_transparent);
        ivArrows1 = findViewById(R.id.iv_arrows1);
        ivArrows2 = findViewById(R.id.iv_arrows2);
        ivMenuBg = findViewById(R.id.iv_bg);
        mPointIndicator = findViewById(R.id.sub_indicator);
        menuView = findViewById(R.id.menuView);
        llTip = findViewById(R.id.ll_tip);
        ivTips = findViewById(R.id.iv_tips);

        initText();

        viewTitle.setText(StringsData.Titles[curPosition]);
//        viewSmallTitle.setText(StringsData.SmallTitles[curPosition]);
        viewContent.setText(StringsData.Contents[curPosition]);
        setText(curPosition);

        mSlideViewPagerAdapter = new SlideViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSlideViewPagerAdapter);
        mViewPager.setPageTransformer(true, new MyPageTransformer());

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("OPPPPP","？？？？？？？"+positionOffset +"::"+positionOffsetPixels);
                if (mViewPagerIndex == position && positionOffset != 0.0) {
                    if (position != 25 && position != 0) {
                        setUpText(mViewPagerIndex, isFirstVisible);
                        setMovingTextColor(mViewPagerIndex + 1, isFirstVisible);
                    }
                    if (isFirstVisible) {
                        LFirstViewVisible(positionOffset);
                    } else {
                        LFirstViewGone(positionOffset);
                    }
                } else {
                    if (positionOffset != 0.0) {
                        if (position != 25) {
                            setDownText(mViewPagerIndex, isFirstVisible);
                            setMovingTextColor(mViewPagerIndex - 1, isFirstVisible);
                        }

                        if (isFirstVisible) {
                            RFirstViewVisible(positionOffset);
                        } else {
                            RFirstViewGone(positionOffset);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                viewPagerIsChange = true;
                showIcon();
                refreshIconColor();
                refreshArrowsColor();
                refreshTextColor();
                refreshIndicator(curPosition);

                if (mHandler != null) {
                    mHandler.removeMessages(2);
                }
                if (oldPosition >= 0 && oldPosition < mSlideViewPagerAdapter.getCount()) {
                    mSlideViewPagerAdapter.getItem(oldPosition).pauseVideo();
                }
                for (int i = 0; i < mSlideViewPagerAdapter.getCount() - 1; i++) {
                    if (i < curPosition - 1 || i > curPosition + 1) {
                        mSlideViewPagerAdapter.getItem(i).releaseVideo();
                    }
                }
                if (curPosition == 0) {
                    curPosition = mSlideViewPagerAdapter.getCount() - 2;
                    return;
                }
                if (curPosition == mSlideViewPagerAdapter.getCount() - 1) {
                    curPosition = 1;
                    return;
                }
                oldPosition = curPosition;
                setTextVISIBLE();
                setAllText(curPosition);
                VApplication.changeInt = curPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1) {

                    mViewPagerIndex = mViewPager.getCurrentItem();
                    //todo 有问题就看这来改
                    isFirstVisible = Math.abs(viewTitle.getTranslationY()) < viewTitle.getMeasuredHeight();
                }

                if (state == 2) {
                    mViewPagerIndex = curPosition;

                }
                if (state == ViewPager.SCROLL_STATE_IDLE && viewPagerIsChange) {
                    if (mHandler != null) {
                        mHandler.removeMessages(2);
                        Message message = new Message();
                        message.what = 2;
                        message.arg1 = curPosition;
                        mHandler.sendMessageDelayed(message, 500);
                    }
                    if (curPosition - 1 >= 0) {
                        mSlideViewPagerAdapter.getItem(curPosition - 1).videoRefresh();
                        mSlideViewPagerAdapter.getItem(curPosition - 1).pauseVideo();
                    }
                    if (curPosition + 1 < mSlideViewPagerAdapter.getCount()) {
                        mSlideViewPagerAdapter.getItem(curPosition + 1).videoRefresh();
                        mSlideViewPagerAdapter.getItem(curPosition + 1).pauseVideo();
                    }
                    viewPagerIsChange = false;
                    if (mViewPager.getCurrentItem() != curPosition) {
                        mViewPager.setCurrentItem(curPosition, false);
                    }
                    View page = mSlideViewPagerAdapter.getItem(curPosition).getView();
                    if (page != null) {
                        page.setTranslationY(0f);
                        page.setTranslationZ(0f);
                        page.setAlpha(1f);
                    }
                    View page2 = mSlideViewPagerAdapter.getItem((curPosition + 1) % mSlideViewPagerAdapter.getCount()).getView();
                    if (page2 != null) {
                        page2.setTranslationY(0f);
                        page2.setTranslationZ(0f);
                        page2.setAlpha(1f);
                    }
                    View page3 = mSlideViewPagerAdapter.getItem((curPosition - 1) % mSlideViewPagerAdapter.getCount()).getView();
                    if (page3 != null) {
                        page3.setTranslationY(0f);
                        page3.setTranslationZ(0f);
                        page3.setAlpha(1f);
                    }
                }
            }
        });
        mViewPager.setTouchListener(new TouchListenerViewPager.TouchListener() {
            @Override
            public void touchDown() {
                if (mHandler != null) {
                    mHandler.removeMessages(0);
                }
                ivArrows1.setVisibility(View.GONE);
                ivArrows2.setVisibility(View.GONE);
            }

            @Override
            public void touchUp() {
                if (mHandler != null) {
                    mHandler.removeMessages(0);
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                }
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 0:
                        ivArrows1.setVisibility(View.VISIBLE);
                        ivArrows2.setVisibility(View.VISIBLE);
                        if (mValueAnimator != null) {
                            mValueAnimator.cancel();
                            mValueAnimator.start();
                        }
                        break;
                    case 1:
                        if (!mSlideViewPagerAdapter.getItem(curPosition).isCanChange()) {
                            mSlideViewPagerAdapter.getItem(curPosition).setCanChange(true);
                            mSlideViewPagerAdapter.getItem(curPosition).onResume();
                        }
                        break;
                    case 2:
                        for (int i = 0; i < mSlideViewPagerAdapter.getCount() - 1; i++) {
                            if (i != curPosition && i != msg.arg1) {
                                mSlideViewPagerAdapter.getItem(i).releaseVideo();
                            }
                        }
                        break;
                }
            }
        };
        mValueAnimator = ValueAnimator.ofFloat(0f, 1.5f);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (ivArrows1 != null) {
                    ivArrows1.setAlpha(0f);
                    ivArrows1.setTranslationY(0f);
                }
                if (ivArrows2 != null) {
                    ivArrows2.setAlpha(0f);
                    ivArrows2.setTranslationY(0f);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (ivArrows1 != null) {
                    ivArrows1.setAlpha(0f);
                    ivArrows1.setTranslationY(0f);
                }
                if (ivArrows2 != null) {
                    ivArrows2.setAlpha(0f);
                    ivArrows2.setTranslationY(0f);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (ivArrows1 != null) {
                    ivArrows1.setAlpha(0f);
                    ivArrows1.setTranslationY(0f);
                }
                if (ivArrows2 != null) {
                    ivArrows2.setAlpha(0f);
                    ivArrows2.setTranslationY(0f);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float temp;
                if (ivArrows1 != null) {
                    if (value < 0.1f) {
                        ivArrows1.setAlpha(0f);
                        ivArrows1.setTranslationY(0f);
                    } else if (value < 0.2f) {
                        temp = (value - 0.1f) / 0.1f / 3f;
                        ivArrows1.setAlpha(temp);
                        ivArrows1.setTranslationY(0f);
                    } else if (value < 0.8f) {
                        temp = (value - 0.2f) / 0.6f;
                        ivArrows1.setTranslationY(-30f * temp);
                        if (value < 0.4f) {
                            temp = (value - 0.1f) / 0.3f;
                            ivArrows1.setAlpha(temp);
                        } else {
                            ivArrows1.setAlpha(1f);
                        }
                    } else if (value < 0.9f) {
                        temp = (value - 0.8f) / 0.1f;
                        ivArrows1.setAlpha(1f - temp);
                        ivArrows1.setTranslationY(-30f);
                    } else {
                        ivArrows1.setAlpha(0f);
                        ivArrows1.setTranslationY(-30f);
                    }
                }
                if (ivArrows2 != null) {
                    if (value < 0.6f) {
                        ivArrows2.setAlpha(0f);
                        ivArrows2.setTranslationY(0f);
                    } else if (value < 0.7f) {
                        temp = (value - 0.6f) / 0.1f / 3f;
                        ivArrows2.setAlpha(temp);
                        ivArrows2.setTranslationY(0f);
                    } else if (value < 1.3f) {
                        temp = (value - 0.7f) / 0.6f;
                        ivArrows2.setTranslationY(-30f * temp);
                        if (value < 0.9f) {
                            temp = (value - 0.6f) / 0.3f;
                            ivArrows2.setAlpha(temp);
                        } else {
                            ivArrows2.setAlpha(1f);
                        }
                    } else if (value < 1.4f) {
                        temp = (value - 1.3f) / 0.1f;
                        ivArrows2.setAlpha(1f - temp);
                        ivArrows2.setTranslationY(-30f);
                    } else {
                        ivArrows2.setAlpha(0f);
                        ivArrows2.setTranslationY(-30f);
                    }
                }
            }
        });
        if (VApplication.isFirst) {
            llTip.setVisibility(View.VISIBLE);
            mSlideViewPagerAdapter.getItem(curPosition).setCanChange(false);
        }

        mViewPager.setCurrentItem(curPosition);
        flBack.setOnClickListener(this);
        ivTrans.setOnClickListener(this);
        flMenuView.setOnClickListener(this);
    }

    /**
     * 设置动画
     */
    private void setTipAnimation() {
        if (!VApplication.isFirst) {
            return;
        }
        if (null != llTip) {
            llTip.setVisibility(View.VISIBLE);
            objectAnimator = AnimationOppoUtils.setTraintAnimationMethods(ivTips);
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    llTip.setVisibility(View.GONE);
                    VApplication.isFirst = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            objectAnimator.start();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        curPosition = pointPosition[intent.getIntExtra(VIEW_PAGER_POSITION, 0)];
        curPosition = intent.getIntExtra(CUR_POSITION, curPosition);
        if (Math.abs(curPosition - oldPosition) == 1) {
            isFirstVisible = Math.abs(viewTitle.getTranslationY()) < viewTitle.getMeasuredHeight();
            mViewPager.setCurrentItem(curPosition);
        } else {
            mViewPager.setCurrentItem(curPosition, false);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            if (!mSlideViewPagerAdapter.getItem(curPosition).isCanChange()) {
                if (mHandler != null) {
                    setTipAnimation();
                    mHandler.removeMessages(1);
                    mHandler.sendEmptyMessageDelayed(1, 1500);

                }
            }
            mViewPager.setOffscreenPageLimit(2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshIconColor();
        refreshArrowsColor();
        refreshTextColor();
        refreshIndicator(curPosition);
        setTextVISIBLE();

        if (mHandler != null) {
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!getPowerState()) {
            if (null != drawerLayout && drawerLayout.isDrawerOpen(GravityCompat.END)) {
                setMenuClick(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (objectAnimator != null) {
            objectAnimator.cancel();
            objectAnimator = null;
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.fl_back:
                if (!mSlideViewPagerAdapter.getItem(curPosition).onActivityClick(view)) {
                    onBackPressed();
                }
                break;
            case R.id.fl_menuView:
            case R.id.iv_transparent:
                if (null == drawerLayout) {
                    return;
                }
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    setMenuClick(false);
                } else {
                    clickOpenMenu(curPosition);
                }
                break;
            default:
        }
    }

    @Override
    public int getCurPosition() {
        return curPosition;
    }

    /**
     * 侧边栏跳转
     *
     * @param position
     */
    @Override
    protected void onClickMenu(final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < mSlideViewPagerAdapter.getCount() - 1; i++) {
                                if (i < curPosition || i > curPosition) {
                                    mSlideViewPagerAdapter.getItem(i).releaseVideo();
                                }
                            }
                            mViewPager.setCurrentItem(position, false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    protected void initText() {
        viewText = findViewById(R.id.text_layout);
        viewTitle = findViewById(R.id.text_title);
        viewSmallTitle = findViewById(R.id.text_small_title);
        viewContent = findViewById(R.id.text_content);
        viewRemake = findViewById(R.id.text_remake);

        viewText1 = findViewById(R.id.text_layout1);
        viewTitle1 = findViewById(R.id.text_title1);
        viewSmallTitle1 = findViewById(R.id.text_small_title1);
        viewContent1 = findViewById(R.id.text_content1);
        viewRemake1 = findViewById(R.id.text_remake1);
    }


    private void setUpText(int pos, boolean isfirst) {
        if (!isfirst) {
            setTextHide();
            setText(pos + 1);
        } else {
            setText1Hide();
            setText1(pos + 1);
        }

    }

    private void setDownText(int pos, boolean isfirst) {
        if (!isfirst) {
            setTextHide();
            setText(pos - 1);
        } else {
            setText1Hide();
            setText1(pos - 1);
        }
    }

    //向上划的时候
    protected void LFirstViewVisible(float positionOffset) {
        Log.d("DashIndicator", "positionOffset:" + positionOffset);
        viewTitle1.setTranslationY(viewTitle1.getMeasuredHeight());
        viewSmallTitle1.setTranslationY(viewSmallTitle1.getMeasuredHeight());
        viewContent1.setTranslationY(viewContent1.getMeasuredHeight());
        viewRemake1.setTranslationY(viewRemake1.getMeasuredHeight());
        viewText1.setVisibility(View.VISIBLE);


        viewTitle.setTranslationY(-viewTitle.getMeasuredHeight() * positionOffset * 3);
        viewSmallTitle.setTranslationY(-viewSmallTitle.getMeasuredHeight() * positionOffset * 2);
        viewContent.setTranslationY(-viewContent.getMeasuredHeight() * positionOffset * 2);
        viewRemake.setTranslationY(-viewRemake.getMeasuredHeight() * positionOffset * 2);
        if (positionOffset >= 0.33) {
            setText1Show();
            viewTitle1.setTranslationY(-viewTitle1.getMeasuredHeight() * (positionOffset - 1f) * 9 / 10);
            viewSmallTitle1.setTranslationY(-viewSmallTitle1.getMeasuredHeight() * (positionOffset - 1f) * 11 / 10);
            viewContent1.setTranslationY(-viewContent1.getMeasuredHeight() * (positionOffset - 1f));
            viewRemake1.setTranslationY(-viewRemake1.getMeasuredHeight() * (positionOffset - 1f));
        }

    }

    protected void LFirstViewGone(float positionOffset) {
        Log.d("DashIndicator", "positionOffset:" + positionOffset);
        viewTitle.setTranslationY(viewTitle.getMeasuredHeight());
        viewSmallTitle.setTranslationY(viewSmallTitle.getMeasuredHeight());
        viewContent.setTranslationY(viewContent.getMeasuredHeight());
        viewRemake.setTranslationY(viewRemake.getMeasuredHeight());
        viewText.setVisibility(View.VISIBLE);


        viewTitle1.setTranslationY(-viewTitle1.getMeasuredHeight() * positionOffset * 3);
        viewSmallTitle1.setTranslationY(-viewSmallTitle1.getMeasuredHeight() * positionOffset * 2);
        viewContent1.setTranslationY(-viewContent1.getMeasuredHeight() * positionOffset * 2);
        viewRemake1.setTranslationY(-viewRemake1.getMeasuredHeight() * positionOffset * 2);
        if (positionOffset >= 0.33) {
            setTextShow();
            viewTitle.setTranslationY(-viewTitle.getMeasuredHeight() * (positionOffset - 1f) * 9 / 10);
            viewSmallTitle.setTranslationY(-viewSmallTitle.getMeasuredHeight() * (positionOffset - 1f) * 11 / 10);
            viewContent.setTranslationY(-viewContent.getMeasuredHeight() * (positionOffset - 1f));
            viewRemake.setTranslationY(-viewRemake.getMeasuredHeight() * (positionOffset - 1f));
        }
    }

    //向下划的时候
    protected void RFirstViewVisible(float positionOffset) {
        if (positionOffset != 0.0) {
            Log.d("DashIndicator", "正在向右滑动");
            Log.d("DashIndicator", "positionOffset:" + positionOffset);
            viewTitle1.setTranslationY(-viewTitle1.getMeasuredHeight());
            viewSmallTitle1.setTranslationY(-viewSmallTitle1.getMeasuredHeight());
            viewContent1.setTranslationY(-viewContent1.getMeasuredHeight());
            viewRemake1.setTranslationY(-viewRemake1.getMeasuredHeight());
            viewText1.setVisibility(View.VISIBLE);

            viewTitle.setTranslationY(viewTitle.getMeasuredHeight() * (1 - positionOffset) * 3);
            viewSmallTitle.setTranslationY(viewSmallTitle.getMeasuredHeight() * (1 - positionOffset) * 2);
            viewContent.setTranslationY(viewContent.getMeasuredHeight() * (1 - positionOffset) * 2);
            viewRemake.setTranslationY(viewRemake.getMeasuredHeight() * (1 - positionOffset) * 2);
            if (positionOffset <= 0.66) {
                setText1Show();
                viewTitle1.setTranslationY(viewTitle1.getMeasuredHeight() * (-positionOffset) * 9 / 10);
                viewSmallTitle1.setTranslationY(viewSmallTitle1.getMeasuredHeight() * (-positionOffset) * 11 / 10);
                viewContent1.setTranslationY(viewContent1.getMeasuredHeight() * (-positionOffset));
                viewRemake1.setTranslationY(viewRemake1.getMeasuredHeight() * (-positionOffset));
            }
        }
    }

    protected void RFirstViewGone(float positionOffset) {
        if (positionOffset != 0.0) {
            Log.d("DashIndicator", "正在向右滑动");
            Log.d("DashIndicator", "positionOffset:" + positionOffset);
            viewTitle.setTranslationY(-viewTitle.getMeasuredHeight());
            viewSmallTitle.setTranslationY(-viewSmallTitle.getMeasuredHeight());
            viewContent.setTranslationY(-viewContent.getMeasuredHeight());
            viewRemake.setTranslationY(-viewRemake.getMeasuredHeight());


            viewTitle1.setTranslationY(viewTitle1.getMeasuredHeight() * (1 - positionOffset) * 3);
            viewSmallTitle1.setTranslationY(viewSmallTitle1.getMeasuredHeight() * (1 - positionOffset) * 2);
            viewContent1.setTranslationY(viewContent1.getMeasuredHeight() * (1 - positionOffset) * 2);
            viewRemake1.setTranslationY(viewRemake1.getMeasuredHeight() * (1 - positionOffset) * 2);
            if (positionOffset <= 0.66) {
                setTextShow();
                viewTitle.setTranslationY(viewTitle.getMeasuredHeight() * (-positionOffset) * 9 / 10);
                viewSmallTitle.setTranslationY(viewSmallTitle.getMeasuredHeight() * (-positionOffset) * 11 / 10);
                viewContent.setTranslationY(viewContent.getMeasuredHeight() * (-positionOffset));
                viewRemake.setTranslationY(viewRemake.getMeasuredHeight() * (-positionOffset));
            }
        }
    }

    private void refreshIndicator(int pos) {
        //过度页隐藏
        if (pos == 1 || pos == 7 || pos == 11 || pos == 17 || pos == 21 || pos == 24) {
            mPointIndicator.setVisibility(View.GONE);
        } else {
            mPointIndicator.setVisibility(View.VISIBLE);
        }
        if (pos > 1 && pos <= 6) {
            mPointIndicator.setzise(5);
            mPointIndicator.setpos(pos - 2);
        }
        if (pos > 7 && pos <= 10) {
            mPointIndicator.setzise(3);
            mPointIndicator.setpos(pos - 8);
        }
        if (pos > 11 && pos <= 16) {
            mPointIndicator.setzise(5);
            mPointIndicator.setpos(pos - 12);
        }
        if (pos > 17 && pos <= 20) {
            mPointIndicator.setzise(3);
            mPointIndicator.setpos(pos - 18);
        }
        if (pos > 21 && pos <= 23) {
            mPointIndicator.setzise(2);
            mPointIndicator.setpos(pos - 22);
        }
        if (pos == 0) {
            mPointIndicator.setzise(2);
            mPointIndicator.setpos(2);
        }

    }

    private void refreshTextColor() {
        switch (curPosition) {
            case 0:
            case 6:
            case 8:
            case 9:
            case 10:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
                setTextBlackColor();
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 21:
            case 25:
                setTextWhiteColor();
                break;
            default:
        }
    }

    public void setMovingTextColor(int pos, boolean isFirstVisible) {
        switch (pos) {
            case 0:
            case 6:
            case 8:
            case 9:
            case 10:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
                setTextBlackColor(isFirstVisible);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 21:
            case 25:
                setTextWhiteColor(isFirstVisible);
                break;
            default:
        }
    }

    private void refreshIconColor() {
        switch (curPosition) {
            case 0:
            case 6:
            case 8:
            case 9:
            case 10:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
                setIconBlack();

                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 21:
            case 25:
                setIconWhite();
                break;
            default:
        }
    }

    private void refreshArrowsColor() {
        switch (curPosition) {
            case 0:
            case 6:
            case 8:
            case 9:
            case 10:
            case 16:
            case 18:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:

                setArrowsBlack();
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 21:
            case 25:
                setArrowsWhite();
                break;
            default:
        }
    }


    @Override
    public void showIcon() {
        flBack.setVisibility(View.VISIBLE);
        mPointIndicator.setVisibility(View.VISIBLE);
        if (!drawerLayout.isDrawerOpen(GravityCompat.END)){
            flMenuView.setVisibility(View.VISIBLE);
            ivMenuBg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideIcon() {
        flBack.setVisibility(View.GONE);
        mPointIndicator.setVisibility(View.GONE);
        if (!drawerLayout.isDrawerOpen(GravityCompat.END)){
            flMenuView.setVisibility(View.GONE);
            ivMenuBg.setVisibility(View.GONE);
        }
    }

    public void setProgressViewWhite() {
        mPointIndicator.setWhite();
    }

    public void setProgressViewBlack() {
        mPointIndicator.setBlack();
    }

    public void setIconWhite() {
        VApplication.sideIsWhite = true;
        ivBack.setImageResource(R.mipmap.icon_sub_back_white);
        setArrowsWhite();
        setMenuWrite(true);
        mPointIndicator.setWhite();

        if (!drawerLayout.isDrawerOpen(GravityCompat.END)){
            ivMenuBg.setImageResource(R.drawable.side_open);
            menuView.setColorState(true);
            menuView.invalidate();
        }
    }

    public void setIconBlack() {
        VApplication.sideIsWhite = false;
        ivBack.setImageResource(R.mipmap.icon_sub_back_black);
        setArrowsBlack();
        setMenuWrite(false);
        mPointIndicator.setBlack();

        if (!drawerLayout.isDrawerOpen(GravityCompat.END)){
            ivMenuBg.setImageResource(R.drawable.side_open_black);
            menuView.setColorState(false);
            menuView.invalidate();
        }
    }

    public void setArrowsWhite() {
        ivArrows1.setImageResource(R.mipmap.icon_arrows_white);
        ivArrows2.setImageResource(R.mipmap.icon_arrows_white);
    }

    public void setArrowsBlack() {
        ivArrows1.setImageResource(R.mipmap.icon_arrows_black);
        ivArrows2.setImageResource(R.mipmap.icon_arrows_black);
    }

    public void setArrowsHide() {
        flBack.setVisibility(View.GONE);
        mPointIndicator.setVisibility(View.GONE);
        if (!drawerLayout.isDrawerOpen(GravityCompat.END)){
            flMenuView.setVisibility(View.GONE);
            ivMenuBg.setVisibility(View.GONE);
        }
    }

    public void setArrowsDispaly() {
        flBack.setVisibility(View.VISIBLE);
        mPointIndicator.setVisibility(View.VISIBLE);
        if (!drawerLayout.isDrawerOpen(GravityCompat.END)){
            flMenuView.setVisibility(View.VISIBLE);
            ivMenuBg.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 字体修改的一些方法
     */
    public void setTextINVISIBLE() {
        if (Math.abs(viewTitle.getTranslationY()) < viewTitle.getMeasuredHeight()) {
            setTextHide();
        } else {
            setText1Hide();
        }
    }

    public void setTextVISIBLE() {
        if (Math.abs(viewTitle.getTranslationY()) < viewTitle.getMeasuredHeight()) {
            setTextShow();
        } else {
            setText1Show();
        }
    }

    public void setTextHide() {
        viewTitle.setVisibility(View.INVISIBLE);
        viewSmallTitle.setVisibility(View.INVISIBLE);
        viewContent.setVisibility(View.INVISIBLE);
        viewRemake.setVisibility(View.INVISIBLE);
    }

    public void setText1Hide() {
        viewTitle1.setVisibility(View.INVISIBLE);
        viewSmallTitle1.setVisibility(View.INVISIBLE);
        viewContent1.setVisibility(View.INVISIBLE);
        viewRemake1.setVisibility(View.INVISIBLE);
    }

    public void setTextShow() {
        viewTitle.setVisibility(View.VISIBLE);
        viewSmallTitle.setVisibility(View.VISIBLE);
        viewContent.setVisibility(View.VISIBLE);
        viewRemake.setVisibility(View.VISIBLE);
    }

    public void setText1Show() {
        viewTitle1.setVisibility(View.VISIBLE);
        viewSmallTitle1.setVisibility(View.VISIBLE);
        viewContent1.setVisibility(View.VISIBLE);
        viewRemake1.setVisibility(View.VISIBLE);
    }


    //小标题隐藏使用吧text设置为空字符串
    public void setSmallTitleGone() {
        viewSmallTitle.setText(R.string.string_null);
        viewSmallTitle1.setText(R.string.string_null);
    }

    public void setTextWhiteColor() {

        viewTitle.setTextColor(getColor(R.color.colorWhite));
        viewTitle1.setTextColor(getColor(R.color.colorWhite));
        viewSmallTitle.setTextColor(getColor(R.color.colorRedC52f13));
        viewSmallTitle1.setTextColor(getColor(R.color.colorRedC52f13));
        viewContent.setTextColor(getColor(R.color.colorWhite));
        viewContent1.setTextColor(getColor(R.color.colorWhite));
        viewRemake.setTextColor(getColor(R.color.colorWhite));
        viewRemake1.setTextColor(getColor(R.color.colorWhite));

    }

    public void setTextBlackColor() {
        viewTitle.setTextColor(getColor(R.color.colorBlack));
        viewTitle1.setTextColor(getColor(R.color.colorBlack));
        viewSmallTitle.setTextColor(getColor(R.color.colorRedC52f13));
        viewSmallTitle1.setTextColor(getColor(R.color.colorRedC52f13));
        viewContent.setTextColor(getColor(R.color.colorBlack));
        viewContent1.setTextColor(getColor(R.color.colorBlack));
        viewRemake.setTextColor(getColor(R.color.color868686));
        viewRemake1.setTextColor(getColor(R.color.color868686));
    }

    public void setTextWhiteColor(boolean isFirstVisible) {
        if (!isFirstVisible) {
            viewTitle.setTextColor(getColor(R.color.colorWhite));
            viewSmallTitle.setTextColor(getColor(R.color.colorRedC52f13));
            viewContent.setTextColor(getColor(R.color.colorWhite));
            viewRemake.setTextColor(getColor(R.color.colorWhite));
        } else {
            viewTitle1.setTextColor(getColor(R.color.colorWhite));
            viewSmallTitle1.setTextColor(getColor(R.color.colorRedC52f13));
            viewContent1.setTextColor(getColor(R.color.colorWhite));
            viewRemake1.setTextColor(getColor(R.color.colorWhite));
        }

    }

    public void setTextBlackColor(boolean isFirstVisible) {
        if (!isFirstVisible) {
            viewTitle.setTextColor(getColor(R.color.colorBlack));
            viewSmallTitle.setTextColor(getColor(R.color.colorRedC52f13));
            viewContent.setTextColor(getColor(R.color.color575757));
            viewRemake.setTextColor(getColor(R.color.color868686));

        } else {
            viewTitle1.setTextColor(getColor(R.color.colorBlack));
            viewSmallTitle1.setTextColor(getColor(R.color.colorRedC52f13));
            viewContent1.setTextColor(getColor(R.color.color575757));
            viewRemake1.setTextColor(getColor(R.color.color868686));
        }
    }

    //todo 切换页面修改文字和颜色的时候需要加判断当前的位置是否是当前pos
    public void setTitleText(int resid) {
        if (Math.abs(viewTitle.getTranslationY()) < viewTitle.getMeasuredHeight()) {
            viewTitle.setText(resid);
        } else {
            viewTitle1.setText(resid);
        }
    }

    public void setSmallTitleText(int resid) {
        viewSmallTitle.setText(resid);
        viewSmallTitle1.setText(resid);
    }

    public void setContentText(int resid) {
        if (Math.abs(viewTitle.getTranslationY()) < viewTitle.getMeasuredHeight()) {
            viewContent.setText(resid);
        } else {
            viewContent1.setText(resid);
        }
    }

    public void setTitleRemake(int resid) {
        if (Math.abs(viewTitle.getTranslationY()) < viewTitle.getMeasuredHeight()) {
            viewRemake.setText(resid);
        } else {
            viewRemake1.setText(resid);
        }
    }

    public void setAllText(int pos) {
        Log.d("OPPPPP", "POS" + viewTitle.getTranslationY() + "Hight :" + viewTitle.getMeasuredHeight());
        if (Math.abs(viewTitle.getTranslationY()) < viewTitle.getMeasuredHeight()) {
            setText(pos);
        } else {
            setText1(pos);
        }


    }

    public void setText(int pos) {

        viewTitle.setText(StringsData.Titles[pos]);
//        viewSmallTitle.setText(StringsData.SmallTitles[pos]);
        viewContent.setText(StringsData.Contents[pos]);
        if (pos == 2) {
            viewRemake.setText(R.string.triple_ano_remake);
        } else if (pos == 3) {
            viewRemake.setText(R.string.hand_remake);
        } else
        if (pos == 22) {
            viewRemake.setText(R.string.watch_remake);
        } else if (pos == 23) {
            viewRemake.setText(R.string.headset_remake);

        } else {
            viewRemake.setText(R.string.string_null);
        }
    }

    public void setText1(int pos) {
        viewTitle1.setText(StringsData.Titles[pos]);
//        viewSmallTitle1.setText(StringsData.SmallTitles[pos]);
        viewContent1.setText(StringsData.Contents[pos]);
        if (pos == 2) {
            viewRemake1.setText(R.string.triple_ano_remake);
        } else if (pos == 3) {
            viewRemake1.setText(R.string.hand_remake);
        } else if (pos == 22) {
            viewRemake1.setText(R.string.watch_remake);
        } else if (pos == 23) {
            viewRemake1.setText(R.string.headset_remake);

        } else {
            viewRemake1.setText(R.string.string_null);
        }
    }

    private boolean getPowerState() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        return isScreenOn;
    }
}