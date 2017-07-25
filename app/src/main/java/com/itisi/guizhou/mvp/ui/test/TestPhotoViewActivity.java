package com.itisi.guizhou.mvp.ui.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.itisi.guizhou.R;

import uk.co.senab.photoview.PhotoView;

public class TestPhotoViewActivity extends AppCompatActivity {

    PhotoView mPhotoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_photo_view);

        mPhotoView= (PhotoView) findViewById(R.id.photoview);
        Glide.with(this).load("http://static10.photo.sina.com.cn/middle/5a3ab1b1x9961016a8699&690").into(mPhotoView);
    }
}
