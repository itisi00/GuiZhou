package com.itisi.guizhou.app;

import android.os.Environment;

import java.io.File;

/**
 * **********************
 * 功 能:全局常量
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 16:28
 * 修改人:itisi
 * 修改时间: 2017/7/5 16:28
 * 修改内容:itisi
 * *********************
 */

public class Constants {
    //搞什么的 已经搞忘了
    public static final String IT_GANK_TYPE = "gank_type";
    //福利缓存路径
    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "daguizhou" + File.separator + "fuli";
    //相册的路径
    public static final String PATH_GALLERY = "daguizhou";
    //相册的裁剪路径
    public static final String PATH_GALLERY_CROP = "daguizhou/crop";

}
