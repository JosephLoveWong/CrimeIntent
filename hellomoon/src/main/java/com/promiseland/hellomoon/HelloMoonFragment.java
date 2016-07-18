package com.promiseland.hellomoon;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by joseph on 2016/7/16.
 */
public class HelloMoonFragment extends Fragment {
    private static final String TAG = "HelloMoonFragment";
    private Button mPlay;
    private Button mStop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);
        mPlay = (Button) view.findViewById(R.id.play);
        mStop = (Button) view.findViewById(R.id.stop);

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.getInstance(getActivity()).play();
            }
        });
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.getInstance(getActivity()).stop();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AudioPlayer.getInstance(getActivity()).stop();
    }
}
