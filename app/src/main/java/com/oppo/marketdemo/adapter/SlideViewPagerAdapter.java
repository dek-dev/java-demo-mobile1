package com.oppo.marketdemo.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.fragments.lot.HeadsetFragment;
import com.oppo.marketdemo.fragments.lot.LotMainFragment;
import com.oppo.marketdemo.fragments.performance.PreventionFragment;
import com.oppo.marketdemo.fragments.lot.WatchFragment;
import com.oppo.marketdemo.fragments.lot.ParameterMainFragment;
import com.oppo.marketdemo.fragments.camera.AntiShakeFragment;
import com.oppo.marketdemo.fragments.camera.CameraMainFragment;
import com.oppo.marketdemo.fragments.camera.AIPortraitFragment;
import com.oppo.marketdemo.fragments.camera.AISlowMotionFragment;
import com.oppo.marketdemo.fragments.camera.MoreCameraFeaturesFragment;
import com.oppo.marketdemo.fragments.camera.NightFlarePortraitFragment;
import com.oppo.marketdemo.fragments.exterior.ExteriorMainFragment;
import com.oppo.marketdemo.fragments.exterior.PhoneBodyFragment;
import com.oppo.marketdemo.fragments.exterior.PhoneColorFragment;
import com.oppo.marketdemo.fragments.performance.BigMemoryFragment;
import com.oppo.marketdemo.fragments.performance.PerformanceMainFragment;
import com.oppo.marketdemo.fragments.performance.AnoFragment;
import com.oppo.marketdemo.fragments.exterior.FullScreenFragment;
import com.oppo.marketdemo.fragments.performance.SuperNetFragment;
import com.oppo.marketdemo.fragments.performance.SuperVoocFragment;
import com.oppo.marketdemo.fragments.system.FlashbackKeyFragment;
import com.oppo.marketdemo.fragments.system.PowerSavingFragment;
import com.oppo.marketdemo.fragments.system.SmallDecideFragment;
import com.oppo.marketdemo.fragments.system.SystemMainFragment;

import java.util.ArrayList;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2019/12/20
 * Description: 展示Fragment的ViewPager适配器
 */

public class SlideViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int FRAGMENT_NUM = 25;
    private ArrayList<BaseFragment> fragments;

    public SlideViewPagerAdapter(FragmentManager mManager) {
        super(mManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragments = new ArrayList<>();
        for (int i = 0; i < FRAGMENT_NUM; i++) {
            fragments.add(null);
        }
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();
        }
        return 0;
    }

    @Override
    @NonNull
    public BaseFragment getItem(int position) {
        if (fragments.get(position) == null) {
            switch (position) {
                case 0:
                case 23:
                    fragments.set(position, new ParameterMainFragment());//参数
                    break;
                /**
                 * 性能
                 */
                case 1:
                case 24:
                    fragments.set(position, new PerformanceMainFragment());//性能过渡页
                    break;
                case 2:
                    fragments.set(position, new PreventionFragment());//AON 防偷窥

                    break;
                case 3:
                    fragments.set(position, new AnoFragment());//AON 空气控制

                    break;
                case 4:
                    fragments.set(position, new SuperNetFragment());//5G芯片

                    break;
                case 5:
                    fragments.set(position, new SuperVoocFragment());//超级闪充
                    break;

                case 6:
                    fragments.set(position, new BigMemoryFragment());//8+128G
                    break;
                /**
                 * 外观
                 */
                case 7:
                    fragments.set(position, new ExteriorMainFragment());//外观过渡页
                    break;
                case 8:
                    fragments.set(position, new FullScreenFragment());//全面屏
                    break;
                case 9:
                    fragments.set(position, new PhoneBodyFragment());//轻薄机身
                    break;

                case 10:
                    fragments.set(position, new PhoneColorFragment());//潮流配色
                    break;
                /**
                 * 影像
                 */
                case 11:
                    fragments.set(position, new CameraMainFragment());//影像过渡页
                    break;
                case 12:
                    fragments.set(position, new AIPortraitFragment());//AI人像留色
                    break;
                case 13:
                    fragments.set(position, new AISlowMotionFragment());//智能高帧慢动作
                    break;
                case 14:
                    fragments.set(position, new AntiShakeFragment());//超级防抖
                    break;
                case 15:
                    fragments.set(position, new NightFlarePortraitFragment());//夜景霓虹人像
                    break;

                case 16:
                    fragments.set(position, new MoreCameraFeaturesFragment());//更多影响功能
                    break;
                /**
                 * 系统
                 */
                case 17:
                    fragments.set(position, new SystemMainFragment());//系统过渡页
                    break;
                case 18:
                    fragments.set(position, new PowerSavingFragment());//超级省电
                    break;
                case 19:
                    fragments.set(position, new FlashbackKeyFragment());//闪回
                    break;

                case 20:
                    fragments.set(position, new SmallDecideFragment());//小决定
                    break;
                /**
                 * IOT
                 */
                case 21:
                    fragments.set(position, new LotMainFragment());//LOT过渡页
                    break;
                case 22:
                    fragments.set(position, new WatchFragment());//手表

                    break;
//                case 23:
//                    fragments.set(position, new HeadsetFragment());//耳机
                default:
            }
        }
        return fragments.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        fragments.set(position, null);
        super.destroyItem(container, position, object);
    }
}