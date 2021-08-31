package com.oppo.marketdemo.fragments.twostage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.TwoMainStageActivity;
import com.oppo.marketdemo.adapter.TwoExperienceAdapter;
import com.oppo.marketdemo.adapter.TwoIotAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Neo
 * 配件
 */
public class TwoIoTFragment extends BaseFragment {
    public static final String IS_ANIMATION = "is_Animation";
    /**
     * 第0个为false不播放动画
     * 第4个为true播放动画
     */
    private boolean isPlay;
    /**
     * 第一个加载是外观页面时，要播放动画
     */
    private boolean isThisFirst;
    ImageView ivIcon;
    TypefaceTextView tvTitle;
    TypefaceTextView tvContent;
    View viewLine;
    RecyclerView rlvAppearance;
    private TwoIotAdapter iotAdapter;
    List<Integer> integerList;

    public static TwoIoTFragment getInstance(boolean isAnimation) {
        TwoIoTFragment fragment = new TwoIoTFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_ANIMATION, isAnimation);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View mRootView) {
        ivIcon = mRootView.findViewById(R.id.iv_icon);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        tvContent = mRootView.findViewById(R.id.tv_content);
        viewLine = mRootView.findViewById(R.id.view);
        rlvAppearance = mRootView.findViewById(R.id.rlv_appearance);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.two_iot_fragment;
    }

    @Override
    protected void init() {
        isPlay = getArguments().getBoolean(IS_ANIMATION);
        integerList = new ArrayList<>();

//        for (int i = 0; i < 2; i++) {
//            integerList.add(i);
//        }

//disible iot oppo enco
        for (int i = 0; i < 1; i++) {
            integerList.add(i);
        }

        iotAdapter = new TwoIotAdapter();
        rlvAppearance.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlvAppearance.setAdapter(iotAdapter);
        iotAdapter.addData(integerList);
        iotAdapter.addChildClickViewIds(R.id.ll_item_bg);
        iotAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AnimationOppoUtils.setRlvAnimationMethods(getActivity(), view, position + 22);
            }
        });
        if (TwoMainStageActivity.isFirst) {
            isThisFirst = true;
            TwoMainStageActivity.isFirst = false;
            rlvAppearance.setAlpha(0);
            tvTitle.setAlpha(0);
            tvContent.setAlpha(0);
            viewLine.setAlpha(0);
            tvTitle.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            rlvAppearance.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyAnimationMetods();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 列表动画
     */
    private void notifyAnimationMetods() {
        if (null != rlvAppearance) {
            if (isPlay || isThisFirst || isAnim){
                isThisFirst = false;
                rlvAppearance.setAlpha(1);
                tvTitle.setAlpha(1);
                tvContent.setAlpha(1);
                viewLine.setAlpha(1);
                iotAdapter.notifyDataSetChanged();
                AnimationOppoUtils.setAnimationMethods(rlvAppearance, getActivity());
                AnimationOppoUtils.setAnimationXMethods(tvTitle, tvContent, viewLine);
                AnimationOppoUtils.setAnimationPartIcon(ivIcon, AnimationOppoUtils.TYPE_APPEARANCE);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (null != rlvAppearance && !isVisibleToUser && isPlay) {
            hideViews(tvTitle, tvContent, viewLine, rlvAppearance, ivIcon);
        }
    }

    private boolean isAnim;
    @Override
    public void startAnimList(boolean isAnimList) {
        super.startAnimList(isAnimList);
        isAnim = isAnimList;
        if (null != tvTitle) {
            showViews(tvTitle, tvContent,viewLine, rlvAppearance, ivIcon);
            if (isAnim){
                notifyAnimationMetods();
            }
        }
    }
}
