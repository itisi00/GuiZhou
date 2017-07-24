package com.itisi.guizhou.mvp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * **********************
 * 功 能:休闲主页 适配器
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/12 16:43
 * 修改人:itisi
 * 修改时间: 2017/7/12 16:43
 * 修改内容:itisi
 * *********************
 */

public class LeisurePagerAdapter extends FragmentPagerAdapter { //FragmentStatePagerAdapter

    List<Fragment> mFragmentList;
    List<String>mTitleList;
    Context mContext;


    public LeisurePagerAdapter(FragmentManager fm) {
        this(fm,null,null,null);
    }

    public LeisurePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList, Context context) {
        super(fm);
        mFragmentList = fragmentList;
        mTitleList = titleList;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }


}
