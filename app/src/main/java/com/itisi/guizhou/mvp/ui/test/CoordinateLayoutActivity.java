package com.itisi.guizhou.mvp.ui.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.itisi.guizhou.R;

public class CoordinateLayoutActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_layout);

        imageView= (ImageView) findViewById(R.id.iv_test);
//        StatusBarUtil.setTranslucent(this);//不加0 是半透明效果
//        StatusBarUtil.setTranslucentForImageView(this, 0, imageView);

    }
}
