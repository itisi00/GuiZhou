package com.itisi.guizhou.mvp.ui.album.detail;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itisi.guizhou.R;
import com.itisi.guizhou.app.Constants;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.model.bean.SelectedImgBean;
import com.itisi.guizhou.utils.ActivityUtil;
import com.itisi.guizhou.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;

public class AlbumAddActivity extends RootActivity<AlbumDetailPresenter>
        implements AlbumDetailContract.View
        , View.OnClickListener {

    @BindView(R.id.et_speak)
    EditText et_speak;
    @BindView(R.id.tv_select_album)
    TextView tv_select_album;
    @BindView(R.id.tv_select_location)
    TextView tv_select_address;
    @BindView(R.id.iv_select_img)
    ImageView iv_select_img;
    private int maxPhotoCount=100;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_album_add;
    }

    @Override
    protected void initEventAndData() {
        setPath();
        initGalleryListener();

        tv_select_album.setOnClickListener(this);
        tv_select_address.setOnClickListener(this);
        iv_select_img.setOnClickListener(this);

        setToolbarMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.Success("上传成功");
            }
        });
    }

    @Override
    protected String setToolbarTvTitle() {
        return "传相片";
    }

    @Override
    protected String setToolbarMoreTxt() {
        return "上传";
    }

    /**
     * 设置 照片路径 和 裁剪路径
     * //裁剪会自动生成路径；也可以手动设置裁剪的路径；
     */
    private void setPath() {
        RxGalleryFinalApi.setImgSaveRxSDCard(Constants.PATH_GALLERY);
        RxGalleryFinalApi.setImgSaveRxCropSDCard(Constants.PATH_GALLERY_CROP);
    }

    /**
     * 初始化 rxgallery 的相关事件
     */
    private void initGalleryListener() {
//        多选事件的回调
        RxGalleryListener
                .getInstance()
                .setMultiImageCheckedListener(
                        new IMultiImageCheckedListener() {
                            @Override
                            public void selectedImg(Object t, boolean isChecked) {
//                                Toast.makeText(getBaseContext(), isChecked ? "itisi选中" : "itisi取消选中", Toast.LENGTH_SHORT).show();
                                //这个主要点击或者按到就会触发，所以不建议在这里进行Toast
                            }

                            @Override
                            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                                ToastUtil.Info("你最多只能选择" + maxSize + "张图片");
                            }
                        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_album:
                // TODO: 2017/8/4                  startActivityForResult();
                ActivityUtil.getInstance().openActivity(mActivity, AlbumNamesActivity.class);
                break;
            case R.id.tv_select_location:

                break;
            case R.id.iv_select_img:
                openAlbum();
                break;
        }

    }

    /**
     * 打开相册 选择照片
     */
    private void openAlbum() {
        RxGalleryFinal
                .with(mActivity)
                .image()
                .multiple()
                .maxSize(maxPhotoCount)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        int size = imageMultipleResultEvent.getResult().size();
//                        maxPhotoCount = maxPhotoCount - size;
//                        if (maxPhotoCount == 0) {
//                            mAdapter.remove(0);//删除添加按钮那个图标
//                        }
                        List<SelectedImgBean> tempList = new ArrayList<>();
                        SelectedImgBean temp;
                        for (int i = 0; i < size; i++) {
                            temp = new SelectedImgBean();
                            temp.setOriginalPath(imageMultipleResultEvent.getResult().get(i).getOriginalPath());
                            temp.setThumbPath(imageMultipleResultEvent.getResult().get(i).getThumbnailSmallPath());
                            Logger.i("original:" + temp.getOriginalPath()+"==thumb:" + temp.getThumbPath());
                            tempList.add(temp);
                        }
//                        List<SelectedImgBean> data = mAdapter.getData();
//                        data.addAll(tempList);
//                        mAdapter.setNewData(data);

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                })
                .openGallery();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
