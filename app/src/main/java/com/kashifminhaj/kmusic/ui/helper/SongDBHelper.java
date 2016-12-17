package com.kashifminhaj.kmusic.ui.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.kashifminhaj.kmusic.ui.AlbumItem;
import com.kashifminhaj.kmusic.ui.SongItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kashif on 15/12/16.
 */

public class SongDBHelper {
    private Context mContext;

    public SongDBHelper(Context context) {
        mContext = context;
    }

    public List<SongItem> getAllSongsList(){
        List<SongItem> songList = new ArrayList<>();

        //query external audio
        ContentResolver musicResolver = mContext.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        //iterate over results if valid`
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisPath = musicCursor.getString(pathColumn);

                songList.add(new SongItem(thisId, thisTitle, thisArtist, thisPath ));
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();

        return songList;

    }

    public List<AlbumItem> getAllAlbumsList(){
        List<AlbumItem> albumList = new ArrayList<>();

        //query external audio
        ContentResolver musicResolver = mContext.getContentResolver();
        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        //iterate over results if valid`
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM_KEY);
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM);
            int countColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS);
            int artColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM_ART);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                int thisCount = musicCursor.getInt(countColumn);
                String thisArt= musicCursor.getString(artColumn);

                albumList.add(new AlbumItem(thisId, thisTitle, thisCount, thisArt ));
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();
        return albumList;

    }


}
