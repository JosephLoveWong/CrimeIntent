package com.bignerdranch.android.criminalintent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.activity.CrimeListActivity;
import com.bignerdranch.android.criminalintent.activity.CrimePagerActivity;
import com.bignerdranch.android.criminalintent.bean.Crime;
import com.bignerdranch.android.criminalintent.bean.CrimeLab;
import com.bignerdranch.android.criminalintent.util.LogUtil;

import java.util.List;

/**
 * Created by joseph on 2016/7/10.
 */
public class CrimeListFragment extends ListFragment {
    public static final int REQUESTCODE = 1;
    private static final String TAG = "CrimeListFragment";
    private List<Crime> mCrimes;
    private FloatingActionButton mFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimes = CrimeLab.getInstance(getActivity()).getmCrimes();

        ArrayAdapter<Crime> adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFab = ((CrimeListActivity) getActivity()).getFab();
        mFab.setVisibility(View.VISIBLE);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "add crime", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        LogUtil.d(TAG, "onClick " + position);

        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimePagerActivity.EXTRA_CRIME_ID, mCrimes.get(position).getID());

        startActivityForResult(intent, REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            // TODO
        }
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {


        public CrimeAdapter(List<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            Crime crime = getItem(position);
            TextView crimeTitle = (TextView) convertView.findViewById(R.id.crime_title);
            crimeTitle.setText(crime.getTitle());

            TextView crimeDate = (TextView) convertView.findViewById(R.id.crime_date);
            crimeDate.setText(crime.getDate().toString());

            CheckBox crimeSolved = (CheckBox) convertView.findViewById(R.id.crime_solved);
            crimeSolved.setChecked(crime.isSolved());

            return convertView;
        }
    }

}
