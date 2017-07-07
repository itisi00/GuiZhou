package com.itisi.guizhou.di.component;

import android.app.Activity;

import com.itisi.guizhou.mvp.ui.main.MainActivity;
import com.itisi.guizhou.di.module.ActivityModule;
import com.itisi.guizhou.di.scope.ActivityScope;
import com.itisi.guizhou.mvp.ui.main.temp.TestRxBusActivity;

import dagger.Component;

/**
 * **********************
 * 功 能:为activity 注入依赖
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 17:43
 * 修改人:itisi
 * 修改时间: 2017/7/5 17:43
 * 修改内容:itisi
 * *********************
 */
@ActivityScope
@Component(dependencies = {AppComponent.class},modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
    void inject(MainActivity activity);
    void inject(TestRxBusActivity activity );
    //h后续创建的activity 接着往下写
}
