package com.itisi.guizhou.mvp.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itisi.guizhou.R;
import com.jaeger.library.StatusBarUtil;

public class TestToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        StatusBarUtil.setTranslucent(this,0);
    }
}
