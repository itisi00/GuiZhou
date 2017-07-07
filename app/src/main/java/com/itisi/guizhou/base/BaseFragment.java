package com.itisi.guizhou.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.itisi.guizhou.app.App;
import com.itisi.guizhou.di.component.DaggerFragmentComponent;
import com.itisi.guizhou.di.component.FragmentComponent;
import com.itisi.guizhou.di.module.FragmentModule;

import javax.inject.Inject;

/**
 * **********************
 * 功 能:待MVP的Fragment基类
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 16:40
 * 修改人:itisi
 * 修改时间: 2017/7/5 16:40
 * 修改内容:itisi
 * *********************
 */

public abstract class BaseFragment<T extends BasePresenter> extends NoMVPFragment implements BaseView  {

    @Inject
    protected T mPresenter;

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule(){
        return new FragmentModule(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(String msg) {
        Snackbar.make(((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0), msg, Snackbar.LENGTH_LONG).show();
        // TODO: 2017/7/5 显示错误信息 以后在封装
    }

    protected abstract void initInject();
}







