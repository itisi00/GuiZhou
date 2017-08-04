package com.itisi.guizhou.mvp.ui.main;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hyphenate.EMCallBack;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.ui.about.AboutActivity;
import com.itisi.guizhou.mvp.ui.account.AccountActivity;
import com.itisi.guizhou.mvp.ui.agenda.AgendaActivity;
import com.itisi.guizhou.mvp.ui.album.AlbumActivity;
import com.itisi.guizhou.mvp.ui.anniversary.AnniversaryActivity;
import com.itisi.guizhou.mvp.ui.chat.EaseMobUtil;
import com.itisi.guizhou.mvp.ui.collection.CollectionActivity;
import com.itisi.guizhou.mvp.ui.fadeback.FadebackActivity;
import com.itisi.guizhou.mvp.ui.footprint.FootprintActivity;
import com.itisi.guizhou.mvp.ui.main.chatsession.ChatSessionFragment;
import com.itisi.guizhou.mvp.ui.main.guizhou.GuiZhouFragment;
import com.itisi.guizhou.mvp.ui.main.home.HomeFragment;
import com.itisi.guizhou.mvp.ui.main.leisure.LeisureFragment;
import com.itisi.guizhou.mvp.ui.main.news.NewsFragment;
import com.itisi.guizhou.mvp.ui.setting.SettingActivity;
import com.itisi.guizhou.mvp.ui.user.login.LoginActivity;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.ClickTree;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.RxBus;
import com.itisi.guizhou.utils.rxbus.annotation.Subscribe;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.itisi.guizhou.utils.rxbus.event.EventThread;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingMenuLayout;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

