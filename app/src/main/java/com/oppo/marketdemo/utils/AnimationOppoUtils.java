package com.oppo.marketdemo.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.oppo.marketdemo.MainActivity;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.SubPageActivity;
import com.oppo.marketdemo.TwoMainStageActivity;
import com.oppo.marketdemo.globle.VApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neo
 * 动画工具类
 */
public class AnimationOppoUtils {
    /**
     * 设置每个二级页面Fragment中Icon动画
     */
    public final static int TYPE_PHOTO = 1;
    public final static int TYPE_VIDEO = 2;
    public final static int TYPE_EXP = 3;
    public final static int TYPE_APPEARANCE = 4;

    public static void setAnimationIcon(View view, int type) {
        if (null == view) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", type == TYPE_PHOTO ? 0.3f : 0.5f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", type == TYPE_PHOTO ? 0.3f : 0.5f, 1f);
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", type == TYPE_PHOTO ? 300 : 400, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY, translationY);
        objectAnimator.setDuration(400);
        objectAnimator.start();
    }

    /**
     * 列表动画
     */
    public static void setAnimationMethods(RecyclerView rlv, Context context) {
        if (null == rlv) {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        controller.setDelay(0.3f);
        rlv.setLayoutAnimation(controller);
        rlv.startLayoutAnimation();
    }

    /**
     * 上滑提示动画
     *
     * @param ivUpglide
     * @param ivUpglide2
     */
    public static AnimatorSet setUpDownAnimationMethods(View ivUpglide, View ivUpglide2) {
        AnimatorSet animatorSet = new AnimatorSet();
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 1f, -40);

        ObjectAnimator animatorLideOne = ObjectAnimator.ofPropertyValuesHolder(ivUpglide, alpha, translationY);
        ObjectAnimator animatorLideTwo = ObjectAnimator.ofPropertyValuesHolder(ivUpglide2, alpha, translationY);

        animatorLideOne.setRepeatCount(ValueAnimator.INFINITE);
        animatorLideOne.setRepeatMode(ValueAnimator.RESTART);
        animatorLideTwo.setRepeatCount(ValueAnimator.INFINITE);
        animatorLideTwo.setRepeatMode(ValueAnimator.RESTART);

        animatorLideTwo.setStartDelay(800);

        animatorSet.playTogether(animatorLideOne, animatorLideTwo);
        animatorSet.setDuration(1500);
        animatorSet.start();
        return animatorSet;
    }

    public static void setAnimationPartIcon(View view, int type) {
        if (null == view) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f);
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", type == TYPE_EXP ? -280 : 200, 1);
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", type == TYPE_EXP ? 1500 : 1300, 1);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY, translationX, translationY);
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }

    /**
     * 设置从屏外右向左动画
     */
    public static void setAnimationXMethods(View viewTitle, View viewContent, View viewLine) {
        if (null == viewTitle) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTitle = ObjectAnimator.ofFloat(viewTitle, "translationX", 1000, 0);
        ObjectAnimator animatorCotent = ObjectAnimator.ofFloat(viewContent, "translationX", 1000, 0);
        ObjectAnimator animatorLine = ObjectAnimator.ofFloat(viewLine, "translationX", 1000, 0);
        animatorSet.playTogether(animatorTitle, animatorCotent, animatorLine);
        animatorSet.setDuration(600);
        animatorSet.start();
    }

    /**
     * 设置列表item点击动画
     *
     * @param view
     */
    public static void setRlvAnimationMethods(final Context context, View view, final int position) {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.8f, 1);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.8f, 1);
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 0, 100, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY, translationX);
        objectAnimator.setDuration(80);
