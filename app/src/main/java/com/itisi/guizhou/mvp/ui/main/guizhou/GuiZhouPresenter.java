package com.itisi.guizhou.mvp.ui.main.guizhou;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.mvp.model.DataManager;

import javax.inject.Inject;

/**
 * author: itisi---
 * created by Administrator on 2017/3/27.
 * desc:
 */

public class GuiZhouPresenter extends RxPresenter<GuiZhouContract.View> implements GuiZhouContract.Presenter {

    private DataManager mDataManager;
    @Inject
    public GuiZhouPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadData() {

    }
}
