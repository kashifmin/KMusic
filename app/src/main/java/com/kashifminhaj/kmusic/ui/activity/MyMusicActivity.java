package com.kashifminhaj.kmusic.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kashifminhaj.kmusic.R;
import com.kashifminhaj.kmusic.ui.AlbumItem;
import com.kashifminhaj.kmusic.ui.SongItem;
import com.kashifminhaj.kmusic.ui.fragment.AlbumsFragment;
import com.kashifminhaj.kmusic.ui.fragment.SongsFragment;
import com.kashifminhaj.kmusic.ui.helper.SongDBHelper;
import com.kashifminhaj.kmusic.ui.service.MusicPlaybackService;
import com.kashifminhaj.kmusic.ui.util.Common;

import java.io.IOException;

public class MyMusicActivity extends AppCompatActivity implements
        SongsFragment.OnListFragmentInteractionListener,
        AlbumsFragment.OnListFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TextView currSongTitle;
    private RelativeLayout currPlayingLayout;

    private ImageButton mPlayPauseButton;

    private SongDBHelper mSongDBHelper;

    private Common mApp;

    private MusicPlaybackService mPlayerService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });




        mSongDBHelper = new SongDBHelper(this);

        mApp = (Common) getApplicationContext();



        // The text displayed for song playing currently
        currSongTitle = (TextView) findViewById(R.id.curr_play_title);
        // get a reference for current song info displayed at the bottom
        currPlayingLayout = (RelativeLayout) findViewById(R.id.curr_play_layout);

        mPlayPauseButton = (ImageButton) findViewById(R.id.main_play_pauseBtn);

        currPlayingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here we launch a new activity that has main playback controls
                // like play/pause, next, previous etc


            }
        });

        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayerService == null) {
                    // ignore the click if the service is not initialized yet!
                    mPlayerService = mApp.getService();
                }
                if(!mPlayerService.isPlayerPrepared()) {
                    // if the song is not playing or paused then we just ignore for now
                    // TODO: play the song from last execution of the app
                    return;
                }
                if(mPlayerService.isSongPlaying()) {
                    animatePauseToPlay();
                } else {
                    animatePlayToPause();
                }
                mPlayerService.togglePlaybackState();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(SongItem item) {
        // TODO: play this song
        if(mPlayerService == null) {
            mPlayerService = mApp.getService();
        }
        try {
            if(!mPlayerService.isSongPlaying()) {
                mPlayerService.playSong(item);
               // currSongTitle.setText(item.getTitle());
            }
            else {
                mPlayerService.resetPlayer();
                mPlayerService.playSong(item);
            }
            animatePlayToPause();
            currSongTitle.setText(item.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListFragmentInteraction(AlbumItem item) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            switch(position) {
                case 0:
                    return SongsFragment.newInstance(1, mSongDBHelper.getAllSongsList());
                case 1:
                    return AlbumsFragment.newInstance(2, mSongDBHelper.getAllAlbumsList());
            }
            return SongsFragment.newInstance(1, mSongDBHelper.getAllSongsList());
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SONGS";
                case 1:
                    return "ALBUMS";
                case 2:
                    return "ARTISTS";
            }
            return null;
        }
    }

    private void animatePlayToPause() {
        // TODO: Add real animation!
        mPlayPauseButton.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
    }

    private void animatePauseToPlay() {
        mPlayPauseButton.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
    }


}
