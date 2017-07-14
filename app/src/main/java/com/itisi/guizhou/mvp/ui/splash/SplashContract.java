package com.itisi.guizhou.mvp.ui.splash;

import com.itisi.guizhou.base.BasePresenter;
import com.itisi.guizhou.base.BaseView;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/14 16:17
 * 修改人:itisi
 * 修改时间: 2017/7/14 16:17
 * 修改内容:itisi
 * *********************
 */

public interface SplashContract {
    interface View extends BaseView {
        void testShowView(String smg);
    }

    interface Presenter extends BasePresenter<View> {
        void testShowPresenter(boolean isShow);
    }
}
