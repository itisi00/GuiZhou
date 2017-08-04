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
    //项目名称 很多地方 都应该以这个大头
    public static final String PROJECT_NAME="daguizhou";
    //数据库 名称 realm
    public static final String DB_NAME=PROJECT_NAME+".realm";
    //sharedpreference 名称
    public static final String SHARE_PREFERENCE_NAME = PROJECT_NAME;
    //软键盘高度
    public static final String SOFT_INPUT_HEIGHT = "soft_input_height";

    //搞什么的 已经搞忘了
    public static final String IT_GANK_TYPE = "gank_type";
    //福利缓存路径
    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + PROJECT_NAME + File.separator + "fuli";
    //相册的路径
    public static final String PATH_GALLERY = PROJECT_NAME;
    //相册的裁剪路径
    public static final String PATH_GALLERY_CROP = PROJECT_NAME+"/crop";



}
