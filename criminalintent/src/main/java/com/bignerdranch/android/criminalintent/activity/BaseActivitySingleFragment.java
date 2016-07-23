package com.bignerdranch.android.criminalintent.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.bignerdranch.android.criminalintent.R;

/**
 * Created by joseph on 2016/7/10.
 */
public abstract class BaseActivitySingleFragment extends FragmentActivity {
    private static final String TAG = "BaseActivitySingleFragment";
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        mFab = (FloatingActionButton)findViewById(R.id.fab_add);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    public FloatingActionButton getFab() {
        return mFab;
    }

    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    protected abstract Fragment createFragment();



}
