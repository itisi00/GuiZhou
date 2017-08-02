package com.itisi.guizhou.mvp.ui.setting;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flyco.animation.ZoomEnter.ZoomInBottomEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.ui.about.AboutActivity;
import com.itisi.guizhou.mvp.ui.fadeback.FadebackActivity;
import com.itisi.guizhou.mvp.ui.user.personal.PersonalActivity;
import com.itisi.guizhou.mvp.ui.user.safe.SafeActivity;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;

import java.util.List;

import butterknife.BindView;

/**
 * **********************
 * 功 能:设置
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/1 15:22
 * 修改人:itisi
 * 修改时间: 2017/8/1 15:22
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class SettingActivity extends RootActivity<SettingPresenter>
        implements SettingContract.View
        , View.OnClickListener {

    @BindView(R.id.tv_personal)
    TextView tv_personal;
    @BindView(R.id.tv_safe)
    TextView tv_safe;
    @BindView(R.id.tv_checkupdate)
    TextView tv_checkupdate;
    @BindView(R.id.tv_cache)
    TextView tv_cache;
    @BindView(R.id.tv_fadeback)
    TextView tv_fadeback;
    @BindView(R.id.tv_aboutus)
    TextView tv_aboutus;
    @BindView(R.id.btn_logout)
    Button btn_logout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
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
        tv_personal.setOnClickListener(this);
        tv_safe.setOnClickListener(this);
        tv_checkupdate.setOnClickListener(this);
        tv_cache.setOnClickListener(this);
        tv_fadeback.setOnClickListener(this);
        tv_aboutus.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    @Override
    protected String setToolbarTvTitle() {
        return "设置";
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_personal:
                ActivityUtil.getInstance().openActivity(mActivity, PersonalActivity.class);

                break;
            case R.id.tv_safe:
                ActivityUtil.getInstance().openActivity(mActivity, SafeActivity.class);

                break;
            case R.id.tv_checkupdate:
                ToastUtil.Info("已是最新版本");
                break;
            case R.id.tv_cache:
                final NormalDialog dialog = new NormalDialog(mActivity);
//                dialog.title("");
                dialog.isTitleShow(false)
                        .content("缓存的图片等信息将会被清理")
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
                        ToastUtil.Info("缓存清理成功");
                        tv_cache.setText("0M");
                    }
                });

                break;
            case R.id.tv_fadeback:
                ActivityUtil.getInstance().openActivity(mActivity, FadebackActivity.class);
                break;
            case R.id.tv_aboutus:
                ActivityUtil.getInstance().openActivity(mActivity, AboutActivity.class);
                break;

            case R.id.btn_logout:
                ToastUtil.Info("退出");
                break;
        }

    }
}
