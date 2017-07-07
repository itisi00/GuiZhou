package com.itisi.guizhou.mvp.ui.main.leisure;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.mvp.model.DataManager;
import com.itisi.guizhou.mvp.model.http.RetrofitHelper;

import javax.inject.Inject;

/**
 * author: itisi---
 * created by Administrator on 2017/3/27.
 * desc:
 */

public class LeisurePresenter extends RxPresenter<LeisureContract.View> implements LeisureContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public LeisurePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadData() {

    }
}
