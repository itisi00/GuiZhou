package com.itisi.guizhou.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.itisi.guizhou.app.App;
import com.itisi.guizhou.utils.SceneAnim;

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

    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mActivity = this;
        onViewCreated();
        setSwipeBackOriginal();
        initSwipeBack();//初始化 侧滑返回

        initEventAndData();

    }

    /**
     *禁止滑动关闭 主页可用
     */
    protected boolean setSwipeEnabled() {
      return true;
    }

    /**
     * 滑动关闭的方向 默认是从做左向右滑动
     * swipeBackOriginal=SwipeBackLayout.EDGE_LEFT;
     * 可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
     */
    public  int setSwipeBackOriginal(){
        return SwipeBackLayout.EDGE_LEFT;
    }

    private void initSwipeBack() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(setSwipeBackOriginal());
        mSwipeBackLayout.setEnableGesture(setSwipeEnabled());
    }


    public void setToolBar(Toolbar toolbar, String title) {
        // TODO: 2017/7/5 toolbar 待定

    }

    /**
     * 返回布局资源的id
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
        SceneAnim.closeActivityByScaleAlpha(this);
    }
}
