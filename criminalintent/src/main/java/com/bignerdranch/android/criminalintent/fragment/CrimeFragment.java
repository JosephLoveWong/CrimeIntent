package com.bignerdranch.android.criminalintent.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.activity.CrimeCameraActivity;
import com.bignerdranch.android.criminalintent.bean.Crime;
import com.bignerdranch.android.criminalintent.bean.CrimeLab;
import com.bignerdranch.android.criminalintent.bean.Photo;
import com.bignerdranch.android.criminalintent.util.LogUtil;
import com.bignerdranch.android.criminalintent.util.PictureUtil;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by joseph on 2016/6/21.
 */
public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.fragment.CrimeFragment.EXTRA_CRIME_ID";
    public static final String EXTRA_CRIME_PHOTO_FILENAME = "com.bignerdranch.android.criminalintent.fragment.CrimeFragment.EXTRA_CRIME_PHOTO_FILENAME";
    private static final String DIALOG_DATE_TAG = "com.bignerdranch.android.criminalintent.fragment.CrimeFragment.DIALOG_DATE_TAG";
    private static final String DIALOG_IMAGE_TAG = "com.bignerdranch.android.criminalintent.fragment.CrimeFragment.DIALOG_IMAGE_TAG";
    public static final int CODE_REQUEST = 0;
    public static final int CODE_REQUEST_CAMERA = 1;
    public static final int CODE_REQUEST_SUSPECT = 2;

    private Crime mCrime;
    private EditText mTitle;
    private CheckBox mSolved;
    private Button mCrimeDate;
    private ImageButton mCrimeCamera;
    private ImageView mCrimePhoto;
    private Button mSuspect;
    private Button mSendReport;
    private Photo mPhoto;

    public static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        UUID crimeId = (UUID) arguments.getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitle = (EditText) rootView.findViewById(R.id.crime_title);
        mTitle.setText(mCrime.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtil.d(TAG, "onTextChanged");
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSolved = (CheckBox) rootView.findViewById(R.id.crime_solved);
        mSolved.setChecked(mCrime.isSolved());
        mSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        mCrimeDate = (Button) rootView.findViewById(R.id.crime_date);
        mCrimeDate.setText(mCrime.getDate().toString());
        mCrimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, CODE_REQUEST);
                dialog.show(fm,DIALOG_DATE_TAG);
            }
        });

        mCrimeCamera = (ImageButton) rootView.findViewById(R.id.crime_camera);
        if(!checkCameraHardware(getActivity())){
            mCrimeCamera.setEnabled(false);
        }else{
            mCrimeCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CrimeCameraActivity.class);
                    startActivityForResult(intent, CODE_REQUEST_CAMERA);
                }
            });
        }

        mCrimePhoto = (ImageView) rootView.findViewById(R.id.crime_photo);
        mCrimePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = mCrime.getPhoto();
                if(photo != null){
                    String path = new File(getActivity().getFilesDir(), photo.getFileName()).getAbsolutePath();
                    ImageFragment imageFragment = ImageFragment.newInstance(path);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    imageFragment.show(fragmentManager,DIALOG_IMAGE_TAG);
                }
            }
        });

        mSuspect = (Button) rootView.findViewById(R.id.crime_suspect);
        mSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST_SUSPECT);
            }
        });

        mSendReport = (Button) rootView.findViewById(R.id.crime_report);
        mSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(intent.EXTRA_TEXT, getCrimeReport());
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_suspect));

                intent = Intent.createChooser(intent, getString(R.string.send_report));

                startActivity(intent);
            }
        });

        if(mCrime.getSuspect() != null){
            mSuspect.setText(mCrime.getSuspect());
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getInstance(getActivity()).saveCrimes();
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtil.cleanImageView(mCrimePhoto);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CODE_REQUEST){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_CRIME_DATE);
            mCrimeDate.setText(date.toString());
            mCrime.setDate(date);
        } else if(requestCode == CODE_REQUEST_CAMERA){
            String photoFileName = data.getStringExtra(EXTRA_CRIME_PHOTO_FILENAME);
            mPhoto = new Photo();
            mPhoto.setFileName(photoFileName);
            mCrime.setPhoto(mPhoto);

            showPhoto();
        } else if(requestCode == CODE_REQUEST_SUSPECT){
            Uri contactUri = data.getData();
            String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY };
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);

            if (c.getCount() == 0) {
                c.close();
                return;
            }

            c.moveToFirst();
            String suspect = c.getString(0);
            mCrime.setSuspect(suspect);
            mSuspect.setText(suspect);
            c.close();
        }
    }

    private void showPhoto() {
        Photo photo = mCrime.getPhoto();
        if(photo != null){
            File file = new File(getActivity().getFilesDir(), photo.getFileName());
            if(file.exists()){
                String path = file.getAbsolutePath();
                mCrimePhoto.setImageDrawable(PictureUtil.getScaledBitmap(getActivity(), path));
            }
        }
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);

        return report;
    }
}
