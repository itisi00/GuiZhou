package com.itisi.guizhou.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.itisi.guizhou.di.component.AppComponent;
import com.itisi.guizhou.di.component.DaggerAppComponent;
import com.itisi.guizhou.di.module.AppModule;
import com.itisi.guizhou.di.module.HttpModule;
import com.itisi.guizhou.mvp.ui.chat.EaseMobUtil;
import com.orhanobut.logger.Logger;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;

/**
 * **********************
 * 功 能:全局应用程序类
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 16:28
 * 修改人:itisi
 * 修改时间: 2017/7/5 16:28
 * 修改内容:itisi
 * *********************
 */
public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;
    public static AppComponent appComponent;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    static {
        //初始化主题
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initAll();
        //初始化屏幕宽高
        getScreenSize();

    }

    private void initAll() {
        //初始化数据库
        Realm.init(getApplicationContext());
        //在子线程中完成其他初始化 异常检测 回头再说
//        InitializeService.start(this);
        Logger.init();//初始化日志信息
//        RxBus.getInstance().init(this); //不能在这里初始化

        EaseMobUtil.init(this);//初始化环信
    }

    /**
     * 获取全局应用程序上下文
     * @return
     */
    public static synchronized App getInstance(){
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);////分割Dex，方法数超过64k
    }

    /**
     * 将当前activity 实例添加到集合中
     * @param activity 当前activity
     */
    public void addActivity(Activity activity){
        if (allActivities==null){
            allActivities=new HashSet<>();
        }
        allActivities.add(activity);
    }

    /**
     * 将当前activity 实例从集合中移除
     * @param activity 当前activity
     */
    public void removeActivity(Activity activity){
        if (allActivities!=null){
            allActivities.remove(activity);
        }
    }

    /**
     * 获得屏幕尺寸
     */
    public void getScreenSize() {
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }
    /**
     * 退出app
     */
    public void exitApp(){
        if (allActivities!=null){
            synchronized (allActivities){
                for (Activity activity:allActivities){
                    activity.finish();
                }
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    public static AppComponent getAppComponent(){
        if (appComponent==null){
            appComponent= DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }
}
