package com.itisi.guizhou.di.component;

import android.app.Activity;

import com.itisi.guizhou.di.module.FragmentModule;
import com.itisi.guizhou.di.scope.FragmentScope;
import com.itisi.guizhou.mvp.ui.main.guizhou.GuiZhouFragment;
import com.itisi.guizhou.mvp.ui.main.home.HomeFragment;
import com.itisi.guizhou.mvp.ui.main.chatsession.ChatSessionFragment;
import com.itisi.guizhou.mvp.ui.main.leisure.LeisureFragment;
import com.itisi.guizhou.mvp.ui.main.leisure.MeiZiFragment;
import com.itisi.guizhou.mvp.ui.main.leisure.VideoFragment;
import com.itisi.guizhou.mvp.ui.main.news.NewsFragment;

import dagger.Component;

/**
 * **********************
 * 功 能:为fragment注入依赖
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 17:43
 * 修改人:itisi
 * 修改时间: 2017/7/5 17:43
 * 修改内容:itisi
 * *********************
 */
@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();
    //主页
    void inject(HomeFragment fragment);
    void inject(ChatSessionFragment fragment);
    void inject(NewsFragment fragment);
    void inject(GuiZhouFragment fragment);
    void inject(LeisureFragment fragment);
    void inject(MeiZiFragment fragment);
    void inject(VideoFragment fragment);

    //
}
