package com.kashifminhaj.kmusic.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kashifminhaj.kmusic.R;
import com.kashifminhaj.kmusic.ui.SongItem;
import com.kashifminhaj.kmusic.ui.helper.SongDBHelper;
import com.kashifminhaj.kmusic.ui.service.MusicPlaybackService;
import com.kashifminhaj.kmusic.ui.util.Common;

public class NowPlayingActivity extends AppCompatActivity {

    private Common mApp;
    private MusicPlaybackService mPlaybackService;

    private ImageView mNowPlayingArt;
    private TextView mNowPlayingTitle;
    private TextView mNowPlayingArtist;

    private SongItem mNowPlayingSong;

    private SongDBHelper mSongDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        mApp = (Common) getApplicationContext();
        mPlaybackService = mApp.getService();
        mSongDBHelper = new SongDBHelper(this);

        mNowPlayingArt = (ImageView) findViewById(R.id.now_ply_album_art);
        mNowPlayingTitle = (TextView) findViewById(R.id.now_ply_song_title);
        mNowPlayingArtist = (TextView) findViewById(R.id.now_ply_song_artist);


        mNowPlayingSong = mApp.getNowPlayingSong();

        mNowPlayingTitle.setText(mNowPlayingSong.getTitle());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Uri uri = mSongDBHelper.getAlbumArtForSong(mNowPlayingSong.getAlbumId());
                    Log.d("NowPlaying: " , "Trying uri: " + uri.toString());
                    mNowPlayingArt.setImageURI(uri);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mNowPlayingArtist.setText(mNowPlayingSong.getArtist());

    }
}
