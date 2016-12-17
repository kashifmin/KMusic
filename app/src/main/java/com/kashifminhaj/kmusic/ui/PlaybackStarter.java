package com.kashifminhaj.kmusic.ui;

import android.content.Context;
import android.content.Intent;

import com.kashifminhaj.kmusic.ui.service.MusicPlaybackService;
import com.kashifminhaj.kmusic.ui.util.Common;

/**
 * Created by kashif on 16/12/16.
 *
 * This class implement's our Service's onPrepared Listener
 * We use the interface to initialize the Service object
 * in our Common class.
 */

public class PlaybackStarter implements MusicPlaybackService.PreparedListener {

    private Context mContext;
    private Common mApp;

    public PlaybackStarter(Context context) {
        mContext = context;

        context.startService(new Intent(context, MusicPlaybackService.class));
    }


    @Override
    public void onServiceRunning(MusicPlaybackService theService) {
        // get the Common Application Object
        mApp = (Common) mContext.getApplicationContext();

        // Set the Service that called this as our PlaybackService
        mApp.setService(theService);
        mApp.setIsServiceRunning(true);
    }
}
