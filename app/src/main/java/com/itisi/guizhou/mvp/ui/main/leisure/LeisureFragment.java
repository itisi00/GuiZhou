package com.itisi.guizhou.mvp.ui.main.leisure;


import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;

/**
 * 休闲 主页
 */
public class LeisureFragment extends RootFragment<LeisurePresenter> implements LeisureContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_leisure;
    }

    @Override
    protected void initEventAndData() {

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
