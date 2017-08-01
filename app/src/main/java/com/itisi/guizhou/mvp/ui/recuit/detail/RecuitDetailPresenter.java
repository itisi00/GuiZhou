package com.itisi.guizhou.mvp.ui.recuit.detail;

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
 * 功 能:招聘详情
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/1 9:07
 * 修改人:itisi
 * 修改时间: 2017/8/1 9:07
 * 修改内容:itisi
 * *********************
 */

public class RecuitDetailPresenter extends RxPresenter<RecuitDetailContract.View> implements RecuitDetailContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public RecuitDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(RecuitDetailContract.View view) {
        super.attachView(view);
        retisterEvent();
    }

    /**
     * 对retrofit 发情的请求 进行订阅?
     */
    private void retisterEvent() {
        // TODO: 2017/7/6  托管订阅???

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
