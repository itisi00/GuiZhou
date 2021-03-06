package com.itisi.guizhou.mvp.ui.chat;

import com.itisi.guizhou.base.RxPresenter;
import com.itisi.guizhou.mvp.model.CommonSubscriber;
import com.itisi.guizhou.common.RxUtil;
import com.itisi.guizhou.mvp.model.DataManager;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.model.http.response.GankResponse;
import com.itisi.guizhou.utils.ToastUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/19 17:46
 * 修改人:itisi
 * 修改时间: 2017/7/19 17:46
 * 修改内容:itisi
 * *********************
 */

public class ChatPresenter  extends RxPresenter<ChatContract.View>
        implements ChatContract.Presenter{

    private DataManager mDataManager;
    @Inject
    public ChatPresenter(DataManager dataManager) {
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
                                if (meiZiBeen==null){
                                    ToastUtil.Info("数据是空的");
                                }else{
                                    mView.loadSuccess(meiZiBeen);

                                }
                            }
                        })
        );
    }
}