//        objectAnimator.start();
        context.startActivity(new Intent(context, SubPageActivity.class)
                .putExtra(SubPageActivity.CUR_POSITION, position));
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                context.startActivity(new Intent(context, SubPageActivity.class)
                        .putExtra(SubPageActivity.CUR_POSITION, position));
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }


    /**
     * 设置按钮点击缩放
     *
     * @param view
     */
    public static void setScaleMethods(final Context context, final View view, final int pagerNum) {
        int[] viewCenter = ViewCenterUtils.getViewCenter(view);
        Intent intent = new Intent(context, TwoMainStageActivity.class);
        intent.putExtra(MainActivity.PAGER_NUM, pagerNum);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        context.startActivity(intent);
    }

    /**
     * 上滑提示动画
     *
     * @param ivTip
     */
    public static ObjectAnimator setTraintAnimationMethods(View ivTip) {
        if (null != ivTip) {
            ivTip.setVisibility(View.VISIBLE);
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivTip, "translationY", 0, 60, -60, 0);
        objectAnimator.setDuration(1500);
        return objectAnimator;
    }

    /**
     * 设置从屏外左向右动画
     */
    public static List<ObjectAnimator> setAnimationYMethods(View... view) {
        List<ObjectAnimator> animatorList = new ArrayList<>();
        for (View v : view) {
            ObjectAnimator animatorTitle = ObjectAnimator.ofFloat(v, "translationX", 500, 0);
            animatorTitle.setDuration(500);
            animatorTitle.setInterpolator(new DecelerateInterpolator());
            animatorTitle.start();
            animatorList.add(animatorTitle);
        }
        return animatorList;
    }

    /**
     * 水波纹
     *
     * @param view
     * @return
     */
    public static AnimatorSet setWaveAnimator(View view, View viewTwo) {
        AnimatorSet animatorSet = new AnimatorSet();

        PropertyValuesHolder alphaP = PropertyValuesHolder.ofFloat("alpha", 1f, 0);
        PropertyValuesHolder scaleXP = PropertyValuesHolder.ofFloat("scaleX", 1, 1.5f);
        PropertyValuesHolder scaleYP = PropertyValuesHolder.ofFloat("scaleY", 1, 1.5f);

        ObjectAnimator objectOne = ObjectAnimator.ofPropertyValuesHolder(view, alphaP, scaleXP, scaleYP);
        ObjectAnimator objectTwo = ObjectAnimator.ofPropertyValuesHolder(viewTwo, alphaP, scaleXP, scaleYP);

        objectOne.setRepeatCount(ValueAnimator.INFINITE);
        objectTwo.setRepeatCount(ValueAnimator.INFINITE);

        objectTwo.setStartDelay(500);

        animatorSet.playTogether(objectOne, objectOne, objectTwo);
        animatorSet.setDuration(2000);
        return animatorSet;
    }


    /**
     * 侧边栏打开动画
     *
     * @param view
     * @return
     */
    public static AnimatorSet setDrawerBgOpenAnimator(final ImageView view, View timeView) {
        if (null == view) {
            return null;
        }
        view.setPivotX(80);
        view.setPivotY(15);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 8.8f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 40);

        //设置图片用
        ObjectAnimator alphaTime = ObjectAnimator.ofFloat(timeView, "alpha", 1, 1);
        alphaTime.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setImageResource(R.drawable.base_menu_bg);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        alphaTime.setDuration(100);

        scaleX.setDuration(280);
        scaleY.setDuration(410);
        animatorSet.playTogether(scaleX, scaleY, alphaTime);
        return animatorSet;
    }

    /**
     * 侧边栏关闭动画
     *
     * @param view
     * @return
     */
    public static AnimatorSet setDrawerBgCloseAnimator(final ImageView view, View viewTime) {
        if (null == view) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 8.8f, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 40, 1);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.2f);

        //专门用来设置圆角图片
        ObjectAnimator objectOther = ObjectAnimator.ofFloat(viewTime, "alpha", 1, 1);
        objectOther.setDuration(350);
        objectOther.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setImageResource(VApplication.sideIsWhite ? R.drawable.side_open : R.drawable.side_open_black);
                view.setAlpha(1f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        scaleX.setDuration(480);
        scaleY.setDuration(350);

        alpha.setDuration(150);
        alpha.setStartDelay(200);

        animatorSet.playTogether(scaleX, scaleY, alpha, objectOther);
        return animatorSet;
    }


    /**
     * 侧边栏浮现动画
     *
     * @param view
     */
    public static void orderInAnimation(final View view, int position, boolean isOpen) {
        int posOne = isOpen ? 150 : 0;
        int posTwo = isOpen ? 0 : 150;
        ObjectAnimator animatorTitle = ObjectAnimator.ofFloat(view, "translationY", posOne, posTwo);
        animatorTitle.setDuration(isOpen ? (600 + (position * 15)) : (position * 20));
        animatorTitle.setInterpolator(new DecelerateInterpolator());
        animatorTitle.start();
    }

    public static void ref(final View view) {
        view.setTranslationY(0);
    }

    /**
     * 参数
     *
     * @param view
     */
    public static void closeAnimation(final View view, boolean isOpen) {
        int posOne = isOpen ? 200 : 0;
        int posTwo = isOpen ? 0 : 200;
        ObjectAnimator animatorTitle = ObjectAnimator.ofFloat(view, "translationY", posOne, posTwo);
        animatorTitle.setDuration(isOpen ? 300 : 100);
        animatorTitle.setInterpolator(new DecelerateInterpolator());
        animatorTitle.start();
    }

    /**
     * 侧边栏动画列表动画
     */
    public static void setDrawerListOpenAnimator(RecyclerView rlv, Context context) {
        if (null == rlv) {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_open);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        controller.setDelay(0.3f);
        rlv.setLayoutAnimation(controller);
        rlv.startLayoutAnimation();
    }

    /**
     * 配件呼吸效果
     *
     * @return
     */
    public static final int INTELLIGENT_PAY = 1;
    public static final int INTELLIGENT_IFLY = 2;
    public static final int INTELLIGENT_FRUIT = 3;
    public static final int INTELLIGENT_COOL = 4;
    public static final int INTELLIGENT_QQ = 5;

    public static ObjectAnimator setWatchMethods(View view, int typeInt) {
        float valueFirst = 0;
        float valueSecond = 0;
        switch (typeInt) {
            case INTELLIGENT_PAY:
                valueFirst = 1.22f;
                valueSecond = 1.27f;
                break;
            case INTELLIGENT_IFLY:
                valueFirst = 1.00f;
                valueSecond = 1.05f;
                break;
            case INTELLIGENT_FRUIT:
                valueFirst = 0.95f;
                valueSecond = 1.00f;
                break;
            case INTELLIGENT_COOL:
                valueFirst = 1.18f;
                valueSecond = 1.23f;
                break;
            case INTELLIGENT_QQ:
                valueFirst = 0.97f;
                valueSecond = 1.02f;
                break;
            default:
        }
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", valueFirst, valueSecond, valueFirst, valueSecond, valueFirst, valueSecond);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", valueFirst, valueSecond, valueFirst, valueSecond, valueFirst, valueSecond);
        ObjectAnimator animatorSet = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        animatorSet.setDuration(3800);
        return animatorSet;
    }

    /**
     * 手表提示动画
     *
     * @param view
     * @return
     */
    public static ObjectAnimator setWatchAiAnimation(View view, boolean isAi) {
        ObjectAnimator objectAnimator;
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", isAi ? 0.8f : 0.5f, isAi ? 1f : 0.7f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", isAi ? 0.8f : 0.5f, isAi ? 1f : 0.7f);
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setDuration(500);
        return objectAnimator;
    }


    //呼吸老照片
    public static ObjectAnimator setBreatheMethods(View... views) {
        ObjectAnimator animatorSet = new ObjectAnimator();
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1, 1.1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1, 1.1f);
        animatorSet = ObjectAnimator.ofPropertyValuesHolder(views[0], scaleX, scaleY);
        animatorSet.setRepeatCount(ValueAnimator.INFINITE);
        animatorSet.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet.setDuration(550);
        return animatorSet;
    }


    public static ObjectAnimator setIn(View view) {
        ObjectAnimator animatorSet;
        animatorSet = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animatorSet.setDuration(1200);
        return animatorSet;
    }

    /**
     * 闪充
     *
     * @param view
     * @return
     */
    public static ObjectAnimator setVoocMethods(View view) {
        if (null == view) {
            return null;
        }
        view.setVisibility(View.VISIBLE);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animatorSet = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(300);
        return animatorSet;
    }

    /**
     * 左右提示动画
     *
     * @param ivTip
     */
    public static ObjectAnimator setColorTipAnimationMethods(View ivTip, final View flView) {
        if (null == ivTip) {
            return null;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivTip, "translationX", 0, 95, -95, 0);
        objectAnimator.setDuration(1800);
        objectAnimator.removeAllListeners();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (null != flView) {
                    flView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        return objectAnimator;
    }

    public static ObjectAnimator setTextInAnimation(View view) {
        ObjectAnimator objectAnimator;
        objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        objectAnimator.setDuration(200);
        return objectAnimator;
    }

    public static ObjectAnimator setEnduranceAnimation(View view) {
        ObjectAnimator objectAnimator;
//        objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.5f,1f,0.5f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1f);
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setDuration(500);
        return objectAnimator;
    }

    /**
     * watch手的动画
     */
    public static ObjectAnimator setHandAnimationMethods(View ivTip) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivTip, "translationY", 0, -140);
        objectAnimator.setDuration(800);
        objectAnimator.setRepeatCount(2);
        return objectAnimator;
    }
}
