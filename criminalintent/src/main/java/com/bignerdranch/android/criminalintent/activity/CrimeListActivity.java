package com.bignerdranch.android.criminalintent.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.bean.Crime;
import com.bignerdranch.android.criminalintent.fragment.CrimeFragment;
import com.bignerdranch.android.criminalintent.fragment.CrimeListFragment;

/**
 * Created by joseph on 2016/7/10.
 */
public class CrimeListActivity extends BaseActivitySingleFragment implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{
    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.detail_fragment_container) == null){
            // 手机
            Intent intent = new Intent(this, CrimePagerActivity.class);
            intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getID());
            startActivityForResult(intent, CrimeListFragment.REQUESTCODE);
        }else {
            // 平板
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment oldFragment = fragmentManager.findFragmentById(R.id.detail_fragment_container);
            CrimeFragment newFragment = CrimeFragment.newInstance(crime.getID());
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(oldFragment != null){
                transaction.remove(oldFragment);
            }
            transaction.add(R.id.detail_fragment_container, newFragment);
            transaction.commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        ((CrimeListFragment)fragment).updateUI();
    }
}
