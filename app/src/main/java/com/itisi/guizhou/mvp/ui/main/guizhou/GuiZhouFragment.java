package com.itisi.guizhou.mvp.ui.main.guizhou;


import android.view.View;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;

/**
 * 大贵州-主页
 */
public class GuiZhouFragment extends RootFragment<GuiZhouPresenter> implements GuiZhouContract.View,View.OnClickListener {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_gui_zhou;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showContent(String list) {

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
