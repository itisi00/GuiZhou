package com.itisi.guizhou.mvp.ui.fadeback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itisi.guizhou.R;
import com.itisi.guizhou.app.Constants;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.adapter.SelectedImgAdapter;
import com.itisi.guizhou.mvp.model.bean.MeiZiBean;
import com.itisi.guizhou.mvp.model.bean.SelectedImgBean;
import com.itisi.guizhou.utils.ToastUtil;
import com.itisi.guizhou.utils.rxbus.annotation.UseRxBus;
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
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;

/**
 * **********************
 * 功 能:建议反馈
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/1 15:20
 * 修改人:itisi
 * 修改时间: 2017/8/1 15:20
 * 修改内容:itisi
 * *********************
 */
@UseRxBus
public class FadebackActivity extends RootActivity<FadebackPresenter>
        implements FadebackContract.View
        , BaseQuickAdapter.OnItemChildClickListener
        , BaseQuickAdapter.OnItemChildLongClickListener{

    private int maxPhotoCount =9;//最多能选择的图片数量
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    SelectedImgAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fadeback;
    }

    @Override
    protected void initEventAndData() {
        initView();
        initViewListener();
        initAdapter();
        setToolbarMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.Success("提交成功");
            }
        });
        loadData();
    }

    private void loadData() {
//        SelectedImgBean imgBean = new SelectedImgBean();
//        imgBean.setOriginalPath("http://v1.qzone.cc/avatar/201409/24/19/58/5422b1ff86ed0232.jpg%21200x200.jpg");
//        imgBean.setThumbPath("http://v1.qzone.cc/avatar/201409/24/19/58/5422b1ff86ed0232.jpg%21200x200.jpg");
//        mAdapter.addData(imgBean);
//        mAdapter.addData(imgBean);
//        mAdapter.addData(imgBean);
//        mAdapter.addData(imgBean);

        SelectedImgBean imgBean1 = new SelectedImgBean();
        imgBean1.setThumbPath("-1");
        imgBean1.setOriginalPath("-1");
        mAdapter.addData(imgBean1);
    }

    private void initView() {
        setPath();
        initGalleryListener();
    }


    private void initViewListener() {

    }

    private void initAdapter() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        layoutManager.setItemPrefetchEnabled(false);
        mAdapter = new SelectedImgAdapter(R.layout.item_selected_img);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
//        裁剪图片的回调
        RxGalleryListener
                .getInstance()
                .setRadioImageCheckedListener(
                        new IRadioImageCheckedListener() {
                            @Override
                            public void cropAfter(Object t) {
//                                Toast.makeText(getBaseContext(), "itisi"+t.toString(), Toast.LENGTH_SHORT).show();
                                Logger.i("裁剪后的图片:" + t.toString());
                            }

                            @Override
                            public boolean isActivityFinish() {
                                return false;
                            }
                        });

    }


    /**
     * 打开相册 选择照片
     */
    private void openAlbum() {
        RxGalleryFinal
                .with(FadebackActivity.this)
                .image()
                .multiple()
                .maxSize(maxPhotoCount)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        int size = imageMultipleResultEvent.getResult().size();
                        maxPhotoCount = maxPhotoCount -size;
                        List<SelectedImgBean>tempList=new ArrayList<>();
                        SelectedImgBean temp;
                        for (int i = 0; i < size; i++) {
                            temp=new SelectedImgBean();
                            temp.setOriginalPath(imageMultipleResultEvent.getResult().get(i).getOriginalPath());
                            temp.setThumbPath(imageMultipleResultEvent.getResult().get(i).getThumbnailSmallPath());
                            Logger.i("original:"+temp.getOriginalPath());
                            Logger.i("thumb:"+temp.getThumbPath());
                            tempList.add(temp);
                        }
                        mAdapter.addData(tempList);

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                })
                .openGallery();

    }

    @Override
    protected String setToolbarTvTitle() {
        return "建议反馈";
    }

    @Override
    protected String setToolbarMoreTxt() {
        return "提交";
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SelectedImgBean bean = mAdapter.getItem(position);
        if (bean.getOriginalPath().equals("-1")){
            openAlbum();
        }else {
            ToastUtil.Info("可能要做删除效果");
        }

    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.Info("long");
        return true;
    }
}
