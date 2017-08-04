package com.itisi.guizhou.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/17 16:52
 * 修改人:itisi
 * 修改时间: 2017/7/17 16:52
 * 修改内容:itisi
 * *********************
 */

public class RecuitAdapter  extends BaseQuickAdapter<MeiZiBean, BaseViewHolder> {
    public RecuitAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZiBean item) {
    }
}
