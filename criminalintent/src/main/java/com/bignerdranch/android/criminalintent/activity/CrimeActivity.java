package com.bignerdranch.android.criminalintent.activity;


import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminalintent.fragment.CrimeFragment;

import java.util.UUID;

public class CrimeActivity extends BaseActivitySingleFragment {

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
