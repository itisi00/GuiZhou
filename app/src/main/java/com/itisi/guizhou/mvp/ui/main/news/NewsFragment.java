package com.itisi.guizhou.mvp.ui.main.news;


import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;

/**
 * 新闻列表界面
 */
public class NewsFragment  extends RootFragment<NewsPresenter> implements NewsContract.View   {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
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


}
