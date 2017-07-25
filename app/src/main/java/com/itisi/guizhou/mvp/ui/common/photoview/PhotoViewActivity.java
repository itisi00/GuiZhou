package com.itisi.guizhou.mvp.ui.common.photoview;

import android.view.View;

import com.itisi.guizhou.R;
import com.itisi.guizhou.app.App;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.imageload.ImageLoadConfiguration;
import com.itisi.guizhou.utils.imageload.ImageLoadProxy;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

/**
 ***********************
 * 功 能:查看单张图片 需要bundle 携带图片路径 key 为 url
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/25 10:56
 * 修改人:itisi
 * 修改时间: 2017/7/25 10:56
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class PhotoViewActivity extends RootActivity<PhotoViewPresenter> implements PhotoViewContract.View {

    @BindView(R.id.photoview)
    PhotoView mPhotoView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void initEventAndData() {
        StatusBarUtil.setTranslucent(this, 0);


        String imgUrl = getIntent().getExtras().getString("url");
        ImageLoadProxy.getInstance()
                .load(new ImageLoadConfiguration.Builder(App.getInstance())
                        .url(imgUrl)
                        .defaultImageResId(R.mipmap.splash_back4)
                        .imageView(mPhotoView).build());

        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ToastUtil.Info("长安");
                return true;
            }
        });
    }

    @Override
    public void testShowView(String smg) {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateSuccess() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
