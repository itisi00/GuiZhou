package com.itisi.guizhou.di.component;

import android.app.Activity;

import com.itisi.guizhou.di.module.ActivityModule;
import com.itisi.guizhou.di.scope.ActivityScope;
import com.itisi.guizhou.mvp.ui.blacknum.BlackNumActivity;
import com.itisi.guizhou.mvp.ui.chat.ChatActivity;
import com.itisi.guizhou.mvp.ui.common.photoview.PhotoViewActivity;
import com.itisi.guizhou.mvp.ui.common.photoview.PhotoViewPagerActivity;
import com.itisi.guizhou.mvp.ui.main.MainActivity;
import com.itisi.guizhou.mvp.ui.recuit.RecuitActivity;
import com.itisi.guizhou.mvp.ui.scenic.ScenicActivity;
import com.itisi.guizhou.mvp.ui.scenic.detail.ScenicDetailActivity;
import com.itisi.guizhou.mvp.ui.scenic.onedetail.ScenicOneActivity;
import com.itisi.guizhou.mvp.ui.scenic.scenicinfo.ScenicInfoActivity;
import com.itisi.guizhou.mvp.ui.shiwuzhaoling.ShiWuZhaoLingActivity;
import com.itisi.guizhou.mvp.ui.splash.SplashActivity;
import com.itisi.guizhou.mvp.ui.techan.TeChanActivity;
import com.itisi.guizhou.mvp.ui.test.TestRxBusActivity;
import com.itisi.guizhou.mvp.ui.university.UniversityActivity;
import com.itisi.guizhou.mvp.ui.user.login.LoginActivity;
import com.itisi.guizhou.mvp.ui.user.register.RegistActivity;
import com.itisi.guizhou.mvp.ui.user.register.RegistConfirmActivity;

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
    void inject(LoginActivity activity );
    void inject(RegistActivity activity );
    void inject(RegistConfirmActivity activity );
    void inject(BlackNumActivity activity );
    void inject(SplashActivity activity );
    void inject(RecuitActivity activity );
    void inject(ChatActivity activity );
    void inject(UniversityActivity activity );
    void inject(ShiWuZhaoLingActivity activity );
    void inject(TeChanActivity activity );
    void inject(ScenicActivity activity );
    void inject(ScenicDetailActivity activity );
    void inject(ScenicOneActivity activity );
    void inject(ScenicInfoActivity activity );
    void inject(PhotoViewActivity activity );
    void inject(PhotoViewPagerActivity activity );
    //h后续创建的activity 接着往下写
}
