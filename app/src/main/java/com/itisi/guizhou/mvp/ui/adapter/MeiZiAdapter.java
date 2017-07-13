package com.itisi.guizhou.mvp.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.view.View;
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
import com.itisi.guizhou.utils.ToastUtil;

/**
 * **********************
 * 功 能:妹纸适配器
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/13 10:50
 * 修改人:itisi
 * 修改时间: 2017/7/13 10:50
 * 修改内容:itisi
 * *********************
 */

public class MeiZiAdapter extends BaseQuickAdapter<MeiZiBean, BaseViewHolder>
        {

    public MeiZiAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeiZiBean item) {
//        ImageLoadProxy.getInstance()
//                .load(new ImageLoadConfiguration.Builder(App.getInstance())
//                        .url(item.getUrl())
//                        .defaultImageResId(R.mipmap.test_menu_love_white)
//                        .imageView((ImageView) helper.getView(R.id.iv_meizi)).build());

        final ImageView view = helper.getView(R.id.iv_meizi);

        //存在记录的高度时先Layout再异步加载图片
        if (item.getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = item.getHeight();
        }


        Glide.with(mContext).load(item.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(App.SCREEN_WIDTH / 2, App.SCREEN_WIDTH / 2) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        int realHeight = (App.SCREEN_WIDTH / 2) * height / width;
                        item.setHeight(realHeight);
                        ViewGroup.LayoutParams lp = view.getLayoutParams();
                        lp.height = realHeight;
                        view.setImageBitmap(resource);
                    }
                });
    }

}
