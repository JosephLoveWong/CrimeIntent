package com.bignerdranch.android.criminalintent.util;

import android.util.Log;

/**
 * Created by joseph on 2016/6/21.
 */
public class LogUtil {
    private static final String TAG = "Crime Intent";
    private static boolean logSwitch = true;

    public static void d(String tag, String msg, Throwable e){
        if(logSwitch){
            Log.d(TAG, "[ " + tag + " ]" + msg, e);
        }
    }

    public static void d(String tag, String msg){
        if(logSwitch){
            Log.d(TAG, "[ " + tag + " ]" + msg);
        }
    }
}
