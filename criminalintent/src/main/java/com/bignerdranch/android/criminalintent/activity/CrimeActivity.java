package com.bignerdranch.android.criminalintent.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminalintent.fragment.CrimeFragment;
import com.bignerdranch.android.criminalintent.util.LogUtil;

import java.util.UUID;

public class CrimeActivity extends BaseActivitySingleFragment {

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("tt",",,,,,,");
    }
}
