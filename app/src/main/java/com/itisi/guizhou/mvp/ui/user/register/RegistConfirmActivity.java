package com.itisi.guizhou.mvp.ui.user.register;

import android.view.View;
import android.widget.CompoundButton;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.jaeger.library.StatusBarUtil;

public class RegistConfirmActivity extends RootActivity<RegistPresenter> implements RegistContract.View
        , View.OnClickListener, CompoundButton.OnCheckedChangeListener{

        @Override
        protected int getLayoutId() {
            return R.layout.activity_regist_confirm;
        }

        @Override
        protected void initEventAndData() {
                StatusBarUtil.setTranslucent(this, 0);//不加0 是半透明效果

        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

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

}
