package com.oppo.marketdemo;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.oppo.marketdemo.adapter.SlideTwoPagerAdapter;
import com.oppo.marketdemo.base.BaseActivity;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.VerticalViewPager;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.fragments.twostage.TwoExperienceFragment;
import com.oppo.marketdemo.fragments.twostage.TwoCameraFragment;
import com.oppo.marketdemo.fragments.twostage.TwoIoTFragment;
import com.oppo.marketdemo.fragments.twostage.TwoPerformanceFragment;
import com.oppo.marketdemo.fragments.twostage.TwoDesignFragment;
import com.oppo.marketdemo.globle.VApplication;
import com.oppo.marketdemo.utils.AnimationOppoUtils;
import com.oppo.marketdemo.utils.ViewCenterUtils;

import java.util.ArrayList;

/**
 * @author Neo
 * 功能: 二级页面
 */
public class TwoMainStageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    VerticalViewPager mViewPage;
    ImageView ivUpglide;
    ImageView ivUpglide2;
    ConstraintLayout cl;
    TypefaceTextView tvTip;
    private ArrayList<BaseFragment> fragmentList;
    private SlideTwoPagerAdapter mSlideViewPagerAdapter;

    private TwoPerformanceFragment photoFragment;
    private TwoDesignFragment videoFragment;
    private TwoCameraFragment expFragment;
    private TwoExperienceFragment appearanceFragment;
    private TwoIoTFragment ioTFragment;
    private TwoPerformanceFragment photoFragment2;
    private TwoIoTFragment ioTFragment2;

    private int mCurrentPagePosition = FIRST_ITEM_INDEX;
    private static final int FIRST_ITEM_INDEX = 1;
    private static final int POINT_LENGTH = 5;
    public static boolean isFirst = true;
    private int oldPosition;
    private AnimatorSet animatorGlide;
    private Handler mHandler;
    private ImageView ivBack;
    private SubPageActivity subPageActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_stage_activity);
        init();
    }

    protected void init() {
        mViewPage = findViewById(R.id.view_page);
        ivUpglide = findViewById(R.id.iv_upglide);
        ivUpglide2 = findViewById(R.id.iv_upglide2);
        cl = findViewById(R.id.cl);
        tvTip = findViewById(R.id.tv_tip);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //揭露动画
        ViewCenterUtils.setActivityStartAnim(this, cl, getIntent());
        mCurrentPagePosition = getIntent().getIntExtra(MainActivity.PAGER_NUM, FIRST_ITEM_INDEX);
        oldPosition = mCurrentPagePosition;
        fragmentList = new ArrayList<>();

        ioTFragment2 = TwoIoTFragment.getInstance(true);
        photoFragment = TwoPerformanceFragment.getInstance(false);
        videoFragment = new TwoDesignFragment();
        expFragment = new TwoCameraFragment();
        appearanceFragment = new TwoExperienceFragment();
        ioTFragment = TwoIoTFragment.getInstance(false);
        photoFragment2 = TwoPerformanceFragment.getInstance(true);

        fragmentList.add(ioTFragment2);
        fragmentList.add(photoFragment);
        fragmentList.add(videoFragment);
        fragmentList.add(expFragment);
        fragmentList.add(appearanceFragment);
        fragmentList.add(ioTFragment);
        fragmentList.add(photoFragment2);

        mSlideViewPagerAdapter = new SlideTwoPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPage.setAdapter(mSlideViewPagerAdapter);
        mViewPage.setCurrentItem(mCurrentPagePosition, false);
        mViewPage.setOnPageChangeListener(this);
        mViewPage.setOffscreenPageLimit(4);
        animatorGlide = AnimationOppoUtils.setUpDownAnimationMethods(ivUpglide, ivUpglide2);
        delayedMethods(mCurrentPagePosition);
    }

    private void delayedMethods(final int pos) {
        if (pos == 0 || pos == 6) {
            return;
        }
        if (null == mHandler) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivUpglide.setVisibility(ViewPager.VISIBLE);
                ivUpglide2.setVisibility(ViewPager.VISIBLE);
                tvTip.setVisibility(ViewPager.VISIBLE);
                setBottomTipMethods(pos);
            }
        }, 800);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (VApplication.changeInt != VApplication.CHANGE_NULL && null != mViewPage) {
            setNowPagerMethods(VApplication.changeInt);
        }
    }

    /**
     * 设置当前页面
     */
    private void setNowPagerMethods(int nowPageInt) {
        int nowPage = -1;
        //Performance
        if ((nowPageInt >= 1 && nowPageInt <= 6) || nowPageInt == 25) {
            nowPage = 1;
        }
        //DESIGN
        if (nowPageInt >= 7 && nowPageInt <= 10) {
            nowPage = 2;
        }
        //CAMERA
        if (nowPageInt >= 11 && nowPageInt <= 16) {
            nowPage = 3;
        }
        //Experience
        if (nowPageInt >= 17 && nowPageInt <= 20) {
            nowPage = 4;
        }
        //IOT
        if ((nowPageInt >= 21 && nowPageInt <= 24) || nowPageInt == 0) {
            nowPage = 5;
        }
        if (nowPage != -1){
            mViewPage.setCurrentItem(nowPage, false);
            VApplication.changeInt = VApplication.CHANGE_NULL;
        }
    }

    /**
     * 设置底部提示
     */
    private void setBottomTipMethods(int pos) {
        switch (pos) {
            case 1:
            case 6:
                tvTip.setText(R.string.main_design);
                break;
            case 2:
                tvTip.setText(R.string.main_video);
                break;
            case 3:
                tvTip.setText(R.string.main_experience);
                break;
            case 4:
                tvTip.setText(R.string.main_iot);
                break;
            case 5:
            case 0:
                tvTip.setText(R.string.main_performance);
                break;
            default:
        }
    }

    @Override
    public void onPageSelected(int position) {
        ivUpglide.setVisibility(ViewPager.GONE);
        ivUpglide2.setVisibility(ViewPager.GONE);
        tvTip.setVisibility(ViewPager.GONE);
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        delayedMethods(position);
        oldPosition = mCurrentPagePosition;
        mCurrentPagePosition = position;
        if (null != fragmentList && fragmentList.size() > 0) {
            //如果oldPosition == mCurrentPagePosition是首尾切换，设置为false不播放动画
            fragmentList.get(mCurrentPagePosition).startAnimList(oldPosition == mCurrentPagePosition ? false : true);
        }
    }

    /**
     * ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
     * 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
     *
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        if (ViewPager.SCROLL_STATE_IDLE == state) {
            if (mCurrentPagePosition > POINT_LENGTH) {
                // 末位之后，跳转到首位（1）
                mCurrentPagePosition = FIRST_ITEM_INDEX;
            } else if (mCurrentPagePosition < FIRST_ITEM_INDEX) {
                // 首位之前，跳转到末尾（N）
                mCurrentPagePosition = POINT_LENGTH;
            }
            //切换，不要动画效果
            if (mCurrentPagePosition == POINT_LENGTH || mCurrentPagePosition == FIRST_ITEM_INDEX) {
                mViewPage.setCurrentItem(mCurrentPagePosition, false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFirst = true;
        if (null != animatorGlide) {
            animatorGlide.cancel();
        }
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

}