@UseRxBus
public class MainActivity extends RootActivity<MainPresenter> implements MainContract.View
        , View.OnClickListener {

    //    @BindView(R.id.tv_test)
//    TextView tv_test;

    //主页 布局
    @BindView(R.id.flowing_drawer)
    FlowingDrawer mDrawer;
    @BindView(R.id.flowing_menu)
    FlowingMenuLayout fm_menu;
    @BindView(R.id.bottom_main)
    BottomNavigationBar bottom_main;
    @BindView(R.id.menu_left_agenda)
    TextView menu_left_agenda;
    @BindView(R.id.menu_left_anniversary)
    TextView menu_left_birthday;
    @BindView(R.id.menu_left_account)
    TextView menu_left_account;
    @BindView(R.id.menu_left_footprint)
    TextView menu_left_footprint;
    @BindView(R.id.menu_left_album)
    TextView menu_left_photo;
    @BindView(R.id.menu_left_collection)
    TextView menu_left_collection;
    @BindView(R.id.menu_left_setting)
    TextView menu_left_setting;
    @BindView(R.id.menu_left_about)
    TextView menu_left_about;
    @BindView(R.id.menu_left_fadeback)
    TextView menu_left_fadeback;

    @BindView(R.id.iv_left_header)
    ImageView iv_left_header;
    @BindView(R.id.tv_left_username)
    TextView tv_left_username;


    //5个主界面
    HomeFragment mHomeFragment;//主页
    NewsFragment mNewsFragment;//新闻
    GuiZhouFragment mGuiZhouFragment;//大贵州
    LeisureFragment mLeisureFragment;//休闲
    ChatSessionFragment mChatFragment;//聊天

    String[] mBottomItems = null;

    ClickTree mClickTree = new ClickTree(2); //点击树
    private List<Fragment> mFragments;//main 中的几个页面的fragment集合
    private Fragment mCurrentFragment;//home中当前显示的fragment
    private boolean isShown = false;//菜单是否处于打开状态
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;


    @Override
    protected int getLayoutId() {
        initPermission();
        return R.layout.activity_main;
    }

    @Override
    protected boolean setIsNavigationIconShow() {
        return false;
    }

    @Override
    protected String setToolbarTvTitle() {
        if (mBottomItems == null) {
            mBottomItems = getResources().getStringArray(R.array.bottomMenu);
        }
        return mBottomItems[0];
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
//        StatusBarUtil.setTranslucent(this);
//        StatusBarUtil.hideFakeStatusBarView(this);
//        StatusBarUtil.setTranslucent(this, 0);//不加0 是半透明效果
//        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorAccent));





        if (mBottomItems == null) { //这里估计不会走 因为 在 setToolbarTvTitle 已经获取一次了
            mBottomItems = getResources().getStringArray(R.array.bottomMenu);
        }
        mFragmentManager = getSupportFragmentManager();
        initMainPage();
        initBottomNav();
        initBottomListener();
        initMenuListener();
        initDrawerMenu();

        initViewListener();
// TODO: 2017/7/20  环信临时登陆 将来肯定要挪地方的
        EaseMobUtil.login("itisi", "itisi", new EMCallBack() {
            @Override
            public void onSuccess() {
                Logger.i("onSuccess");
            }

            @Override
            public void onError(int code, String error) {
                Logger.i("onError:" + error);
            }

            @Override
            public void onProgress(int progress, String status) {
            }
        });

    }

    /**
     * 申请权限
     */
    private void initPermission() {
        RxPermissions rxPermissions=new RxPermissions(this);
        // TODO: 2017/7/27 权限申请日志 发布需关闭
        rxPermissions.setLogging(true);
        rxPermissions.requestEach(
//                Manifest.permission.CAMERA,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.READ_CONTACTS,
//                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {
                        String name = permission.name;
                    }
                });

    }

    private void initViewListener() {
        menu_left_agenda.setOnClickListener(this);
        menu_left_birthday.setOnClickListener(this);
        menu_left_account.setOnClickListener(this);
        menu_left_footprint.setOnClickListener(this);
        menu_left_photo.setOnClickListener(this);
        menu_left_collection.setOnClickListener(this);
        menu_left_setting.setOnClickListener(this);
        menu_left_about.setOnClickListener(this);
        menu_left_fadeback.setOnClickListener(this);
        iv_left_header.setOnClickListener(this);
        tv_left_username.setOnClickListener(this);
    }

    /**
     * 设置侧滑菜单的 滑动范围为全屏
     */
    private void initDrawerMenu() {
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
    }

    public void setDrawerMenuFullscreen() {
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
    }

    public void setDrawerMenuLeftSlide() {
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
    }

    private void initMenuListener() {
        //侧滑菜单 滑动监听
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    isShown = false;
                } else if (newState == ElasticDrawer.STATE_OPEN) {
                    isShown = true;
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
//                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });
    }

    /**
     * 初始化底部导航点击事件
     */
    private void initBottomListener() {
        //底部 bottomnavigation点击事件
        bottom_main.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (mFragments != null) {
                    if (position < mFragments.size()) {
                        mTransaction = mFragmentManager.beginTransaction();
                        mCurrentFragment = mFragments.get(position);
                        if (mCurrentFragment.isAdded()) {
                            mTransaction.replace(R.id.fl_main, mCurrentFragment);
                        } else {
                            mTransaction.add(R.id.fl_main, mCurrentFragment);
                        }
                        // transaction.commit();
                        mTransaction.commitAllowingStateLoss();
                    }
                    changeToolbarTitle(position);
                }
            }

            @Override
            public void onTabUnselected(int position) {
//                Logger.i("unselected:" + position);
                if (mFragments != null) {
                    if (position < mFragments.size()) {
                        mTransaction = mFragmentManager.beginTransaction();
                        mTransaction.remove(mFragments.get(position));
                        mTransaction.commitAllowingStateLoss();

                    }
                }

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    private void changeToolbarTitle(int position) {
        setToolbarTvTitle(mBottomItems[position]);
    }

    /**
     * 初始化页面
     */
    private void initMainPage() {
        getFragments();
        setDefaultFragment();
    }

    /**
     * 设置默认页面
     */
    private void setDefaultFragment() {
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.fl_main, mHomeFragment);
        mTransaction.commit();
    }

    /**
     * 初始化页面集合
     */
    private void getFragments() {
        mFragments = new ArrayList<>();
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        mFragments.add(mHomeFragment);
        if (mNewsFragment == null) {
            mNewsFragment = new NewsFragment();
        }
        mFragments.add(mNewsFragment);

        if (mGuiZhouFragment == null) {
            mGuiZhouFragment = new GuiZhouFragment();
        }
        mFragments.add(mGuiZhouFragment);

        if (mLeisureFragment == null) {
            mLeisureFragment = new LeisureFragment();
        }
        mFragments.add(mLeisureFragment);

        if (mChatFragment == null) {
            mChatFragment = new ChatSessionFragment();
        }
        mFragments.add(mChatFragment);
    }

    /**
     * 初始化底部导航
     */
    private void initBottomNav() {
//主页底部导航栏
        //角标
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setText("66")
                .setHideOnSelect(false);
//00695c  00776a  4a148c aa00ff  9966cc
        bottom_main
                .setActiveColor(R.color.colorAccent)
                .setInActiveColor(R.color.colorGray)
                .addItem(new BottomNavigationItem(R.mipmap.bottom_menu_home_white, mBottomItems[0])
                        .setActiveColor("#009688"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_menu_time_white, mBottomItems[1])
                        .setActiveColor("#00695c"))
//                        .setBadgeItem(numberBadgeItem)) //多一个括号 少一个括号的问题
                .addItem(new BottomNavigationItem(R.mipmap.bottom_menu_love_white, mBottomItems[2])
                        .setActiveColor("#00796b"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_menu_music_white, mBottomItems[3])
                        .setActiveColor("#00897b"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_menu_chat_white, mBottomItems[4])
                        .setActiveColor("#009688"))
                .setFirstSelectedPosition(0)
                .initialise();
    }


    @Override
    protected String setToolbarMoreTxt() {
        setToolbarMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.Error("天气界面?");
            }
        });
        return "18℃";
        // TODO: 2017/7/11 得请求网络天气
    }

    @Override
    protected boolean setSwipeEnabled() {
        return false;
    }

    @Override
    public void testShowView(String smg) {
    }

    @Subscribe(tag = RxBus.TAG_UPDATE, thread = EventThread.MAIN_THREAD)
    public void testRx(String message) {
//        Logger.i("rxbus接受数据了"+message);
//        tv_test.setText(message);

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
    public void onClick(View view) {
        if (mDrawer.isShown()){
            mDrawer.closeMenu(false);
        }
        switch (view.getId()) {
            case R.id.iv_left_header:
                ActivityUtil.getInstance().openActivity(mActivity, LoginActivity.class);
                break;
            case R.id.menu_left_agenda:
                ActivityUtil.getInstance().openActivity(mActivity, AgendaActivity.class);

                break;
            case R.id.menu_left_anniversary:
                ActivityUtil.getInstance().openActivity(mActivity, AnniversaryActivity.class);

                break;
            case R.id.menu_left_account:
                ActivityUtil.getInstance().openActivity(mActivity, AccountActivity.class);

                break;
            case R.id.menu_left_footprint:
                ActivityUtil.getInstance().openActivity(mActivity, FootprintActivity.class);

                break;
            case R.id.menu_left_album:
                ActivityUtil.getInstance().openActivity(mActivity, AlbumActivity.class);

                break;
            case R.id.menu_left_collection:
                ActivityUtil.getInstance().openActivity(mActivity, CollectionActivity.class);

                break;
            case R.id.menu_left_setting:
                ActivityUtil.getInstance().openActivity(mActivity, SettingActivity.class);

                break;
            case R.id.menu_left_about:
                ActivityUtil.getInstance().openActivity(mActivity, AboutActivity.class);

                break;
            case R.id.menu_left_fadeback:
                ActivityUtil.getInstance().openActivity(mActivity, FadebackActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isShown) {//菜单打开的情况下 关闭菜单
            isShown = false;
            mDrawer.closeMenu(true);
        } else {
            boolean clickResult = mClickTree.completeClickCount();
            if (clickResult) {
//                System.exit(0);//直接杀死进程???? 是不是不妥
//                App.getInstance().exitApp();

//                https://juejin.im/post/58c407ee44d90400698757d8
                //来自上面链接
                Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
                launcherIntent.addCategory(Intent.CATEGORY_HOME);
                startActivity(launcherIntent);

            } else {
                ToastUtil.Info("双击退出程序");

            }
        }
    }

}