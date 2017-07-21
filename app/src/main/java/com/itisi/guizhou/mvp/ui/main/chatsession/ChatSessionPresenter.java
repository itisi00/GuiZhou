package com.itisi.guizhou.mvp.ui.main.chatsession;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.common.CommonSubscriber;
import com.itisi.guizhou.common.RxUtil;
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