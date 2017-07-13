package com.itisi.guizhou.mvp.ui.blacknum;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.mvp.model.DataManager;

import javax.inject.Inject;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/12 15:57
 * 修改人:itisi
 * 修改时间: 2017/7/12 15:57
 * 修改内容:itisi
 * *********************
 */

public class BlackNumPresenter  extends RxPresenter<BlackNumContract.View> implements BlackNumContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public BlackNumPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(BlackNumContract.View view) {
        super.attachView(view);
        retisterEvent();
    }

    /**
     * 对retrofit 发情的请求 进行订阅?
     */
    private void retisterEvent() {
        // TODO: 2017/7/6  托管订阅???

    }

    @Override
    public void testShowPresenter(boolean isShow) {

    }
}
