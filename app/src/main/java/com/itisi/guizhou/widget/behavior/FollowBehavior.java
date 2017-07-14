package com.itisi.guizhou.widget.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/14 15:10
 * 修改人:itisi
 * 修改时间: 2017/7/14 15:10
 * 修改内容:itisi
 * *********************
 */

public class FollowBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private int width,height,top,left;
    public FollowBehavior() {
    }

    public FollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        Logger.i(child.getTop()+":top");
        if (dependency.getY() == 0) {
            width = child.getWidth();
            height = child.getHeight();
            top = child.getTop();
            left = child.getLeft();
        }
        float percent = Math.abs(dependency.getY()) / 260;
        float yPercent = (float) (percent * 0.85);
        child.setY(top * (1 - yPercent));
        child.setX(left + 300 * percent);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        layoutParams.width = (int) (width * (1 - percent * 3 / 4));
        layoutParams.height = (int) (height * (1 - percent * 3 / 4));
        child.setLayoutParams(layoutParams);
        return true;
    }
}
