package com.itisi.guizhou.mvp.model.http;

import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.model.http.response.GankResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * **********************
 * 功 能:定义所有获取数据的接口
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 17:26
 * 修改人:itisi
 * 修改时间: 2017/7/5 17:26
 * 修改内容:itisi
 * *********************
 */
public interface HttpHelper {
    Observable<GankResponse<List<MeiZiBean>>> getMeiZiList(int num, int page);
}
