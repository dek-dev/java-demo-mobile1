package com.oppo.marketdemo.adapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.oppo.marketdemo.base.BaseFragment;

import java.util.ArrayList;


public class SlideTwoPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragments;

    public SlideTwoPagerAdapter(FragmentManager mManager, ArrayList<BaseFragment> fragments) {
        super(mManager);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();
        }
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}
