package com.itisi.guizhou.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

/**
 * **********************
 * 功 能:纪念日
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/24 17:54
 * 修改人:itisi
 * 修改时间: 2017/7/24 17:54
 * 修改内容:itisi
 * *********************
 */

public class AnniversaryAdapter extends BaseQuickAdapter<MeiZiBean, BaseViewHolder> {

    public AnniversaryAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZiBean item) {

    }
}
