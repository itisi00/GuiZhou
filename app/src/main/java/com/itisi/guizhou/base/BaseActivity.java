package com.itisi.guizhou.base;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDelegate;
import android.view.ViewGroup;

import com.itisi.guizhou.app.App;
import com.itisi.guizhou.di.component.ActivityComponent;
import com.itisi.guizhou.di.component.DaggerActivityComponent;
import com.itisi.guizhou.di.module.ActivityModule;
import com.itisi.guizhou.utils.rxbus.RxBus;

import javax.inject.Inject;

/**
 * **********************
 * 功 能:待MVP的Activity基类
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 16:40
 * 修改人:itisi
 * 修改时间: 2017/7/5 16:40
 * 修改内容:itisi
 * *********************
 */

public abstract class BaseActivity <T extends BasePresenter>extends NoMVPActivity implements BaseView{
    @Inject
    protected T mPresenter;


    /**
     * 注入依赖
     */
    protected abstract void initInject();

    protected ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        RxBus.getInstance().init(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        super.onDestroy();

    }

    /**
     * 主题切换
     * @param isNight
     */
    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    /**
     * 显示错误信息
     * @param msg
     */
    @Override
    public void showErrorMsg(String msg) {
//        SnackbarUtil.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), msg);
        Snackbar.make(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), msg, Snackbar.LENGTH_LONG).show();
        // TODO: 2017/7/5 显示错误信息 以后在封装
        stateError(); //重写这个方法 处理即可
    }

}
