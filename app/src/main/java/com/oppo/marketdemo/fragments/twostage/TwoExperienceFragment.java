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
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Neo
 */
public class TwoExperienceFragment extends BaseFragment {
    ImageView ivIcon;
    TypefaceTextView tvTitle;
    TypefaceTextView tvContent;
    View viewLine;
    RecyclerView rlvAppearance;
    private TwoExperienceAdapter appearanceAdapter;
    List<Integer> integerList;

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
        return R.layout.two_appearance_fragment;
    }

    @Override
    protected void init() {
        integerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            integerList.add(i);
        }
        appearanceAdapter = new TwoExperienceAdapter();
        rlvAppearance.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlvAppearance.setAdapter(appearanceAdapter);
        appearanceAdapter.addData(integerList);
        appearanceAdapter.addChildClickViewIds(R.id.ll_item_bg);
        appearanceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AnimationOppoUtils.setRlvAnimationMethods(getActivity(), view, position + 18);
            }
        });
        if (TwoMainStageActivity.isFirst) {
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
        if (null != rlvAppearance && null != appearanceAdapter) {
                rlvAppearance.setAlpha(1);
                tvTitle.setAlpha(1);
                tvContent.setAlpha(1);
                viewLine.setAlpha(1);
                appearanceAdapter.notifyDataSetChanged();
                AnimationOppoUtils.setAnimationMethods(rlvAppearance, getActivity());
                AnimationOppoUtils.setAnimationXMethods(tvTitle, tvContent, viewLine);
                AnimationOppoUtils.setAnimationPartIcon(ivIcon, AnimationOppoUtils.TYPE_APPEARANCE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (null != rlvAppearance && !isVisibleToUser) {
            hideViews(tvTitle, tvContent, viewLine, rlvAppearance, ivIcon);
        }
    }

    @Override
    public void startAnimList(boolean isAnimList) {
        super.startAnimList(isAnimList);
        if (null != tvTitle) {
            showViews(tvTitle, tvContent, viewLine, rlvAppearance, ivIcon);
            notifyAnimationMetods();
        }
    }
}
