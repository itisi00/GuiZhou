package com.itisi.guizhou.mvp.ui.blacknum;

import com.itisi.guizhou.base.BasePresenter;
import com.itisi.guizhou.base.BaseView;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/12 15:56
 * 修改人:itisi
 * 修改时间: 2017/7/12 15:56
 * 修改内容:itisi
 * *********************
 */

public interface BlackNumContract {
    interface View extends BaseView {
        void testShowView(String smg);
    }

    interface Presenter extends BasePresenter<View> {
        void testShowPresenter(boolean isShow);
    }
}
