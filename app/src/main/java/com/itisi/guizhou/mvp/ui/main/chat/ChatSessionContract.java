package com.itisi.guizhou.mvp.ui.main.chat;

import com.itisi.guizhou.base.BasePresenter;
import com.itisi.guizhou.base.BaseView;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/7 9:38
 * 修改人:itisi
 * 修改时间: 2017/7/7 9:38
 * 修改内容:itisi
 * *********************
 */

public interface ChatSessionContract  {
    interface View extends BaseView {
        //定义自己特有的方法
        void showContent(String msg);

    }
    interface Presenter extends BasePresenter<View> {
        //定义自己特有的方法
        void loadData();
    }
}
