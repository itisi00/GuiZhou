package com.itisi.guizhou.mvp.ui.shiwuzhaoling;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

@UseRxBus
public class ShiWuZhaoLingActivity extends RootActivity<ShiWuZhaoLingPresenter> implements ShiWuZhaoLingContract.View{


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shi_wu_zhao_ling;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateSuccess() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);

    }
}
