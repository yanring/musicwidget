package com.example.yanring.a4yanring;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Yanring on 2016/2/20.
 */
public class MusicService extends Service {

    private MediaPlayer mMediaPlayer;
    String[] mMusicNames = new String[]{"animals", "starship", "summertrain"};
    int resid[] = new int[]{R.raw.animals, R.raw.starship, R.raw.summertrain};
    private int mCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(this, R.raw.animals);
    }


        @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            if (TextUtils.equals(intent.getStringExtra("action"), "pre")){
                mMediaPlayer.stop();
                mCount = (mCount - 1) % (resid.length+1);
                mCount = Math.abs(mCount);
                mMediaPlayer = MediaPlayer.create(this, resid[mCount]);
                mMediaPlayer.start();
            } else if (TextUtils.equals(intent.getStringExtra("action"), "next")) {
                mMediaPlayer.stop();
                mCount = (mCount - 1) % (resid.length+1);
                mCount = Math.abs(mCount);
                mMediaPlayer = MediaPlayer.create(this, resid[mCount]);
                mMediaPlayer.start();
            } else if (TextUtils.equals(intent.getStringExtra("action"), "start")) {

                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                } else {
                    mMediaPlayer.start();
                }
            }
        }
            Intent intentBack = new Intent(this,MusicWidget.class);
            intentBack.setAction("MessageFromService");
            intentBack.putExtra("MusicName",mMusicNames[mCount]);
            intentBack.putExtra("state",mMediaPlayer.isPlaying());
            sendBroadcast(intentBack);
            return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}