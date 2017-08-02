package com.itisi.guizhou.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.animation.ZoomEnter.ZoomInBottomEnter;
import com.flyco.dialog.widget.base.BaseDialog;
import com.itisi.guizhou.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/2 17:22
 * 修改人:itisi
 * 修改时间: 2017/8/2 17:22
 * 修改内容:itisi
 * *********************
 */

public class SignleInputDialog  extends BaseDialog<SignleInputDialog> {

    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    public SignleInputDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new ZoomInBottomEnter());
        View inflate = View.inflate(mContext, R.layout.dialog_content_signalinput, null);
        ButterKnife.bind(this, inflate);
        Window win = getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        win.setSoftInputMode(params.SOFT_INPUT_ADJUST_RESIZE);

        return inflate;
    }


    @Override
    public void setUiBeforShow() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    /**
     * 确定按钮的点击事件
     * @param listener
     */
    public void setOKClickListener(View.OnClickListener listener){
        tv_ok.setOnClickListener(listener);
    }

    /**
     * 获取输入框的内容
     * @return
     */
    public String getInputContent(){
        return et_content.getText().toString();
    }
}
