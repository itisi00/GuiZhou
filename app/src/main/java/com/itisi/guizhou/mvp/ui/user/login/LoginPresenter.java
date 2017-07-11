package com.itisi.guizhou.mvp.ui.user.login;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.mvp.model.DataManager;

import javax.inject.Inject;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/11 16:07
 * 修改人:itisi
 * 修改时间: 2017/7/11 16:07
 * 修改内容:itisi
 * *********************
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginContract.View view) {
        super.attachView(view);
        retisterEvent();
    }
    /**
     * 对retrofit 发情的请求 进行订阅?
     */
    private void retisterEvent() {

    }

    @Override
    public void testShowPresenter(boolean isShow) {

    }
}
