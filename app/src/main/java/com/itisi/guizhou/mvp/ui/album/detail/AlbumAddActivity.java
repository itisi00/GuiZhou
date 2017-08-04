package com.itisi.guizhou.mvp.ui.album.detail;

import android.os.Bundle;
import android.view.View;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

import java.util.List;

public class AlbumAddActivity extends RootActivity<AlbumDetailPresenter>
        implements AlbumDetailContract.View
        , View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_album_add;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onClick(View view) {

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
