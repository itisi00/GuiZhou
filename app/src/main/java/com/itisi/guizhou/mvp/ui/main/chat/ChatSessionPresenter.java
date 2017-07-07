package com.itisi.guizhou.mvp.ui.main.chat;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.mvp.model.DataManager;

import javax.inject.Inject;

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

public class ChatSessionPresenter  extends RxPresenter<ChatSessionContract.View>
        implements ChatSessionContract.Presenter{

    private DataManager mDataManager;
    @Inject
    public ChatSessionPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadData() {

    }
}
