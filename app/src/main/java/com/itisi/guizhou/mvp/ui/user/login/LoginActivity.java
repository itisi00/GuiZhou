package com.itisi.guizhou.mvp.ui.user.login;

import android.content.Context;
import android.os.SystemClock;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.rainbow.RainbowTextView;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.ui.user.register.RegistActivity;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.SceneAnim;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.jaeger.library.StatusBarUtil;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.SweetSheet;
import com.mingle.sweetpick.ViewPagerDelegate;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@UseRxBus
public class LoginActivity extends RootActivity<LoginPresenter> implements LoginContract.View
        , View.OnClickListener {
    @BindView(R.id.iv_login_header)
    ImageView iv_login_header;//头像 登陆过是用户头像,第一次是默认都行
    @BindView(R.id.tv_login_nick)
    RainbowTextView tv_login_nick;//用户昵称
    @BindView(R.id.til_name_wrapper)
    TextInputLayout til_name_wrapper;//账号 父布局
    @BindView(R.id.til_password_wrapper)
    TextInputLayout til_password_wrapper;//密码 父布局
    @BindView(R.id.btn_login)
    Button btn_login;//登陆按钮
    @BindView(R.id.tv_login_forget_password)
    TextView tv_login_forget_password;//忘记密码
    @BindView(R.id.tv_login_regist)
    TextView tv_login_regist;//注册
    @BindView(R.id.tv_login_other_account)
    TextView tv_login_other_account;//第三方合作账号
    @BindView(R.id.tie_name)
    TextInputEditText tie_name;//账号输入框 用来监听焦点事件
    @BindView(R.id.tie_password)
    TextInputEditText tie_password;//密码输入框 用来监听焦点事件
    @BindView(R.id.rl_login_account)
    RelativeLayout rl_login_account;//最外层父布局 为 底部 sheet 加上的

    SweetSheet mSweetSheet;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        StatusBarUtil.setTranslucent(this, 0);//不加0 是半透明效果
        initViewListener();
        initTextOnBlurListener();

    }


    private void initViewListener() {
        iv_login_header.setOnClickListener(this);
        tv_login_nick.setOnClickListener(this);
        til_name_wrapper.setOnClickListener(this);
        til_password_wrapper.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_login_forget_password.setOnClickListener(this);
        tv_login_regist.setOnClickListener(this);
        tv_login_other_account.setOnClickListener(this);

        openSoftKeyBoard();
    }

    private void openSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        SystemClock.sleep(200);
        tie_name.setFocusable(true);
        tie_name.setFocusableInTouchMode(true);
        tie_name.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_nick:
                //这么设置没用
//                tv_login_nick.setColors(R.color.colorAccent,R.color.colorPrimary,R.color.colorWhite,R.color.colorBlack);
                break;
            case R.id.btn_login:
                virfyUserInput();
                break;
            case R.id.tv_login_forget_password:
                ToastUtil.Success(tv_login_forget_password.getText().toString());
                break;
            case R.id.tv_login_regist:
                ActivityUtil.getInstance().openActivity(LoginActivity.this, RegistActivity.class, SceneAnim.AnimType.TOP_IN);
                break;
            case R.id.tv_login_other_account:
