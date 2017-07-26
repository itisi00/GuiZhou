package com.itisi.guizhou.mvp.ui.rental.detail;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.ui.websit.detail.WebsitDetailContract;
import com.itisi.guizhou.mvp.ui.websit.detail.WebsitDetailPresenter;

import java.util.List;

public class RentalAddActivity extends RootActivity<WebsitDetailPresenter>
        implements WebsitDetailContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_rental_add;
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
