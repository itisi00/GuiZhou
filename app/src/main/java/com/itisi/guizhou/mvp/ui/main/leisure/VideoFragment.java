package com.itisi.guizhou.mvp.ui.main.leisure;


import android.support.v4.app.Fragment;

import com.itisi.guizhou.R;
import com.itisi.guizhou.base.RootFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends RootFragment<MeiZiPresenter> {


    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void useNightMode(boolean isNight) {

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
        getFragmentComponent().inject(this);
    }


//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Field childFragmentManager = null;
//        try {
//            childFragmentManager = Fragment.class
//                    .getDeclaredField("mChildFragmentManager");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        childFragmentManager.setAccessible(true);
//        try {
//            childFragmentManager.set(this, null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//    }
}
