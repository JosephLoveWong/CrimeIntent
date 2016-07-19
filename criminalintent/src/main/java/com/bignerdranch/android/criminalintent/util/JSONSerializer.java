package com.bignerdranch.android.criminalintent.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by joseph on 2016/7/19.
 */
public class JSONSerializer {
    private static final String TAG = "JSONSerializer";
    private static JSONSerializer sInstance = new JSONSerializer();
    private Gson mGson;

    private JSONSerializer(){
        mGson = new Gson();
    }

    public static JSONSerializer getInstance() {
        return sInstance;
    }

    public String toJson(Object object){
        return mGson.toJson(object);
    }

    public <T> T fromJson(String json, Type typeOfT){
        return (T)mGson.fromJson(json, typeOfT);
    }

}
