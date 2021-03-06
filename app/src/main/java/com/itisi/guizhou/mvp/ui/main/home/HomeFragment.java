package com.itisi.guizhou.mvp.ui.main.home;


import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.widget.ActionSheetDialog;
import com.itisi.guizhou.R;
import com.itisi.guizhou.app.App;
import com.itisi.guizhou.base.RootFragment;
import com.itisi.guizhou.mvp.ui.blacknum.BlackNumActivity;
import com.itisi.guizhou.mvp.ui.ittool.ItToolActivity;
import com.itisi.guizhou.mvp.ui.jingxuan.JingXuanActivity;
import com.itisi.guizhou.mvp.ui.mingyan.MingYanActivity;
import com.itisi.guizhou.mvp.ui.read.ReadActivity;
import com.itisi.guizhou.mvp.ui.recuit.RecuitActivity;
import com.itisi.guizhou.mvp.ui.rental.RentalActivity;
import com.itisi.guizhou.mvp.ui.websit.WebsitActivity;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.imageload.ImageLoadConfiguration;
import com.itisi.guizhou.utils.imageload.ImageLoadProxy;
import com.itisi.guizhou.utils.update.DownloadProgress;
import com.itisi.guizhou.utils.update.RxUpdateUtil;
import com.orhanobut.logger.Logger;
import com.yalantis.phoenix.PullToRefreshView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * 首页-主页
 */
