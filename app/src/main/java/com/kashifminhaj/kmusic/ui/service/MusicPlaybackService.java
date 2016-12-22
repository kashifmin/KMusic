/*
 * We use this service to manage all audio playback functions
 */
package com.kashifminhaj.kmusic.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.kashifminhaj.kmusic.R;
import com.kashifminhaj.kmusic.ui.SongItem;
import com.kashifminhaj.kmusic.ui.activity.NowPlayingActivity;
import com.kashifminhaj.kmusic.ui.util.Common;

import java.io.IOException;

public class MusicPlaybackService extends Service {
    private final static String LOGTAG = "MusicPlaybackService";
    // Android requires an interface aka binder for clients send access this service
    private final IBinder mBinder = new MyBinder();
    private MediaPlayer mAudioPlayer;
    private Context mContext;
    private Common mApp;
    private boolean mIsPlayerPrepared = false;
   // private SongItem currSong;

    private PreparedListener mPreparedListener;

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d(LOGTAG, "onStartCommand: Yay! Music service started...");
        mAudioPlayer = new MediaPlayer(); // initialize our MediaPlayer
        mApp = (Common) getApplicationContext();
        mApp.setService(this);
        mApp.setIsServiceRunning(true);
        mContext = mApp.getApplicationContext();


        setPreparedListener(mApp.getPlaybackStarter());
        mPreparedListener.onServiceRunning(this);


        return START_STICKY;
    }



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
        mIsPlayerPrepared = true;
        mAudioPlayer.start();
    //    currSong = newSong;
        showNotification(newSong);

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
            mIsPlayerPrepared = false;
        }
    }

    public void resetPlayer() {
        mAudioPlayer.reset();
        mIsPlayerPrepared = false;
    }

    public void resumeSong() {
        if(!mAudioPlayer.isPlaying()) {
            mAudioPlayer.start();
        }
    }

    public boolean isSongPlaying(){
        return mAudioPlayer.isPlaying();
    }

    public void togglePlaybackState() {
        if(!mIsPlayerPrepared) {
            return;
        }
        if(mAudioPlayer.isPlaying()) {
            mAudioPlayer.pause();
        } else {
            mAudioPlayer.start();
        }
    }

    public void setPreparedListener(PreparedListener mPreparedListener) {
        this.mPreparedListener = mPreparedListener;
    }

    public interface PreparedListener {
        void onServiceRunning(MusicPlaybackService playbackService);
    }

    public boolean isPlayerPrepared() {
        return mIsPlayerPrepared;
    }

    public void showNotification(SongItem songItem) {
        // TODO: Implement custom views fully and make the buttons work!

        Intent intent = new Intent(mContext, NowPlayingActivity.class);
        PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);

        /* A custom view for our notification */
        RemoteViews customNotfView = new RemoteViews(getPackageName(), R.layout.notification_custom);
        customNotfView.setTextViewText(R.id.notification_base_line_one, songItem.getTitle());
        customNotfView.setTextViewText(R.id.notification_base_line_two, songItem.getArtist());
        if(isSongPlaying()) {
            // If song is playing then we display a pause button
            customNotfView.setImageViewResource(R.id.notification_base_play, R.drawable.ic_pause_circle_filled_white_48dp);
        } else {
            // otherwise display play button
            customNotfView.setImageViewResource(R.id.notification_base_play, R.drawable.ic_play_circle_filled_white_48dp);
        }

        Notification.Builder notfBuilder = new Notification.Builder(mContext)
                .setContentTitle(songItem.getTitle())
                .setContentText(songItem.getArtist())
                .setContentIntent(pi)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true);


        Notification notification = notfBuilder.build();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1080, notification);

    }
}
