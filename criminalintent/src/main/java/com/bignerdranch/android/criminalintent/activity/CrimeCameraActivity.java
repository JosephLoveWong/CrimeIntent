package com.bignerdranch.android.criminalintent.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.bignerdranch.android.criminalintent.fragment.CrimeCameraFragment;

/**
 * Created by joseph on 2016/7/20.
 */
public class CrimeCameraActivity extends BaseActivitySingleFragment{
    private static final String TAG = "CrimeCameraActivity";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }
}
