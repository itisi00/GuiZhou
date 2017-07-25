package com.itisi.guizhou.mvp.ui.common.photoview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.itisi.guizhou.R;
import com.itisi.guizhou.app.App;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.imageload.ImageLoadConfiguration;
import com.itisi.guizhou.utils.imageload.ImageLoadProxy;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

@UseRxBus
public class PhotoViewPagerActivity extends RootActivity<PhotoViewPresenter> implements PhotoViewContract.View {

    @BindView(R.id.photoview_pager)
    HackyViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_view_pager;
    }

    @Override
    protected void initEventAndData() {
//        StatusBarUtil.setTranslucent(this,0);
        ArrayList<String> urls = getIntent().getExtras().getStringArrayList("urls");
        if (urls.size()>0){
            mViewPager.setAdapter(new SamplePagerAdapter(urls));
        }
    }

    @Override
    public void testShowView(String smg) {

    }

//    @Override
//    protected boolean isToolbarTransparent() {
//        return true;
//    }

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

    /**
     * 图片浏览器的适配器
     */
    static class SamplePagerAdapter extends PagerAdapter {

        private List<String> mUrlList;
        private PhotoView mPhotoView;

        public SamplePagerAdapter(List<String> urlList) {
            mUrlList = urlList;
        }

        @Override
        public int getCount() {
            return mUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            return super.instantiateItem(container, position);
            mPhotoView = new PhotoView(container.getContext());
            ImageLoadProxy.getInstance()
                    .load(new ImageLoadConfiguration.Builder(App.getInstance())
                            .url(mUrlList.get(position))
                            .defaultImageResId(R.mipmap.splash_back4)
                            .imageView(mPhotoView).build());
            mPhotoView.setFitsSystemWindows(true);
            container.addView(mPhotoView);
            mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ToastUtil.Info(mUrlList.get(position));
                    return true;
                }
            });
            return mPhotoView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);

        }
    }
}
