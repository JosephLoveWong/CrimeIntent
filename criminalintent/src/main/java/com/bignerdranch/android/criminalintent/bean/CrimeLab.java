package com.bignerdranch.android.criminalintent.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by joseph on 2016/7/10.
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static CrimeLab sCrimeLab;
    private Context mContext;

    private List<Crime> mCrimes;

    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mCrimes = new ArrayList<Crime>();

        for(int i = 0;i < 100;i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public static CrimeLab getInstance(Context context) {
        if(sCrimeLab == null){
            synchronized (CrimeLab.class){
                if(sCrimeLab == null){
                    sCrimeLab  = new CrimeLab(context);
                }
            }
        }
        return sCrimeLab;
    }

    public List<Crime> getmCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID crimeId){
        for(Crime crime: mCrimes){
            if(crime.getID().equals(crimeId)){
                return crime;
            }
        }
        return null;
    }
}
