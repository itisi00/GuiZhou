package com.itisi.guizhou.mvp.ui.user.personal;

import android.view.View;
import android.widget.EditText;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

import butterknife.BindView;

/**
 * **********************
 * 功 能:修改签名
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/3 16:49
 * 修改人:itisi
 * 修改时间: 2017/8/3 16:49
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class AutographAddActivity extends RootActivity<PersonalPresenter>
        implements PersonalContract.View {

    @BindView(R.id.et_autograph)
    EditText et_autograph;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_autograph_add;
    }

    @Override
    protected void initEventAndData() {
        setToolbarMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.Info(et_autograph.getText().toString());
            }
        });
    }

    @Override
    protected String setToolbarTvTitle() {
        return "调整签名";
    }

    @Override
    protected String setToolbarMoreTxt() {
        return "保存";
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
}
