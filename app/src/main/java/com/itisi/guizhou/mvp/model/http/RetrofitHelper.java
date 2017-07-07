package com.itisi.guizhou.mvp.model.http;

import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.model.http.api.GankApi;
import com.itisi.guizhou.mvp.model.http.api.MyApi;
import com.itisi.guizhou.mvp.model.http.response.GankResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * **********************
 * 功 能:请求实现类
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 17:27
 * 修改人:itisi
 * 修改时间: 2017/7/5 17:27
 * 修改内容:itisi
 * *********************
 */

public class RetrofitHelper implements HttpHelper {

    private GankApi mGankApi;
    private MyApi mMyApi;

    @Inject
    public RetrofitHelper(GankApi gankApi,MyApi myApi) {
        mGankApi=gankApi;
        mMyApi=myApi;

    }

    @Override
    public Observable<GankResponse<List<MeiZiBean>>> getMeiZiList(int num, int page) {
        return mGankApi.getMeiZiList(num,page);
    }
}
