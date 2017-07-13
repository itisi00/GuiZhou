package com.itisi.guizhou.mvp.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
public class MainActivity extends RootActivity<MainPresenter> implements MainContract.View
        ,View.OnClickListener{

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
    @BindView(R.id.menu_left_birthday)
    TextView menu_left_birthday;
    @BindView(R.id.menu_left_account)
    TextView menu_left_account;
    @BindView(R.id.menu_left_footprint)
    TextView menu_left_footprint;
    @BindView(R.id.menu_left_photo)
    TextView menu_left_photo;
    @BindView(R.id.menu_left_collection)
    TextView menu_left_collection;
    @BindView(R.id.menu_left_setting)
    TextView menu_left_setting;
    @BindView(R.id.menu_left_about)
    TextView menu_left_about;
    @BindView(R.id.menu_left_fadeback)
    TextView menu_left_fadeback;


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
        return R.layout.activity_main;
    }

    @Override
    protected boolean setIsNavigationIconShow() {
        return false;
    }

    @Override
    protected String setToolbarTitle() {
        if (mBottomItems==null){
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
        if (mBottomItems==null){ //这里估计不会走 因为 在 setToolbarTitle 已经获取一次了
            mBottomItems = getResources().getStringArray(R.array.bottomMenu);
        }
        mFragmentManager = getSupportFragmentManager();
        initMainPage();
        initBottomNav();
        initBottomListener();
        initMenuListener();
        initDrawerMenu();

        initViewListener();
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
    }

    /**
     * 设置侧滑菜单的 滑动范围为全屏
     */
    private void initDrawerMenu() {
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
    }

    public void setDrawerMenuFullscreen(){
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
    }
    public void setDrawerMenuLeftSlide(){
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

    private void changeToolbarTitle(int position){
        setToolbarTitle(mBottomItems[position]);
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
        mTransaction= mFragmentManager.beginTransaction();
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
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setText("66")
                .setHideOnSelect(false);
        bottom_main
                .setActiveColor(R.color.colorAccent)
                .setInActiveColor(R.color.colorGray)
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_home_white, mBottomItems[0])
                        .setActiveColor("#00776a"))
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_time_white, mBottomItems[1] )
                        .setActiveColor("#8d6b63")
                        .setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_love_white, mBottomItems[2])
                        .setActiveColor("#2293f4"))
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_music_white,mBottomItems[3])
                        .setActiveColor("#ff4081"))
                .addItem(new BottomNavigationItem(R.mipmap.test_menu_chat_white, mBottomItems[4] )
                        .setActiveColor("#9966cc"))
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

//    @Override
//    protected void setToolbarMoreClickListener(View.OnClickListener clickListener) {
//        super.setToolbarMoreClickListener(clickListener);
//    }

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_left_agenda:
                ToastUtil.Success(menu_left_agenda.getText().toString());
                break;
            case R.id.menu_left_birthday:
                ToastUtil.Success(menu_left_birthday.getText().toString());
                break;
            case R.id.menu_left_account:
                ToastUtil.Success(menu_left_account.getText().toString());
                break;
            case R.id.menu_left_footprint:
                ToastUtil.Success(menu_left_footprint.getText().toString());
                break;
            case R.id.menu_left_photo:
                ToastUtil.Success(menu_left_photo.getText().toString());
                break;
            case R.id.menu_left_collection:
                ToastUtil.Success(menu_left_collection.getText().toString());
                break;
            case R.id.menu_left_setting:
                ToastUtil.Success(menu_left_setting.getText().toString());
                break;
            case R.id.menu_left_about:
                ToastUtil.Success(menu_left_about.getText().toString());
                break;
            case R.id.menu_left_fadeback:
                ToastUtil.Success(menu_left_fadeback.getText().toString());
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
                App.getInstance().exitApp();
            } else {
                ToastUtil.Info("双击退出程序");

            }
        }
    }


}