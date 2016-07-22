package com.bignerdranch.android.criminalintent.fragment;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.criminalintent.util.PictureUtil;

/**
 * Created by joseph on 2016/7/22.
 */
public class ImageFragment extends DialogFragment {
    private static final String TAG = "ImageFragment";
    private static final String EXTRA_CRIME_PHOTO = "com.bignerdranch.android.criminalintent.fragment.ImageFragment.EXTRA_CRIME_PHOTO";
    private ImageView mImageView;

    public static ImageFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString(EXTRA_CRIME_PHOTO, path);

        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String path = args.getString(EXTRA_CRIME_PHOTO);
        BitmapDrawable bitmap = PictureUtil.getScaledBitmap(getActivity(), path);
        mImageView = new ImageView(getActivity());
        mImageView.setImageDrawable(bitmap);

        return mImageView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PictureUtil.cleanImageView(mImageView);
    }
}
