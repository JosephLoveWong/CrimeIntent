package com.bignerdranch.android.criminalintent.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bignerdranch.android.criminalintent.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by joseph on 2016/7/20.
 */
public class CrimeCameraFragment extends Fragment{
    private static final String TAG = "CrimeCameraFragment";
    private SurfaceView mSurfaceView;
    private Button mButton;
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_camera, container, false);

        mButton = (Button) view.findViewById(R.id.crime_camera_takePictureButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCamera != null){
                    mCamera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            boolean success = true;
                            String fileName = UUID.randomUUID().toString() + ".jpg";
                            FileOutputStream outputStream = null;
                            try {
                                outputStream = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                                outputStream.write(data);
                            } catch (IOException e) {
                                success = false;
                                e.printStackTrace();
                            } finally {
                                if(outputStream != null){
                                    try {
                                        outputStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        outputStream = null;
                                    }
                                }
                            }

                            if(success){
                                Intent intent = new Intent();
                                intent.putExtra(CrimeFragment.EXTRA_CRIME_PHOTO_FILENAME, fileName);
                                getActivity().setResult(Activity.RESULT_OK, intent);
                            }else {
                                getActivity().setResult(Activity.RESULT_CANCELED);
                            }
                            getActivity().finish();
                        }
                    });
                }

            }
        });

        mSurfaceView = (SurfaceView) view.findViewById(R.id.crime_camera_surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(mCamera != null){
                    try {
                        mCamera.setPreviewDisplay(mSurfaceHolder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if(mCamera != null){
                    try{
                        mCamera.startPreview();
                    }catch (Exception e){
                        mCamera.release();
                        mCamera = null;
                    }
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(mCamera != null){
                    mCamera.stopPreview();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCamera = Camera.open(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }
}
