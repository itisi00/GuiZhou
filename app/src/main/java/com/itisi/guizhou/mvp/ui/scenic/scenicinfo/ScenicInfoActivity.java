package com.itisi.guizhou.mvp.ui.scenic.scenicinfo;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.ui.scenic.ScenicContract;
import com.itisi.guizhou.mvp.ui.scenic.ScenicPresenter;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

public class ScenicInfoActivity extends RootActivity<ScenicPresenter>
        implements ScenicContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_scenic_info;
    }

    @Override
    protected void initEventAndData() {
        StatusBarUtil.setTranslucent(this, 0);
//        setToolbarBackground(R.color.colorTransparent);
    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    protected boolean isToolbarTransparent() {
        return true;
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
}
