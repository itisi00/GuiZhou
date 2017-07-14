package com.itisi.guizhou.widget.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定 behavior
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/14 14:40
 * 修改人:itisi
 * 修改时间: 2017/7/14 14:40
 * 修改内容:itisi
 * *********************
 */

public class MyBottomBarBehavior extends CoordinatorLayout.Behavior<View> {
    public MyBottomBarBehavior() {
    }

    public MyBottomBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 只要是CoordinatorLayout内的View的状态发送了变化,该方法就会执行
     * @param parent 顶层父控件CoordinatorLayout
     * @param child 我们设置这个Behavior的View
     * @param dependency View的值会不断的变化,他会轮询CoordinatorLayout下所有所属的子View
     * @return 我们需要在这里判断dependency所属的View是哪一个,如果是ture执行  onDependentViewChanged,否则不执行
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        return super.layoutDependsOn(parent, child, dependency);
        return dependency instanceof AppBarLayout; //多个的时候需要判断

    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        return super.onDependentViewChanged(parent, child, dependency);
        /*
         *这里获取dependency的top值,也就是AppBarLayout的top,因为AppBarLayout
         *在是向上滚出界面的,我们的因为是和AppBarLayout相反,所以取绝对值.
         */
        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);
        return true;
    }
}
