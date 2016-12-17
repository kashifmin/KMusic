package com.kashifminhaj.kmusic.ui;

import java.io.Serializable;

/**
 * Created by kashif on 11/12/16.
 */

public class SongItem implements Serializable{


    private String path;
    private long id;
    private String title;
    private String artist;
    private long albumId;

    public SongItem(long songId, String songTitle, String songArtist, String songPath, long songAlbumId) {
        id=songId;
        title = songTitle;
        artist = songArtist;
        path = songPath;
        albumId = songAlbumId;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}

    public String getPath() {
        return path;
    }

    public long getAlbumId() {
        return albumId;
    }
}
