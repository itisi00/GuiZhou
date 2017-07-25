package com.itisi.guizhou.mvp.ui.user.register;

import android.graphics.Paint;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hanks.htextview.rainbow.RainbowTextView;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.jaeger.library.StatusBarUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

@UseRxBus
public class RegistActivity extends RootActivity<RegistPresenter> implements RegistContract.View
        , View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.tv_regist_wellcom)
    RainbowTextView tv_regist_wellcom;//欢迎语
    @BindView(R.id.tv_regist_agreement)
    TextView tv_regist_agreement;//用户注册协议
    @BindView(R.id.til_regist_phone_wrapper)
    TextInputLayout til_regist_phone_wrapper;//电话父布局
    @BindView(R.id.til_regist_validate_wrapper)
    TextInputLayout til_regist_validate_wrapper;//验证码父布局
    @BindView(R.id.tv_regist_getcode)
    TextView tv_regist_getcode;//获取验证码
    @BindView(R.id.cb_regist_agreement)
    CheckBox cb_regist_agreement;//同意复选框
    @BindView(R.id.btn_regist_next)
    Button btn_regist_next;//下一步按钮
    @BindView(R.id.tie_regist_validate)
    TextInputEditText tie_regist_validate;//电话号码 输入框 用来监听焦点事件
    @BindView(R.id.tie_regist_phone)
    TextInputEditText tie_regist_phone;//验证码 输入框 用来监听焦点事件
    private Timer mTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initEventAndData() {
        StatusBarUtil.setTranslucent(this, 0);//不加0 是半透明效果
        tv_regist_agreement.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

        initViewListener();
    }

    private void initViewListener() {
        tv_regist_agreement.setOnClickListener(this);
        tv_regist_getcode.setOnClickListener(this);
        cb_regist_agreement.setOnCheckedChangeListener(this);
        btn_regist_next.setOnClickListener(this);
        tv_regist_wellcom.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_regist_wellcom:
                break;
            case R.id.tv_regist_getcode:
                getValidateCode();
                break;
            case R.id.tv_regist_agreement:
                openAgreement();
                break;
            case R.id.btn_regist_next:
                ActivityUtil.getInstance().openActivity(RegistActivity.this, RegistConfirmActivity.class);
                break;
        }
    }

    private void openAgreement() {
        ToastUtil.Info("用户注册协议");
    }

    /**
     * 获取验证码的时候应用的
     */
    boolean isWaiting = false;

    /**
     * 获取验证码---手机验证码
     */
    private void getValidateCode() {
        // TODO: 2017/7/12 验证 手机号
        if (isWaiting) {
            ToastUtil.Info("操作太快,请稍后");
        } else {
            tv_regist_getcode.setTextColor(getResources().getColor(R.color.colorGray));
            isWaiting = true;
            mTimer = new Timer();
            final int[] temp = {10};//10s
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            temp[0]--;
                            tv_regist_getcode.setText(temp[0] + "秒后重新获取");
                            if (temp[0] == 0) {
                                mTimer.cancel();
                                isWaiting = false;
                                tv_regist_getcode.setTextColor(getResources().getColor(R.color.colorAccent));
                                tv_regist_getcode.setText("获取验证码");
                            }
                        }
                    });

                }
            };
            mTimer.schedule(timerTask, 1000, 1000);

        }
    }

    @Override
    public void testShowView(String smg) {

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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_regist_agreement:
                if (b) {
                    ToastUtil.Success("选中了");
                } else {
                    ToastUtil.Error("取消了");

                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
