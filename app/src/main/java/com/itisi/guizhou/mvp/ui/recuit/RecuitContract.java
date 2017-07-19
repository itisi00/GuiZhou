package com.itisi.guizhou.mvp.ui.recuit;

import com.itisi.guizhou.base.BasePresenter;
import com.itisi.guizhou.base.BaseView;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

import java.util.List;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/17 16:45
 * 修改人:itisi
 * 修改时间: 2017/7/17 16:45
 * 修改内容:itisi
 * *********************
 */

public interface RecuitContract {
    interface View extends BaseView {
        void testShowView(String smg);
        void loadSuccess(List<MeiZiBean> beanList);
    }

    interface Presenter extends BasePresenter<View> {
        void testShowPresenter(boolean isShow);
        void loadData(int num,int page);
    }
}