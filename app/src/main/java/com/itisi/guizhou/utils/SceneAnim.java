package com.itisi.guizhou.utils;

import android.app.Activity;

import com.itisi.guizhou.R;


/**
 ***********************
 * 功 能:转场动画 老式的 5.0 以前的
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/7 14:16
 * 修改人:itisi
 * 修改时间: 2017/7/7 14:16
 * 修改内容:itisi
 * *********************
 */
public class SceneAnim {
    /**
     * 打开一个新的界面 缩放 和透明度动画
     * @param activity
     */
    public static void openActivityByScaleAlpha(Activity activity){
        activity.overridePendingTransition(R.anim.zoom,0);
    }
    /**
     * 关闭一个新的界面 缩放 和透明度动画
     * @param activity
     */
    public static void closeActivityByScaleAlpha(Activity activity){
        activity.overridePendingTransition(0,R.anim.scale_out);
    }

}
