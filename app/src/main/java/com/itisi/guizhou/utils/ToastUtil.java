package com.itisi.guizhou.utils;

import com.itisi.guizhou.app.App;
import com.sdsmdg.tastytoast.TastyToast;

/**
 ***********************
 * 功 能:基于TastyToast 的toast再次封装
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/7 10:25
 * 修改人:itisi
 * 修改时间: 2017/7/7 10:25
 * 修改内容:itisi
 * *********************
 */
public class ToastUtil {

    public static void Success(String msg){
       TastyToast.makeText(App.getInstance(),msg,1,TastyToast.SUCCESS).show();
    }
    public static void Error(String msg){
        TastyToast.makeText(App.getInstance(),msg,1,TastyToast.ERROR).show();
    }
    public static void Info(String msg){
        TastyToast.makeText(App.getInstance(),msg,1,TastyToast.INFO).show();
    }
    public static void Warning(String msg){
        TastyToast.makeText(App.getInstance(),msg,1,TastyToast.WARNING).show();
    }
    public static void Confusing(String msg){
        TastyToast.makeText(App.getInstance(),msg,1,TastyToast.CONFUSING).show();
    }

}
