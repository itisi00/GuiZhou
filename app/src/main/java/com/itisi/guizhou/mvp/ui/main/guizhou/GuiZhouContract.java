package com.itisi.guizhou.mvp.ui.main.guizhou;

import com.itisi.guizhou.base.BasePresenter;
import com.itisi.guizhou.base.BaseView;

/**
 * author: itisi---
 * created by Administrator on 2017/3/27.
 * desc:
 */

public interface GuiZhouContract {
    interface View extends BaseView {
        //定义自己特有的方法
        void showContent(String list);


    }
    interface Presenter extends BasePresenter<View> {
        //定义自己特有的方法
        void loadData();


    }
}
