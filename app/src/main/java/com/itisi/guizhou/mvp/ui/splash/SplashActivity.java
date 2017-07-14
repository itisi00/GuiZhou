package com.itisi.guizhou.mvp.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootActivity;
import com.itisi.guizhou.mvp.ui.main.MainActivity;
import com.jaeger.library.StatusBarUtil;

public class SplashActivity extends RootActivity<SplashPresenter> implements SplashContract.View {
    private ImageView img;
    private ImageView ImgMark;
    ViewPropertyAnimation.Animator animator=new ViewPropertyAnimation.Animator(){

        @Override
        public void animate(View view) {
            view.setAlpha(0f);
            ObjectAnimator objAnimator=ObjectAnimator.ofFloat(view,"alpha",0f,1f);
            objAnimator.setDuration(1500);
            objAnimator.start();
        }
    };

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        StatusBarUtil.setTranslucent(this, 0);//不加0 是半透明效果
//        getWindow().setBackgroundDrawable(null);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        img= (ImageView) findViewById(R.id.img_id);
//        ImgMark= (ImageView) findViewById(R.id.icon_mark);
//        ImgMark.post(new Runnable() {
//            @Override
//            public void run() {
//                Glide.with(SplashActivity.this).load(R.drawable.benbenla)
//                        .animate(animator).into(img);
//                startAnimat();
//            }
//        });
//    }

    @Override
    protected int getLayoutId() {
        StatusBarUtil.setTranslucent(this, 0);//不加0 是半透明效果
        getWindow().setBackgroundDrawable(null);
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        img= (ImageView) findViewById(R.id.img_id);
        ImgMark= (ImageView) findViewById(R.id.icon_mark);
        ImgMark.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(SplashActivity.this).load(R.drawable.benbenla)
                        .animate(animator).into(img);
                startAnimat();
            }
        });

    }

    private void startAnimat(){

        int imgHeight=ImgMark.getHeight()/5;
        int height=getWindowManager().getDefaultDisplay().getHeight();
        int curY=height/2 + imgHeight/2;
        int dy=(height-imgHeight)/2;
        AnimatorSet set=new AnimatorSet();
        ObjectAnimator animatorTranslate=ObjectAnimator.ofFloat(ImgMark,"translationY",0,dy);
        ObjectAnimator animatorScaleX=ObjectAnimator.ofFloat(ImgMark,"ScaleX",1f,0.2f);
        ObjectAnimator animatorScaleY=ObjectAnimator.ofFloat(ImgMark,"ScaleY",1f,0.2f);
        ObjectAnimator animatorAlpha=ObjectAnimator.ofFloat(ImgMark,"alpha",1f,0.5f);
        set.play(animatorTranslate)
                .with(animatorScaleX).with(animatorScaleY).with(animatorAlpha);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                ImgMark.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        SplashActivity.this.finish();
                    }
                },1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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
