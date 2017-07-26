package com.itisi.guizhou.mvp.ui.websit.detail;

import com.itisi.guizhou.base.BasePresenter;
import com.itisi.guizhou.base.BaseView;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

import java.util.List;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/26 11:27
 * 修改人:itisi
 * 修改时间: 2017/7/26 11:27
 * 修改内容:itisi
 * *********************
 */

public interface WebsitDetailContract {
    interface View extends BaseView {
        //定义自己特有的方法
        void showContent(String msg);

        void loadSuccess(List<MeiZiBean> beanList);

    }

    interface Presenter extends BasePresenter<View> {
        //定义自己特有的方法

        /**
         * 从服务器加载数据
         *
         * @param num  每页的数据量 10条
         * @param page 第几页
         */
        void loadData(int num, int page);
    }
}
