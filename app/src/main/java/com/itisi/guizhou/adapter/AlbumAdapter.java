package com.itisi.guizhou.adapter;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itisi.guizhou.R;
import com.itisi.guizhou.app.App;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/21 18:02
 * 修改人:itisi
 * 修改时间: 2017/7/21 18:02
 * 修改内容:itisi
 * *********************
 */

public class AlbumAdapter extends BaseQuickAdapter<MeiZiBean, BaseViewHolder> {

    public AlbumAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeiZiBean item) {
        //iv_cover tv_title tv_extend
        final ImageView view = helper.getView(R.id.iv_cover);
        //子控件添加事件
        helper.addOnClickListener(R.id.iv_cover);
        helper.addOnLongClickListener(R.id.iv_cover);
        //存在记录的高度时先Layout再异步加载图片
        if (item.getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = item.getHeight();
        }
        Glide.with(mContext).load(item.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(App.SCREEN_WIDTH / 2, App.SCREEN_WIDTH / 2) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        item.setHeight(App.SCREEN_WIDTH / 2);
                        ViewGroup.LayoutParams lp = view.getLayoutParams();
                        lp.height = App.SCREEN_WIDTH / 2;
                        view.setImageBitmap(resource);
                    }
                });
    }
}
