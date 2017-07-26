package com.itisi.guizhou.mvp.ui.scenic.footprint;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

@UseRxBus
public class ScenicFootprintAddActivity extends RootActivity<ScenicFootprintPresenter>
        implements ScenicFootprintContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_scenic_footprint_add;
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

    }


}
