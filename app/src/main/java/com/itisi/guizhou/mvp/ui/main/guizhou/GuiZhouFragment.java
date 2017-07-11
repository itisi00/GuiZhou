package com.itisi.guizhou.mvp.ui.main.guizhou;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.itisi.guizhou.R;
import com.itisi.guizhou.app.App;
import com.itisi.guizhou.base.RootFragment;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.imageload.ImageLoadConfiguration;
import com.itisi.guizhou.utils.imageload.ImageLoadProxy;
import com.yalantis.phoenix.PullToRefreshView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 大贵州-主页
 */
public class GuiZhouFragment extends RootFragment<GuiZhouPresenter> implements GuiZhouContract.View,View.OnClickListener {

    @BindView(R.id.banner_guizhou)
    Banner mBanner;

    @BindView(R.id.pullrefresh)
    PullToRefreshView mPullToRefreshView;
    private int pageSize=10;
    private int pageIndex=1;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_gui_zhou;
    }

    @Override
    protected void initEventAndData() {
        initRepresh();
        initBannerListener();
        initBannerData();
    }

    private void initRepresh() {
        mPullToRefreshView .setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView .postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Logger.i("刷新数据");
                        mPullToRefreshView .setRefreshing(false);
                    }
                },3000);
//                mPresenter.loadData(pageSize,pageIndex);
            }
        });
    }


    private void initBannerData() {
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //事实上  这个数据 应该在presenter 里面获取 临时征用了
        List<String> images=new ArrayList<>();
        List<String>titles=new ArrayList<>();
        titles.add("风景1");
        titles.add("风景2");
        titles.add("风景3");
        titles.add("风景4");
        titles.add("风景5");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491211711&di=dc34fbb078c525a0f0b6b3e6906ec21d&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tuku.cn%2Ffile_big%2F201502%2Fd130653bfb884152b8a5ba9e846362d1.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490616877150&di=18a87f61829c7c7787d33bc27d781dec&imgtype=0&src=http%3A%2F%2Ftupian.enterdesk.com%2F2016%2Fhxj%2F08%2F16%2F1602%2FChMkJlexsJmIIe8dAAghrgQhdMQAAUdNAJInfYACCHG699.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490616877150&di=406214a0adc6c4f6bb2f5dc51c37dad9&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1608%2F31%2Fc12%2F26348420_1472658812267_800x800.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490616877149&di=0d90b7bbc175410a715bf59cec6d153b&imgtype=0&src=http%3A%2F%2Fbbs.crsky.com%2F1236983883%2FMon_1209%2F25_187069_eaac13adbd074a5.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490616917781&di=edd04f734bb2a25485d385525f488644&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201505%2F27%2F172521n6az6c7477d66cxa.jpg");
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object o, ImageView imageView) {
                ImageLoadProxy.getInstance()
                        .load(new ImageLoadConfiguration.Builder(App.getInstance())
                                .url(o)
                                .defaultImageResId(R.mipmap.test_menu_love_white)
                                .imageView(imageView).build());

            }
        });
        mBanner.setImages(images);
        mBanner.setBannerTitles(titles);
        ////指示器 垂直显示 默认水平显示
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
    }

    private void initBannerListener() {
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int i) {
                ToastUtil.Success("轮播图 点击位置 :"+i);
            }
        });
    }


    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showContent(String list) {

    }

    @Override
    public void useNightMode(boolean isNight) {

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
    public void onStart() {
        super.onStart();
        if (mBanner!=null){
            mBanner.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner!=null){
            mBanner.stopAutoPlay();
        }
    }

}
