package com.itisi.guizhou.mvp.ui.main.leisure;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;
import com.itisi.guizhou.adapter.LeisurePagerAdapter;
import com.itisi.guizhou.mvp.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 休闲 主页
 */
public class LeisureFragment extends RootFragment<LeisurePresenter>
        implements LeisureContract.View {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    List<String> mTitleList;
    List<Fragment> mFragmentList;
    LeisurePagerAdapter mPagerAdapter;
    private MainActivity mMainActivity;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_leisure;
    }

    @Override
    protected void initEventAndData() {
        mMainActivity = (MainActivity) getActivity();
        intTabLayout();
        initViewPager();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainActivity.setDrawerMenuLeftSlide();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMainActivity.setDrawerMenuFullscreen();
    }

    private void intTabLayout() {
        mTitleList = new ArrayList<>();
        mTitleList.add("妹纸");
        mTitleList.add("视频");
        mTitleList.add("音乐");


        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

    }

    private void initViewPager() {
        MeiZiFragment mMeiZiFragment = new MeiZiFragment();
        VideoFragment mVideoFragment = new VideoFragment();
        VideoFragment mVideoFragment2 = new VideoFragment();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mMeiZiFragment);
        mFragmentList.add(mVideoFragment);
        mFragmentList.add(mVideoFragment2);
//        getChildFragmentManager()
        mPagerAdapter = new LeisurePagerAdapter(getChildFragmentManager(), mFragmentList, mTitleList, getActivity());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }


    @Override
    public void showContent(String msg) {

    }

    @Override
    public void useNightMode(boolean isNight) {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateSuccess() {

    }
}
