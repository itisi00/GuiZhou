package com.itisi.guizhou.mvp.ui.university.style;

import android.view.View;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

@UseRxBus
public class UniversityStyleAddActivity extends RootActivity<UniversityStylePresenter>
        implements UniversityStyleContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_university_style_add;
    }

    @Override
    protected void initEventAndData() {

        setToolbarMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.Success("假设保存成功");
            }
        });
    }

    @Override
    protected String setToolbarTvTitle() {
        return "贡献校园一角";
    }

    @Override
    protected String setToolbarMoreTxt() {
        return "发布";
    }

    @Override
    public void showContent(String msg) {

    }

    @Override
    public void loadSuccess(List<MeiZiBean> beanList) {
        //这个方法不做处理
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
