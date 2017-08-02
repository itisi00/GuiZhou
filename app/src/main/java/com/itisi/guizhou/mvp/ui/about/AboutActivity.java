package com.itisi.guizhou.mvp.ui.about;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.ZoomEnter.ZoomInBottomEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.SystemUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

import butterknife.BindView;

/**
 * **********************
 * 功 能:关于我们
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/1 15:16
 * 修改人:itisi
 * 修改时间: 2017/8/1 15:16
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class AboutActivity extends RootActivity<AboutPresenter>
        implements AboutContract.View
        , View.OnClickListener {

    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    @BindView(R.id.tv_aboutus)
    TextView tv_aboutus;
    @BindView(R.id.tv_call_us)
    TextView tv_call_us;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initEventAndData() {
        initView();
        initAdapter();
        initViewListener();
    }

    private void initView() {

    }

    private void initAdapter() {

    }

    private void initViewListener() {
        tv_aboutus.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
        tv_call_us.setOnClickListener(this);
        tv_version.setOnClickListener(this);
        iv_logo.setOnClickListener(this);
    }

    @Override
    protected String setToolbarTvTitle() {
        return "关于我们";
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


    private void loadData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_agreement:
                ActivityUtil.getInstance().openActivity(AboutActivity.this, AgreementActivity.class);
                break;
            case R.id.tv_aboutus:
                ActivityUtil.getInstance().openActivity(AboutActivity.this, AboutUsActivity.class);
                break;
            case R.id.tv_call_us:
                final String number = tv_call_us.getText().toString();
                final NormalDialog dialog = new NormalDialog(mActivity);

//                dialog.title("");
                dialog.isTitleShow(false)
                        .content("需要拨打电话 " + number + "吗")
                        .btnText("取消", "确定")
                        .btnTextColor(getResources().getColor(R.color.colorGray), getResources().getColor(R.color.colorPrimary))//
                        .showAnim(new ZoomInBottomEnter())
                        .show();
                dialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        SystemUtil.callPhone(mActivity, number);
                    }
                });
                break;
        }
    }
}
