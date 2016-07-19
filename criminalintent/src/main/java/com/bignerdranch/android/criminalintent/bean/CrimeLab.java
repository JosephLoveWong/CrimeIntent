package com.bignerdranch.android.criminalintent.bean;

import android.content.Context;

import com.bignerdranch.android.criminalintent.util.JSONSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by joseph on 2016/7/10.
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    public static final String CRIME_LAB = "crimeLab";
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private String mFileName;
    private JSONSerializer serializer;

    private List<Crime> mCrimes;

    private CrimeLab(Context context, String fileName) {
        mContext = context.getApplicationContext();
        mFileName = fileName;
        serializer = JSONSerializer.getInstance();

        mCrimes = loadCrimes();
        if(mCrimes == null){
            mCrimes = new ArrayList<Crime>();
        }
    }

    public static CrimeLab getInstance(Context context) {
        if (sCrimeLab == null) {
            synchronized (CrimeLab.class) {
                if (sCrimeLab == null) {
                    sCrimeLab = new CrimeLab(context, CRIME_LAB);
                }
            }
        }
        return sCrimeLab;
    }

    public List<Crime> getmCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID crimeId) {
        for (Crime crime : mCrimes) {
            if (crime.getID().equals(crimeId)) {
                return crime;
            }
        }
        return null;
    }

    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

    public void saveCrimes() {
        String json = serializer.toJson(mCrimes);
        OutputStreamWriter writer = null;
        try {
            FileOutputStream outputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    writer = null;
                }
            }
        }
    }

    public List<Crime> loadCrimes(){
        ArrayList<Crime> crimes = null;
        BufferedReader reader = null;
        try {
            FileInputStream inputStream = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder json = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                json.append(line);
            }

            Type type = new TypeToken<ArrayList<Crime>>() {}.getType();
            crimes = serializer.fromJson(json.toString(), type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    reader = null;
                }
            }
        }
        return crimes;
    }
}
