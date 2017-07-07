package com.itisi.guizhou.mvp.ui.main;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.mvp.model.DataManager;

import javax.inject.Inject;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/6 11:09
 * 修改人:itisi
 * 修改时间: 2017/7/6 11:09
 * 修改内容:itisi
 * *********************
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        retisterEvent();
    }

    private void retisterEvent() {
// TODO: 2017/7/6  托管订阅???

    }

    @Override
    public void testShowPresenter(boolean isShow) {
        mView.testShowView("itisi:"+isShow);
    }
}
