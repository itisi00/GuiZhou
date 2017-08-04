package com.itisi.guizhou.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itisi.guizhou.R;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

import static com.itisi.guizhou.R.id.tv_name;

/**
 * **********************
 * 功 能:相册名称
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/21 18:02
 * 修改人:itisi
 * 修改时间: 2017/7/21 18:02
 * 修改内容:itisi
 * *********************
 */

public class AlbumNamesAdapter extends BaseQuickAdapter<MeiZiBean, BaseViewHolder> {

    public AlbumNamesAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeiZiBean item) {
        final ImageView view = helper.getView(R.id.iv_cover);
        helper.addOnClickListener(R.id.iv_cover);
       Glide.with(mContext).load(item.getUrl()).into(view);
        helper.setText(tv_name,"说说和日志的相册"+ helper.getLayoutPosition());
        helper.setText(R.id.tv_desc,helper.getLayoutPosition()+"张     仅自己可见");
    }
}
