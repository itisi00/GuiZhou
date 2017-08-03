package com.itisi.guizhou.mvp.ui.user.personal;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.animation.ZoomEnter.ZoomInBottomEnter;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.itisi.guizhou.R;
import com.itisi.guizhou.app.Constants;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
import com.itisi.guizhou.widget.dialog.SignleInputDialog;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;
import cn.finalteam.rxgalleryfinal.utils.MediaScanner;

/**
 * **********************
 * 功 能:个人信息
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/2 14:05
 * 修改人:itisi
 * 修改时间: 2017/8/2 14:05
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class PersonalActivity extends RootActivity<PersonalPresenter>
        implements PersonalContract.View
        , View.OnClickListener {

    @BindView(R.id.ll_parent)
    LinearLayout ll_parent;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_autograph)
    TextView tv_autograph;

    private String currentImgPath = "";//拍照 或者 从相册选择的图片的路径 裁剪之后的
    private boolean isFromCamera = true;//当前路径是否来自于拍照之后的裁剪

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initEventAndData() {

        setPath();
        initGalleryListener();

        iv_header.setOnClickListener(this);
        tv_nick.setOnClickListener(this);
        tv_gender.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        tv_autograph.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                final String[] menus = getResources().getStringArray(R.array.menu_normal);
                final ActionSheetDialog sheetDialog = new ActionSheetDialog(mActivity, menus, ll_parent);
                sheetDialog.isTitleShow(false)
                        .showAnim(new ZoomInBottomEnter())
                        .itemTextColor(getResources().getColor(R.color.colorPrimary))
                        .show();
                sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {//相册
                            openAlbum();
                            isFromCamera=false;
                        } else {//相机
                            openCamera();
                            isFromCamera=true;
                        }
                        sheetDialog.dismiss();
                    }
                });

                break;
            case R.id.tv_nick:
                final SignleInputDialog dialog=new SignleInputDialog(mActivity);

//                Window win = getWindow();
//                WindowManager.LayoutParams params = win.getAttributes();
//                params.y = params.y - 50;
//                win.setSoftInputMode(params.SOFT_INPUT_ADJUST_RESIZE);
//                win.setGravity(Gravity.BOTTOM);
//
//                win.setAttributes(params);

                dialog.show();
//                dialog.dismissAnim(new FlipVerticalExit());
                dialog.setOKClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        ToastUtil.Info(dialog.getInputContent());
                    }
                });

                break;
            case R.id.tv_gender:
                break;
            case R.id.tv_phone:
                break;
            case R.id.tv_address:
                break;
            case R.id.tv_autograph:
                break;
        }
    }

    /**
     * 设置 照片路径 和 裁剪路径
     * //裁剪会自动生成路径；也可以手动设置裁剪的路径；
     */
    private void setPath() {
        RxGalleryFinalApi.setImgSaveRxSDCard(Constants.PATH_GALLERY);
        //裁剪路径貌似无效
        RxGalleryFinalApi.setImgSaveRxCropSDCard(Constants.PATH_GALLERY_CROP);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFromCamera) {
            //选择调用：裁剪图片的回调  从相机到裁剪
            RxGalleryFinalApi.cropActivityForResult(this, new MediaScanner.ScanCallback() {
                @Override
                public void onScanCompleted(final String[] images) {
                    currentImgPath = images[0];

                    Logger.i(String.format("裁剪图片成功,图片裁剪后存储路径xxyy:%s", currentImgPath));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Glide.with(mActivity).load(new File(currentImgPath)).into(iv_header);
                        }
                    });
                }
            });
        }

    }

    /**
     * 初始化 rxgallery 的相关事件
     */
    private void initGalleryListener() {
        //        裁剪图片的回调  从相册到裁剪
        RxGalleryListener
                .getInstance()
                .setRadioImageCheckedListener(
                        new IRadioImageCheckedListener() {
                            @Override
                            public void cropAfter(Object t) {
                                Logger.i("裁剪后的图片:" + t.toString());
                                currentImgPath = t.toString();
                                Glide.with(mActivity).load(new File(currentImgPath)).into(iv_header);
                            }
                            @Override
                            public boolean isActivityFinish() {
                                //"返回false不关闭界面，返回true则为关闭"
                                return true;
                            }
                        });
    }

    /**
     * 打开相册 选择照片
     */
    private void openAlbum() {
        //打开相册
        RxGalleryFinalApi.getInstance(mActivity)
                .openGalleryRadioImgDefault(
                        new RxBusResultSubscriber<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                                Logger.i("只要选择图片就会触发");
                            }
                        });
    }

    /**
     * 打开相机-拍照
     */
    private void openCamera() {
        //裁剪路径貌似无效
        RxGalleryFinalApi.setImgSaveRxCropSDCard(Constants.PATH_GALLERY_CROP);
        RxGalleryFinalApi.openZKCamera(mActivity);
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

    /**
     * 拍照后的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RxGalleryFinalApi.TAKE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //刷新相册数据库
            RxGalleryFinalApi.openZKCameraForResult(mActivity, new MediaScanner.ScanCallback() {
                @Override
                public void onScanCompleted(String[] strings) {
//                    Logger.i(String.format("拍照成功,图片存储路径:%s", strings[0]));
                    RxGalleryFinalApi.cropScannerForResult(mActivity, RxGalleryFinalApi.getModelPath(), strings[0]);
                    //调用裁剪.RxGalleryFinalApi.getModelPath()为默认的输出路径
                }
            });
        } else {
            Logger.i("失敗或者取消");
        }
    }
}
