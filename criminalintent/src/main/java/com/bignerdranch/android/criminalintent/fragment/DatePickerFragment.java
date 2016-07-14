package com.bignerdranch.android.criminalintent.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.bignerdranch.android.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by joseph on 2016/7/14.
 */
public class DatePickerFragment extends DialogFragment {
    private static final String TAG = "DatePickerFragment";
    public static final String EXTRA_CRIME_DATE = "com.bignerdranch.android.criminalintent.fragment.DatePickerFragment.EXTRA_CRIME_DATE";
    private Date mDate;

    public static DatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_CRIME_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePicker datePicker = (DatePicker) getActivity().getLayoutInflater().inflate(R.layout.dialog_date_crime, null);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                getArguments().putSerializable(EXTRA_CRIME_DATE, mDate);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(datePicker)
                .setTitle(R.string.title_crime_date)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null){
            return;
        }

        Intent data = new Intent();
        data.putExtra(DatePickerFragment.EXTRA_CRIME_DATE, mDate);
        getTargetFragment().onActivityResult(CrimeFragment.CODE_REQUEST, resultCode, data);
    }
}
