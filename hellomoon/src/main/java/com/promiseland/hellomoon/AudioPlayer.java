package com.promiseland.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by joseph on 2016/7/16.
 */
public class AudioPlayer {
    private static final String TAG = "AudioPlayer";

    private static Context mContext;
    private MediaPlayer mediaPlayer;

    private AudioPlayer() {
    }

    private static AudioPlayer sInstance = new AudioPlayer();

    public static AudioPlayer getInstance(Context context) {
        mContext = context.getApplicationContext();
        return sInstance;
    }

    public void play() {
        stop();
        mediaPlayer = MediaPlayer.create(mContext, R.raw.one_small_step);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });
        mediaPlayer.start();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