//                openOtherAccountSheet();
                setupCustomView();
                break;
            case R.id.tv_weixin:
                ToastUtil.Success("微信登陆");
                mSweetSheet.toggle();
                break;
            case R.id.tv_qq:
                ToastUtil.Success("QQ登陆");
                mSweetSheet.toggle();
                break;
            case R.id.tv_weibo:
                ToastUtil.Success("微博登陆");
                mSweetSheet.toggle();
                break;
        }
    }


    /**
     * 打开地方合作账号的 sheet
     * 自定义布局
     */
    private void openOtherAccountSheet() {
        if (mSweetSheet == null) {
            mSweetSheet = new SweetSheet(rl_login_account);
            List<MenuEntity> list = new ArrayList<>();
            MenuEntity menuEntity1 = new MenuEntity();
            menuEntity1.iconId = R.mipmap.test_menu_love_white;
            menuEntity1.title = "QQ";
            list.add(menuEntity1);

            MenuEntity menuEntity2 = new MenuEntity();
            menuEntity2.iconId = R.mipmap.test_menu_music_white;
            menuEntity2.title = "微信";
            list.add(menuEntity2);

            MenuEntity menuEntity3 = new MenuEntity();
            menuEntity3.iconId = R.mipmap.test_menu_chat_white;
            menuEntity3.title = "微博";
            list.add(menuEntity3);

            //设置数据源 (数据源支持设置 list 数组,也支持从menu 资源中获取)
            mSweetSheet.setMenuList(list);

            //根据设置不同的 Delegate 来显示不同的风格.
            ViewPagerDelegate viewPagerDelegate = new ViewPagerDelegate();
            viewPagerDelegate.setContentHeight(200);
            mSweetSheet.setDelegate(viewPagerDelegate);
            //根据设置不同Effect来设置背景效果:BlurEffect 模糊效果.DimEffect 变暗效果,NoneEffect 没有效果.
            mSweetSheet.setBackgroundEffect(new BlurEffect(50));//背景模糊
            mSweetSheet.setBackgroundEffect(new DimEffect(0.5F));//背景透明度
            //设置菜单点击事件
            mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
                @Override
                public boolean onItemClick(int position, MenuEntity menuEntity1) {

                    //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                    Toast.makeText(LoginActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
        mSweetSheet.show();
    }

    private void setupCustomView() {
        if (mSweetSheet == null) {

            mSweetSheet = new SweetSheet(rl_login_account);
            //根据设置不同Effect来设置背景效果:BlurEffect 模糊效果.DimEffect 变暗效果,NoneEffect 没有效果.
            mSweetSheet.setBackgroundEffect(new BlurEffect(50));//背景模糊
            mSweetSheet.setBackgroundEffect(new DimEffect(0.5F));//背景透明度
            CustomDelegate customDelegate = new CustomDelegate(true,
                    CustomDelegate.AnimationType.DuangLayoutAnimation);//DuangAnimation AlphaAnimation
            View view = LayoutInflater.from(this).inflate(R.layout.custom_other_account, null, false);
            customDelegate.setCustomView(view);
            customDelegate.setContentHeight(300);//自定义高度
            mSweetSheet.setDelegate(customDelegate);

            TextView tv_weixin = view.findViewById(R.id.tv_weixin);
            TextView tv_qq = view.findViewById(R.id.tv_qq);
            TextView tv_weibo = view.findViewById(R.id.tv_weibo);

            tv_weixin.setOnClickListener(this);
            tv_qq.setOnClickListener(this);
            tv_weibo.setOnClickListener(this);

        }

        mSweetSheet.show();

    }

    /**
     * 验证输入的合法性
     */
    private void virfyUserInput() {
        String username = til_name_wrapper.getEditText().getText().toString();
        String userpwd = til_password_wrapper.getEditText().getText().toString();

        if (username.equals("itisi")) {//验证合法性
            til_name_wrapper.setErrorEnabled(true);
            til_name_wrapper.setError("用户名不合法");
        } else {
            til_name_wrapper.setErrorEnabled(false);
//            til_name_wrapper.setError("");
        }
    }

    /**
     * 输入框 失去焦点事件
     */
    private void initTextOnBlurListener() {
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Logger.i(view.getId() + "=" + b);
            }
        };
        tie_name.setOnFocusChangeListener(onFocusChangeListener);
        tie_password.setOnFocusChangeListener(onFocusChangeListener);
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
    public void onBackPressed() {
        if (mSweetSheet!=null&&mSweetSheet.isShow()) {
            if (mSweetSheet.isShow()) {
                mSweetSheet.dismiss();
            }
        } else {
            mSweetSheet = null;
            super.onBackPressed();
        }
    }


}
