package com.itisi.guizhou.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.itisi.guizhou.R;
import com.itisi.guizhou.app.App;
import com.itisi.guizhou.utils.ActivityUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * **********************
 * 功 能:非MVP的Activity的基类
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 16:53
 * 修改人:itisi
 * 修改时间: 2017/7/5 16:53
 * 修改内容:itisi
 * *********************
 */

public abstract class NoMVPActivity extends SwipeBackActivity { //SwipeBackActivity AppCompatActivity
    protected Activity mActivity;
    private Unbinder mUnbinder;

    @BindView(R.id.toolbar_all)
    protected Toolbar mToolbar;
    //    @BindView(R.id.toolbar_search)
//    protected SearchView mSearchView;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_more)
    TextView mToolbalMore;
    protected SwipeBackLayout mSwipeBackLayout;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //将window的背景图设置为空
        // TODO: 2017/7/14  以后会在splash 里面做  清空背景
        getWindow().setBackgroundDrawable(null);

        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mActivity = this;
        onViewCreated();
        initToolbar();
//        initSearchView();
        setSwipeBackOriginal();
        initSwipeBack();//初始化 侧滑返回
        initEventAndData();
    }

    private void initSearchView() {
//        mSearchView.setIconifiedByDefault(true);//设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
//        mSearchView.onActionViewExpanded();// 写上此句后searchView初始是可以点击输入的状态，如果不写，
//        // 那么就需要点击下放大镜，才能出现输入框,也就是设置为ToolBar的ActionView，默认展开
//        mSearchView.requestFocus();//输入焦点
//        mSearchView.setSubmitButtonEnabled(false);//添加提交按钮，监听在OnQueryTextListener的onQueryTextSubmit响应
//        mSearchView.setFocusable(true);//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
//        mSearchView.setIconified(false);//输入框内icon不显示
//        mSearchView.requestFocusFromTouch();//模拟焦点点击事件
//
//        mSearchView.setFocusable(false);//禁止弹出输入法，在某些情况下有需要
//        mSearchView.clearFocus();//禁止弹出输入法，在某些情况下有需要
//        //事件监听
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                ToastUtil.Success(query);
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

    }

    /**
     * 初始化 toolbar
     */
    private void initToolbar() {

         boolean isShowNavigationIcon = setIsNavigationIconShow();//是否显示返回图标
         String title=setToolbarTitle();//标题
         String moreTxt= setToolbarMoreTxt();//更多-文字--可能会换成 字体图标
         int menuLayoutId=setMenuLayoutId();//溢出菜单布局id
         Toolbar.OnMenuItemClickListener onMenuItemClickListener=setMenuItemClickListener();//溢出菜单点击事件

        if (isShowNavigationIcon) {
            mToolbar.setNavigationIcon(R.mipmap.menu_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        //填充的方式 但是不够自由 切换图标 不够自由

        if (!TextUtils.isEmpty(title)) {
            mToolbarTitle.setText(title);
//            mToolbar.setTitle(title);
        }
        if (!TextUtils.isEmpty(moreTxt)) {
            mToolbalMore.setVisibility(View.VISIBLE);
            mToolbalMore.setText(moreTxt);
        }
        if (menuLayoutId != 0 && onMenuItemClickListener != null) {
            //设置 Toolbar menu
            mToolbar.inflateMenu(menuLayoutId);
            // 设置溢出菜单的图标 --这个图标可传  可不传
            mToolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.menu_three_circle));
            // 设置menu item 点击事件
            mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        }

    }

    /**
     * 是否显示返回按钮
     * @return
     */
    protected boolean setIsNavigationIconShow(){
        return true;
    }

    /**
     * 设置标题--此方法一般在进入页面的时候调用,且标题不会常变
     * @return
     */
    protected String setToolbarTitle() {
        return "";
    }

    /**
     * 设置标题--此方法一般用于动态改变title
     * @param title
     */
    protected void setToolbarTitle(String title){
        mToolbarTitle.setText(title);
    }


    /**
     * 设置更多---右边的文字 将来可换成子图图标
     * @return
     */
    protected String setToolbarMoreTxt() {
        return "";
    }

    /**
     * 给更多---设置单击事件--必须设置文本内容 菜单才会显示-才有点击效果
     * @param clickListener
     */
    protected void setToolbarMoreClickListener(View.OnClickListener clickListener){
        mToolbalMore.setOnClickListener(clickListener);
    }

    /**
     * 设置溢出菜单布局---还需设置溢出菜单的点击事件
     * @return
     */
    protected int setMenuLayoutId(){
        return 0;
    }

    /**
     * 设置溢出菜单的点击事件---还需设置溢出菜单布局
     * @return
     */
    protected Toolbar.OnMenuItemClickListener setMenuItemClickListener(){
        return null;
    }

    /**
     * 隐藏toolbar
     */
    protected void setToolbarHide(){
        mToolbar.setVisibility(View.GONE);
    }

    /**
     * 禁止滑动关闭 主页可用
     */
    protected boolean setSwipeEnabled() {
        return true;
    }

    /**
     * 滑动关闭的方向 默认是从做左向右滑动
     * swipeBackOriginal=SwipeBackLayout.EDGE_LEFT;
     * 可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
     */
    public int setSwipeBackOriginal() {
        return SwipeBackLayout.EDGE_LEFT;
    }

    private void initSwipeBack() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(setSwipeBackOriginal());
        mSwipeBackLayout.setEnableGesture(setSwipeEnabled());
    }


    /**
     * 返回布局资源的id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化事件和数据
     */
    protected abstract void initEventAndData();

    /**
     * 视图创建完成以后 可能需要做的事
     */
    protected void onViewCreated() {
        App.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        mUnbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        ActivityUtil.getInstance().closeActivity(this);
    }
}
