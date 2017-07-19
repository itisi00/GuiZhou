package com.itisi.guizhou.mvp.ui.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

/**
 * **********************
 * 功 能:聊天会话的界面的适配器
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/19 18:02
 * 修改人:itisi
 * 修改时间: 2017/7/19 18:02
 * 修改内容:itisi
 * *********************
 */

public class ChatSessionAdapter  extends BaseQuickAdapter<MeiZiBean, BaseViewHolder> {

    public ChatSessionAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZiBean item) {

    }
}
