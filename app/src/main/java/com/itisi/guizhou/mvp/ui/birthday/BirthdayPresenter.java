package com.itisi.guizhou.mvp.ui.birthday;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.common.RxUtil;
import com.itisi.guizhou.mvp.model.CommonSubscriber;
import com.itisi.guizhou.mvp.model.DataManager;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.model.http.response.GankResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/1 14:27
 * 修改人:itisi
 * 修改时间: 2017/8/1 14:27
 * 修改内容:itisi
 * *********************
 */

public class BirthdayPresenter extends RxPresenter<BirthdayContract.View> implements BirthdayContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public BirthdayPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadData(int num, int page) {
        addSubscribe(
                mDataManager.getMeiZiList(num, page)
                        .compose(RxUtil.<GankResponse<List<MeiZiBean>>>rxSchedulerObservableHelper())
                        .compose(RxUtil.<List<MeiZiBean>>handlerGankResult())
                        .subscribeWith(new CommonSubscriber<List<MeiZiBean>>(mView, false) {
                            @Override
                            public void onNext(List<MeiZiBean> meiZiBeen) {
                                mView.loadSuccess(meiZiBeen);
                            }
                        })
        );
    }
}
