package com.kashifminhaj.kmusic.ui.util;

import android.app.Application;
import android.content.Context;

import com.kashifminhaj.kmusic.ui.PlaybackStarter;
import com.kashifminhaj.kmusic.ui.service.MusicPlaybackService;

/**
 * Created by kashif on 16/12/16.
 *
 * This class manages services used across various components in the app
 * For now, we have to manage only the MusicPlaybackService
 */

public class Common extends Application {

    private Context mContext;
    private MusicPlaybackService mService;
    private boolean mIsServiceRunning = false;
    private PlaybackStarter mPlaybackStarter;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mPlaybackStarter = new PlaybackStarter(mContext);
    }

    public MusicPlaybackService getService() {
        return mService;
    }

    public void setService(MusicPlaybackService service) {
        this.mService = service;
    }

    public boolean isServiceRunning() {
        return mIsServiceRunning;
    }

    public void setIsServiceRunning(boolean isServiceRunning) {
        this.mIsServiceRunning = isServiceRunning;
    }


    public PlaybackStarter getPlaybackStarter() {
        return mPlaybackStarter;
    }
}
