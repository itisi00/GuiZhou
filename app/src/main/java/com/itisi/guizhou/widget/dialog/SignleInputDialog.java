package com.itisi.guizhou.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.itisi.guizhou.R;
import com.itisi.guizhou.app.App;

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

public class SignleInputDialog extends Dialog  //BaseDialog<SignleInputDialog>
{

    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_ok)
    TextView tv_ok;

    public SignleInputDialog(Context context,int inputType,String oldValue) {
        super(context);
        setContentView(R.layout.dialog_content_signalinput);
        ButterKnife.bind(this);
        Window win = getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        //设置背景颜色,只有设置了这个属性,宽度才能全屏MATCH_PARENT
        win.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.colorWhite)));//注意此处
        win.setGravity(Gravity.CENTER);//定位是基于这个的
        params.width = (int) (App.SCREEN_WIDTH*0.9);//这个属性需要配合透明背景颜色,才会真正的 MATCH_PARENT
        //位置还是把握不好
//        int oldHeight = 260;//params.height;
//        int anInt = SharedPreferencedUtils.getInt(Constants.SOFT_INPUT_HEIGHT, 0);
//        if (anInt>0){
//            params.y=App.SCREEN_HEIGHT-anInt-oldHeight;
//        }else {
//            params.y=App.SCREEN_HEIGHT/2-oldHeight;
//
//        }
        win.setAttributes(params);
        et_content.setInputType(inputType);
        et_content.setHint(oldValue);
//        et_content.setSelection(oldValue.length()); //值都没有 就不用移动光标
    }

    /**
     * 取消按钮的点击事件
     * @param listener
     */
    public void setCancelClickListener(View.OnClickListener listener) {
        tv_cancel.setOnClickListener(listener);
    }


    /**
     * 确定按钮的点击事件
     *
     * @param listener
     */
    public void setOKClickListener(View.OnClickListener listener) {
        tv_ok.setOnClickListener(listener);
    }

    /**
     * 获取输入框的内容
     *
     * @return
     */
    public String getInputContent() {
        return et_content.getText().toString();
    }
}
