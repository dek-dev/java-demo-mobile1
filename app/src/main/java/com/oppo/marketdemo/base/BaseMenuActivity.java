package com.oppo.marketdemo.base;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.adapter.BaseSideAdapter;
import com.oppo.marketdemo.custom.MenuView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.entity.BaseSideMultipleItem;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2018/2/26
 * Description: 有侧边栏的Activity基类
 */

public abstract class BaseMenuActivity extends BaseActivity implements DrawerLayout.DrawerListener {
    private FrameLayout flBaseContainer;
    public DrawerLayout drawerLayout;
    private List<BaseSideMultipleItem> sideList;
    private BaseSideAdapter sideAdapter;
    private RecyclerView rlvSide;
    private FrameLayout flDrawerBg;
    private View viewLine;
    private int position;
    private ImageView ivDrawerBg;
    private MenuView menuView;
    private FrameLayout flParameter,flList;
    private TypefaceTextView tvParameter;
    private ImageView ivParameterIcon;
    private ConstraintLayout llTip;
    /**
     * 打开动画
     */
    private AnimatorSet animatorOpenSet;
    /**
     * 关闭动画
     */
    private AnimatorSet animatorCloseSet;
    /**
     * 判断当前页面侧边是否为白色
     */
    private boolean isMenuViewWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basemenu);
        flBaseContainer = findViewById(R.id.fl_base_content_container);
        drawerLayout = findViewById(R.id.drawer_layout);
        ivDrawerBg = findViewById(R.id.iv_bg);
        rlvSide = findViewById(R.id.rlv_side);
        viewLine = findViewById(R.id.view_line);
        menuView = findViewById(R.id.menuView);
        llTip = findViewById(R.id.ll_tip);

        flParameter = findViewById(R.id.base_menu_parameter);
        flList = findViewById(R.id.fl_menu_list);
        tvParameter = findViewById(R.id.tv_parameter);
        ivParameterIcon = findViewById(R.id.iv_parameter_icon);

        flParameter.setOnClickListener(this);
        llTip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //加入布局
        View view = getLayoutInflater().inflate(getLayoutId(), flBaseContainer, false);
        flBaseContainer.addView(view);
        //设置侧边栏
        setSideDataMethods();
        //子类初始化
        init();
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    public void setMenuWrite(boolean isWrite) {
        isMenuViewWrite = isWrite;
    }

    /**
     * 设置侧边栏
     */
    public void setSideDataMethods() {
        //设置背景初始位置与及背景打开/关闭动画
        if (null != ivDrawerBg) {
            animatorOpenSet = AnimationOppoUtils.setDrawerBgOpenAnimator(ivDrawerBg, flList);
            animatorCloseSet = AnimationOppoUtils.setDrawerBgCloseAnimator(ivDrawerBg,flList);
            tvParameter.setTranslationY(200);
            ivParameterIcon.setTranslationY(200);
        }
        //禁止从侧边拉出
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(this);
        sideList = new ArrayList<>();
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_TITLE, R.string.main_selling_point1, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point1, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point2, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point3, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point4, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point5, false));


        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_TITLE, R.string.main_selling_point2, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point6, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point7, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point8, false));

        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_TITLE, R.string.main_selling_point3, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point9, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point10, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point11, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point12, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point13, false));

        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_TITLE, R.string.main_selling_point4, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point14, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point15, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point16, false));

        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_TITLE, R.string.main_selling_point5, false));
        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point17, false));
