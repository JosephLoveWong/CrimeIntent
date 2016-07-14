package com.bignerdranch.android.criminalintent.activity;


import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminalintent.fragment.CrimeListFragment;

/**
 * Created by joseph on 2016/7/10.
 */
public class CrimeListActivity extends BaseActivitySingleFragment{
    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
