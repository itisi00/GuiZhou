package com.itisi.guizhou.mvp.model;

import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.model.db.DBHelper;
import com.itisi.guizhou.mvp.model.http.HttpHelper;
import com.itisi.guizhou.mvp.model.http.response.GankResponse;
import com.itisi.guizhou.mvp.model.prefs.PreferencesHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 17:25
 * 修改人:itisi
 * 修改时间: 2017/7/5 17:25
 * 修改内容:itisi
 * *********************
 */

public class DataManager implements HttpHelper,DBHelper,PreferencesHelper{

    HttpHelper mHttpHelper;
    DBHelper mDBHelper;
    PreferencesHelper mPreferencesHelper;

    public DataManager(HttpHelper httpHelper, DBHelper DBHelper, PreferencesHelper preferencesHelper) {
        mHttpHelper = httpHelper;
        mDBHelper = DBHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public boolean getNightModeState() {
        return false;
    }

    @Override
    public void setNightModeState(boolean state) {

    }

    @Override
    public boolean queryNewsId(int id) {
        return false;
    }


    @Override
    public Observable<GankResponse<List<MeiZiBean>>> getMeiZiList(int num, int page) {
        return mHttpHelper.getMeiZiList(num,page);
    }
}

















