package com.itisi.guizhou.mvp.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.itisi.guizhou.R;
import com.itisi.guizhou.app.App;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.ui.main.chat.ChatSessionFragment;
import com.itisi.guizhou.mvp.ui.main.guizhou.GuiZhouFragment;
import com.itisi.guizhou.mvp.ui.main.home.HomeFragment;
import com.itisi.guizhou.mvp.ui.main.leisure.LeisureFragment;
import com.itisi.guizhou.mvp.ui.main.news.NewsFragment;
import com.itisi.guizhou.utils.ClickTree;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.RxBus;
import com.itisi.guizhou.utils.rxbus.annotation.Subscribe;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.itisi.guizhou.utils.rxbus.event.EventThread;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingMenuLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@UseRxBus
public class MainActivity extends RootActivity<MainPresenter> implements MainContract.View {

    //    @BindView(R.id.tv_test)
//    TextView tv_test;

    //主页 布局
    @BindView(R.id.flowing_drawer)
    FlowingDrawer mDrawer;
    @BindView(R.id.flowing_menu)
    FlowingMenuLayout fm_menu;
    @BindView(R.id.bottom_main)
    BottomNavigationBar bottom_main;


    //5个主界面
    HomeFragment mHomeFragment;//主页
    NewsFragment mNewsFragment;//新闻
    GuiZhouFragment mGuiZhouFragment;//大贵州
    LeisureFragment mLeisureFragment;//休闲
    ChatSessionFragment mChatFragment;//聊天

    ClickTree mClickTree = new ClickTree(2); //点击树
    private List<Fragment> mFragments;//main 中的几个页面的fragment集合
    private Fragment mCurrentFragment;//home中当前显示的fragment
    private boolean isShown = false;//菜单是否处于打开状态


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean setIsNavigationIconShow() {
        return false;
    }

    @Override
    protected String setToolbarTitle() {
        return "可以显示嘛";
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        initMainPage();
        initBottomNav();
        initBottomListener();
        initMenuListener();
        initDrawerMenu();

    }

    /**
     * 设置侧滑菜单的 滑动范围为全屏
     */
    private void initDrawerMenu() {
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
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
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
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
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        mCurrentFragment = mFragments.get(position);
                        if (mCurrentFragment.isAdded()) {
                            transaction.replace(R.id.fl_main, mCurrentFragment);
                        } else {
                            transaction.add(R.id.fl_main, mCurrentFragment);
                        }
                        // transaction.commit();
                        transaction.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onTabUnselected(int position) {
//                Logger.i("unselected:" + position);
                if (mFragments != null) {
                    if (position < mFragments.size()) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.remove(mFragments.get(position));
                        transaction.commitAllowingStateLoss();

                    }
                }

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

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
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_main, mHomeFragment);
        transaction.commit();
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
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setText("66")
                .setHideOnSelect(false);
        bottom_main
                .setActiveColor(R.color.colorAccent)
                .setInActiveColor(R.color.colorGray)
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_home_white, R.string.menu_home)
                        .setActiveColor("#00776a"))
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_time_white, R.string.menu_news)
                        .setActiveColor("#8d6b63")
                        .setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_love_white, R.string.menu_guizhou)
                        .setActiveColor("#2293f4"))
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_music_white, R.string.menu_leisure)
                        .setActiveColor("#ff4081"))
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_chat_white, R.string.menu_chat)
                        .setActiveColor("#9966cc"))
                .setFirstSelectedPosition(0)
                .initialise();
    }


    @Override
    protected boolean setSwipeEnabled() {
        return false;
    }

    @Override
    public void testShowView(String smg) {
//        tv_test.setText(smg);
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
    public void onBackPressed() {
        if (isShown) {//菜单打开的情况下 关闭菜单
            isShown = false;
            mDrawer.closeMenu(true);
        } else {
            boolean clickResult = mClickTree.completeClickCount();
            if (clickResult) {
//                System.exit(0);//直接杀死进程???? 是不是不妥
                App.getInstance().exitApp();
            } else {
                ToastUtil.Info("双击退出程序");

            }
        }
    }
}