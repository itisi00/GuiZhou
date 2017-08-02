package com.itisi.guizhou.di.component;

import android.app.Activity;

import com.itisi.guizhou.di.module.ActivityModule;
import com.itisi.guizhou.di.scope.ActivityScope;
import com.itisi.guizhou.mvp.ui.about.AboutActivity;
import com.itisi.guizhou.mvp.ui.about.AboutUsActivity;
import com.itisi.guizhou.mvp.ui.about.AgreementActivity;
import com.itisi.guizhou.mvp.ui.account.AccountActivity;
import com.itisi.guizhou.mvp.ui.album.AlbumActivity;
import com.itisi.guizhou.mvp.ui.birthday.BirthdayActivity;
import com.itisi.guizhou.mvp.ui.blacknum.BlackNumActivity;
import com.itisi.guizhou.mvp.ui.blacknum.detail.BlackAddActivity;
import com.itisi.guizhou.mvp.ui.blacknum.detail.BlackDetailActivity;
import com.itisi.guizhou.mvp.ui.agenda.AgendaActivity;
import com.itisi.guizhou.mvp.ui.chat.ChatActivity;
import com.itisi.guizhou.mvp.ui.collection.CollectionActivity;
import com.itisi.guizhou.mvp.ui.common.photoview.PhotoViewActivity;
import com.itisi.guizhou.mvp.ui.common.photoview.PhotoViewPagerActivity;
import com.itisi.guizhou.mvp.ui.fadeback.FadebackActivity;
import com.itisi.guizhou.mvp.ui.footprint.FootprintActivity;
import com.itisi.guizhou.mvp.ui.ittool.ItToolActivity;
import com.itisi.guizhou.mvp.ui.ittool.detail.ItToolAddActivity;
import com.itisi.guizhou.mvp.ui.ittool.detail.ItToolDetailActivity;
import com.itisi.guizhou.mvp.ui.jingxuan.JingXuanActivity;
import com.itisi.guizhou.mvp.ui.jingxuan.JingXuanDetailActivity;
import com.itisi.guizhou.mvp.ui.main.MainActivity;
import com.itisi.guizhou.mvp.ui.mingyan.MingYanActivity;
import com.itisi.guizhou.mvp.ui.read.ReadActivity;
import com.itisi.guizhou.mvp.ui.recuit.RecuitActivity;
import com.itisi.guizhou.mvp.ui.recuit.detail.RecuitAddActivity;
import com.itisi.guizhou.mvp.ui.recuit.detail.RecuitDetailActivity;
import com.itisi.guizhou.mvp.ui.rental.RentalActivity;
import com.itisi.guizhou.mvp.ui.rental.detail.RentalAddActivity;
import com.itisi.guizhou.mvp.ui.rental.detail.RentalDetailActivity;
import com.itisi.guizhou.mvp.ui.scenic.ScenicActivity;
import com.itisi.guizhou.mvp.ui.scenic.footprint.ScenicFootprintActivity;
import com.itisi.guizhou.mvp.ui.scenic.footprint.ScenicFootprintAddActivity;
import com.itisi.guizhou.mvp.ui.scenic.onedetail.ScenicOneActivity;
import com.itisi.guizhou.mvp.ui.scenic.scenicinfo.ScenicInfoActivity;
import com.itisi.guizhou.mvp.ui.setting.SettingActivity;
import com.itisi.guizhou.mvp.ui.shiwuzhaoling.ShiWuZhaoLingActivity;
import com.itisi.guizhou.mvp.ui.splash.SplashActivity;
import com.itisi.guizhou.mvp.ui.techan.TeChanActivity;
import com.itisi.guizhou.mvp.ui.test.TestRxBusActivity;
import com.itisi.guizhou.mvp.ui.university.UniversityActivity;
import com.itisi.guizhou.mvp.ui.university.style.UniversityStyleActivity;
import com.itisi.guizhou.mvp.ui.university.style.UniversityStyleAddActivity;
import com.itisi.guizhou.mvp.ui.user.login.LoginActivity;
import com.itisi.guizhou.mvp.ui.user.register.RegistActivity;
import com.itisi.guizhou.mvp.ui.user.register.RegistConfirmActivity;
import com.itisi.guizhou.mvp.ui.websit.WebsitActivity;
import com.itisi.guizhou.mvp.ui.websit.detail.WebsitAddActivity;
import com.itisi.guizhou.mvp.ui.websit.detail.WebsitDetailActivity;

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
    void inject(ScenicFootprintActivity activity );
    void inject(ScenicFootprintAddActivity activity );
    void inject(ScenicOneActivity activity );
    void inject(ScenicInfoActivity activity );

    void inject(PhotoViewActivity activity );
    void inject(PhotoViewPagerActivity activity );

    void inject(UniversityStyleActivity activity );
    void inject(UniversityStyleAddActivity activity );

    void inject(ItToolActivity activity );
    void inject(JingXuanActivity activity );
    void inject(ReadActivity activity );
    void inject(RentalActivity activity );
    void inject(WebsitActivity activity );

    void inject(WebsitDetailActivity activity );
    void inject(WebsitAddActivity activity );

    void inject(ItToolDetailActivity activity );
    void inject(ItToolAddActivity activity );

    void inject(RentalDetailActivity activity );
    void inject(RentalAddActivity activity );

    void inject(BlackDetailActivity activity );
    void inject(BlackAddActivity activity );

    void inject(JingXuanDetailActivity activity );

    void inject(RecuitDetailActivity activity );
    void inject(RecuitAddActivity activity );

    void inject(MingYanActivity activity );


    void inject(AccountActivity activity );
    void inject(AlbumActivity activity );
    void inject(BirthdayActivity activity );
    void inject(AgendaActivity activity );
    void inject(CollectionActivity activity );
    void inject(FadebackActivity activity );
    void inject(FootprintActivity activity );
    void inject(SettingActivity activity );
    void inject(AboutActivity activity );
    void inject(AboutUsActivity activity );
    void inject(AgreementActivity activity );


    //h后续创建的activity 接着往下写
}
