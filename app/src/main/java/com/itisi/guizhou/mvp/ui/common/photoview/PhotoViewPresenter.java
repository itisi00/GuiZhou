package com.itisi.guizhou.mvp.ui.common.photoview;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.mvp.model.DataManager;

import javax.inject.Inject;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/25 10:47
 * 修改人:itisi
 * 修改时间: 2017/7/25 10:47
 * 修改内容:itisi
 * *********************
 */

public class PhotoViewPresenter extends RxPresenter<PhotoViewContract.View> implements PhotoViewContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public PhotoViewPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PhotoViewContract.View view) {
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
        mView.testShowView("itisi:" + isShow);
    }

}