public class HomeFragment extends RootFragment<HomePresenter>
        implements HomeContract.View
        , View.OnClickListener {

//    @BindView(R.id.tv_test1)
//    TextView tv_test;


    @BindView(R.id.banner_home)
    Banner mBanner;

    @BindView(R.id.pullrefresh)
    PullToRefreshView mPullToRefreshView;
    private int pageSize = 10;//每页多少条数据
    private int pageIndex = 1;//第几页
    private int totalCount = 0;//总的数据条数 有些接口会返回

    @BindView(R.id.tv_home_rental)
    TextView tv_home_rental;//租房
    @BindView(R.id.tv_home_recuit)
    TextView tv_home_recuit;//招聘
    @BindView(R.id.tv_home_read)
    TextView tv_home_read;//阅读
    @BindView(R.id.tv_home_jingxuan)
    TextView tv_home_jingxuan;//精选
    @BindView(R.id.tv_home_mingyan)
    TextView tv_home_mingyan;//名言警句
    @BindView(R.id.tv_home_ittool)
    TextView tv_home_ittool;//it工具
    @BindView(R.id.tv_home_websit)
    TextView tv_home_websit;//推荐网站
    @BindView(R.id.tv_home_blacknum)
    TextView tv_home_blacknum;//黑名单
    @BindView(R.id.tv_home_more)
    TextView tv_home_more;//更多

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {

//        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "iconfont.ttf");
//        TextView textview = (TextView)findViewById(R.id.like);
//        tv_test.setTypeface(iconfont);
//        tv_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mPresenter.loadData();
//                ActivityUtil.getInstance().openActivity(getActivity(), TestRxBusActivity.class);
//            }
//        });
        initViewListener();
        initRepresh();
        initBannerListener();
        initBannerData();
    }

    private void initViewListener() {
        tv_home_rental.setOnClickListener(this);
        tv_home_recuit.setOnClickListener(this);
        tv_home_read.setOnClickListener(this);
        tv_home_jingxuan.setOnClickListener(this);
        tv_home_mingyan.setOnClickListener(this);
        tv_home_ittool.setOnClickListener(this);
        tv_home_websit.setOnClickListener(this);
        tv_home_blacknum.setOnClickListener(this);
        tv_home_more.setOnClickListener(this);
    }

    private void initBannerData() {
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //事实上  这个数据 应该在presenter 里面获取 临时征用了
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
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
                                .defaultImageResId(R.mipmap.splash_back4)
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
                ToastUtil.Success("轮播图 点击位置 :" + i);
            }
        });

        // 监听下载进度
        RxUpdateUtil.getDownloadProgressObservable()
                .subscribe(new Consumer<DownloadProgress>() {
                    @Override
                    public void accept(@NonNull DownloadProgress downloadProgress) throws Exception {
                        Logger.i("下载进度:" + downloadProgress.getTotal() + "==" + downloadProgress.getProgress());
                    }
                });

    }

    /**
     * 下拉刷新
     */
    private void initRepresh() {
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mPullToRefreshView .postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Logger.i("刷新数据");
//                        mPullToRefreshView .setRefreshing(false);
//                    }
//                },3000);
                mPresenter.loadData(pageSize, pageIndex);
            }
        });
    }


    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }


    @Override
    public void showContent(String msg) {
        ToastUtil.Success(msg);
        mPullToRefreshView.setRefreshing(false);
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

    //临时变量
    private String mNewVersion = "1.0.1";
    private String mApkUrl = "http://7xk9dj.com1.z0.glb.clouddn.com/BGAUpdateSample_v1.0.1_debug.apk";


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_rental:
                ActivityUtil.getInstance().openActivity(getActivity(), RentalActivity.class);
                break;
            case R.id.tv_home_recuit:
                ActivityUtil.getInstance().openActivity(getActivity(), RecuitActivity.class);
                break;
            case R.id.tv_home_read:
                ActivityUtil.getInstance().openActivity(getActivity(), ReadActivity.class);
                break;
            case R.id.tv_home_jingxuan:
//                ToastUtil.Success(tv_home_jingxuan.getText().toString());
                Bundle bundle = new Bundle();
//                bundle.putString("url","http://static10.photo.sina.com.cn/middle/5a3ab1b1x9961016a8699&690");
//                ActivityUtil.getInstance().openActivity(getActivity(), PhotoViewActivity.class,bundle);

                ArrayList<String> urls = new ArrayList<>();
                urls.add("http://inews.gtimg.com/newsapp_bt/0/1834185779/641");
                urls.add("http://inews.gtimg.com/newsapp_bt/0/1834185236/641");
                urls.add("http://inews.gtimg.com/newsapp_bt/0/1834186365/641");
                urls.add("http://inews.gtimg.com/newsapp_bt/0/1834187785/641");

                bundle.putStringArrayList("urls", urls);

//                ActivityUtil.getInstance().openActivity(getActivity(), PhotoViewPagerActivity.class,bundle);
                ActivityUtil.getInstance().openActivity(getActivity(), JingXuanActivity.class);

                break;
            case R.id.tv_home_mingyan:
                ActivityUtil.getInstance().openActivity(getActivity(), MingYanActivity.class);

                break;
            case R.id.tv_home_ittool:
                ActivityUtil.getInstance().openActivity(getActivity(), ItToolActivity.class);

                break;
            case R.id.tv_home_websit:
//                ActivityUtil.getInstance().openActivity(getActivity(), LoginActivity.class);
                ActivityUtil.getInstance().openActivity(getActivity(), WebsitActivity.class);
                break;
            case R.id.tv_home_blacknum:
                ToastUtil.Success(tv_home_blacknum.getText().toString());
//                downloadApkFile();
                ActivityUtil.getInstance().openActivity(getActivity(), BlackNumActivity.class);
                break;
            case R.id.tv_home_more:
                //暂无
                ToastUtil.Success(tv_home_more.getText().toString());
                break;

        }
    }

    public void downloadApkFile() {
//        NormalDialog normalDialog = new NormalDialog(mActivity);
//        normalDialog.show();

        ActionSheetDialog sheetDialog = new ActionSheetDialog(mActivity, new String[]{"相册", "相机"}, mRootView);
        sheetDialog.setTitle("修改头像");
        sheetDialog.show();
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        // 如果新版 apk 文件已经下载过了，直接 return，此时不需要开发者调用安装 apk 文件的方法，在 isApkFileDownloaded 里已经调用了安装」
        // TODO: 2017/7/28  测试进度  临时注释
//        if (RxUpdateUtil.isApkDownloaded(mNewVersion)) {
//            Logger.i("不下载,取缓存");
//            return;
//        }
        // 下载新版 apk 文件
//        RxUpdateUtil.downloadApkFile(mApkUrl, mNewVersion)
//                .subscribe(new Observer<File>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull File file) {
//                        if (file != null) {
//                            RxUpdateUtil.installApp(file);
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

}
