/*
 * We use this service to manage all audio playback functions
 */
package com.kashifminhaj.kmusic.ui.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.kashifminhaj.kmusic.ui.SongItem;

import java.io.IOException;

public class MusicPlaybackService extends Service {
    private final static String LOGTAG = "MusicPlaybackService";
    // Android requires an interface aka binder for clients send access this service
    private final IBinder mBinder = new MyBinder();
    private MediaPlayer mAudioPlayer;
    private Context mContext;
   // private SongItem currSong;

    public MusicPlaybackService() {
    }

    /*
     * A class that can be used as a Local Binder
     */
    public class MyBinder extends Binder {
        public MusicPlaybackService getService() {
            return MusicPlaybackService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        super.onStartCommand(intent, flags, startId);
//
//        Log.d(LOGTAG, "Yay! Music service started...");
//        mAudioPlayer = new MediaPlayer(); // initialize our MediaPlayer
//
//        return START_STICKY;
//    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOGTAG, "Yay! Music service started...");
        mAudioPlayer = new MediaPlayer(); // initialize our MediaPlayer
    }

    public void playSong(SongItem newSong) throws IOException {
        mAudioPlayer.reset();
        mAudioPlayer.setDataSource(newSong.getPath());
        mAudioPlayer.prepare();
        mAudioPlayer.start();
    //    currSong = newSong;

    }



    public void pauseSong() {
        if(mAudioPlayer.isPlaying()) {
            mAudioPlayer.pause();
        }
    }

    public void stopSong() {
        if(mAudioPlayer.isPlaying()) {
            mAudioPlayer.stop();
            mAudioPlayer.release();
        }
    }

    public void resetPlayer() {
        mAudioPlayer.reset();
    }

    public void resumeSong() {
        if(!mAudioPlayer.isPlaying()) {
            mAudioPlayer.start();
        }
    }

    public boolean isSongPlaying(){
        return mAudioPlayer.isPlaying();
    }
}
