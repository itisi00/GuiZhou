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
import com.itisi.guizhou.mvp.model.bean.SelectedImgBean;

import java.io.File;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/1 16:08
 * 修改人:itisi
 * 修改时间: 2017/8/1 16:08
 * 修改内容:itisi
 * *********************
 */

public class SelectedImgAdapter extends BaseQuickAdapter<SelectedImgBean, BaseViewHolder> {

    public SelectedImgAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SelectedImgBean item) {
        final ImageView view = helper.getView(R.id.iv_selected_img);
        //子控件添加事件
        helper.addOnClickListener(R.id.iv_selected_img);
        helper.addOnLongClickListener(R.id.iv_selected_img);
        //存在记录的高度时先Layout再异步加载图片
        if (item.getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = item.getHeight();
        }

        if (item.getThumbPath().equals("-1")) {
            Glide.with(mContext).load(R.mipmap.icon_picture_add).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(App.SCREEN_WIDTH / 3, 220) {// App.SCREEN_WIDTH / 2-145
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            int realHeight =220;// (App.SCREEN_WIDTH / 2) * height / width-145;
                            item.setHeight(realHeight);
                            ViewGroup.LayoutParams lp = view.getLayoutParams();
                            lp.height = realHeight;
                            view.setImageBitmap(resource);
                        }
                    });

        } else {

            Glide.with(mContext).load(new File(item.getOriginalPath())).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(App.SCREEN_WIDTH / 3, 220) {//App.SCREEN_WIDTH / 2-145
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            int realHeight = 220;//(App.SCREEN_WIDTH / 2) * height / width-145;
                            item.setHeight(realHeight);
                            ViewGroup.LayoutParams lp = view.getLayoutParams();
                            lp.height = realHeight;
                            view.setImageBitmap(resource);
                        }
                    });
        }

    }
}