//        sideList.add(new BaseSideMultipleItem(BaseSideMultipleItem.TYPE_CONTENT, R.string.secondary_selling_point18, false));



        sideAdapter = new BaseSideAdapter(sideList);
        sideAdapter.setHasStableIds(true);
        rlvSide.setLayoutManager(new LinearLayoutManager(this));
        rlvSide.setAdapter(sideAdapter);
        rlvSide.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (null != sideAdapter) {
                    sideAdapter.setPlayAnimation(false, false);
                }
            }
        });
        //设置子类点击
        sideAdapter.addChildClickViewIds(R.id.base_menu_title, R.id.base_menu_text_title);
        sideAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int pos) {
                if (null != drawerLayout) {
                    position = pos + 1;
                    setMenuClick(true);
                }
            }
        });
    }

    /**
     * 关闭侧边栏
     * 点击关闭动画
     */
    public void setMenuClick(final boolean isGo) {
        if (null == sideAdapter || isGetOn) {
            return;
        }
        isGetOn = true;
        //列表收起动画
        if (null != sideAdapter) {
            sideAdapter.setPlayAnimation(true, false);
            sideAdapter.notifyDataSetChanged();
        }
        if (null != menuView) {
            menuView.startAnimatorMethods();
        }
        tvParameter.setVisibility(View.GONE);
        ivParameterIcon.setVisibility(View.GONE);
        tvParameter.setTranslationY(200);
        ivParameterIcon.setTranslationY(200);
        viewLine.setVisibility(View.GONE);
        //背景发起动画
        animatorCloseSet.removeAllListeners();
        animatorCloseSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (null != drawerLayout) {
                    flList.setVisibility(View.GONE);
                    flParameter.setVisibility(View.GONE);
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    if (null != menuView) {
                        menuView.setColorState(isMenuViewWrite);
                        menuView.invalidate();
                    }
                    if (isGo) {
                        onClickMenu(position);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorCloseSet.start();

    }

    /**
     * 打开侧边栏
     */
    public void clickOpenMenu(int curPosition) {
        if (null == sideAdapter || isGetOn) {
            return;
        }
        isGetOn = true;
        if (null != menuView) {
            menuView.startAnimatorMethods();
        }
        drawerLayout.openDrawer(Gravity.RIGHT);
        //初始化
        for (BaseSideMultipleItem side : sideAdapter.getData()) {
            side.setDot(false);
        }
        if (curPosition == -1) {
            tvParameter.setTextColor(ContextCompat.getColor(this, R.color.colorBlack6));
            tvParameter.setTypefaceStyle(0);
            return;
        }
        //设置选中项
        if (curPosition != 23) {
            curPosition = curPosition - 1;
            sideAdapter.getData().get(curPosition).setDot(true);
            tvParameter.setTextColor(ContextCompat.getColor(this, R.color.colorBlack6));
            tvParameter.setTypefaceStyle(0);
        } else {
            tvParameter.setTextColor(ContextCompat.getColor(this, R.color.colorBlackf75d40));
            tvParameter.setTypefaceStyle(2);
        }
    }

    /**
     * 抽屉点击跳转
     *
     * @param position
     */
    protected void onClickMenu(int position) {
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //打开参数页
            case R.id.base_menu_parameter:
                position = 23;
                setMenuClick(true);
                break;
            default:
        }
    }


    /**
     * 判断是否播放侧边下拉动画
     */
    private boolean isOpenDrawer;
    /**
     * 防止重复播放
     */
    private boolean isPlay;
    private boolean isGetOn;

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        if (null != animatorOpenSet && isOpenDrawer && !isPlay && slideOffset > 0.6) {
            flList.setVisibility(View.VISIBLE);
            flParameter.setVisibility(View.VISIBLE);
            //打开后按钮为黑色
            menuView.setColorState(false);
            menuView.invalidate();
            //列表打开动画
            sideAdapter.setPlayAnimation(true, true);
            sideAdapter.notifyDataSetChanged();
            isPlay = true;
            animatorOpenSet.removeAllListeners();
            animatorOpenSet.start();
            animatorOpenSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    viewLine.setVisibility(View.VISIBLE);
                    tvParameter.setVisibility(View.VISIBLE);
                    ivParameterIcon.setVisibility(View.VISIBLE);
                    AnimationOppoUtils.closeAnimation(tvParameter, true);
                    AnimationOppoUtils.closeAnimation(ivParameterIcon, true);
                    isGetOn = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        if (newState == DrawerLayout.STATE_SETTLING) {
            isOpenDrawer = !isOpenDrawer;
        }
        if (newState == DrawerLayout.STATE_IDLE) {
            if (!isPlay) {
                isGetOn = false;
            }
            isPlay = false;
        }
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        if (null != animatorOpenSet) {
            animatorOpenSet.cancel();
        }
        if (null != animatorCloseSet) {
            animatorCloseSet.cancel();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                    return true;
                }
                break;
            default:
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != animatorOpenSet) {
            animatorOpenSet.cancel();
        }
        if (null != animatorCloseSet) {
            animatorCloseSet.cancel();
        }
    }
}