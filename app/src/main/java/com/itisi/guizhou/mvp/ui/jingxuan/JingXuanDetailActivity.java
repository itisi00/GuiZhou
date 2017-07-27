package com.itisi.guizhou.mvp.ui.jingxuan;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

@UseRxBus
public class JingXuanDetailActivity extends RootActivity<JingXuanPresenter>
        implements JingXuanContract.View {


    @BindView(R.id.webview)
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jing_xuan_detail;
    }

    @Override
    protected void initEventAndData() {

        initWebSettings();
        mWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MjM5MDI5OTkyOA==&idx=1&mid=222639330&sn=b7b0425cde58aea059d9376da0d40444&qb_mtt_show_type=1");
    }

    private void initWebSettings() {
        // String url_str="https://juejin.im/post/591e734d2f301e006bea5243?utm_source=gold_browser_extension";
        // mWebView.loadUrl(url_str);

        WebSettings settings = mWebView.getSettings();
//        settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
//        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);// 支持js功能

        //控制webView不要出现横向滚动条
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                Logger.i("网页开始加载了");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                Logger.i("网页加载结束");
                int contentHeight = mWebView.getContentHeight(); //有值 应该是px
//                int measuredHeight = mWebView.getMeasuredHeight();
//                int height = mWebView.getHeight(); // 为0
//                Logger.i("contentHeight:" + contentHeight);
//                Logger.i("measuredHeight:" + measuredHeight);
//                Logger.i("height:" + height);

//                int dp = ConvertUtils.px2dp(contentHeight, NewsDetailActivity.this);
//                Logger.i("dp:" + dp);

                int contentHeight1 = view.getContentHeight();
//                Logger.i("contentHeight1:" + contentHeight1);

                ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
//                layoutParams.height=contentHeight*2;
//                layoutParams.height=contentHeight;
//                mWebView.setLayoutParams(layoutParams);
//                Logger.i(height+"");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
//                Logger.i("跳转链接:" + url);
//                view.loadUrl(url); // 在跳转链接时强制在当前webview中加载
                //此方法还有其他应用场景, 比如写一个超链接<a href="tel:110">联系我们</a>,
                // 当点击该超链接时,可以在此方法中获取链接地址tel:110,
                // 解析该地址,获取电话号码, 然后跳转到本地打电话页面, 而不是加载网页,
                // 从而实现了webView和本地代码的交互
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                Logger.i("进度:" + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                Logger.i("网页标题:" + title);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                Logger.i("进度:" + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Logger.i("网页标题:" + title);
            }
        });

    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
    }
}
