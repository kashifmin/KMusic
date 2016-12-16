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

    public SongItem(long songId, String songTitle, String songArtist, String songPath) {
        id=songId;
        title = songTitle;
        artist = songArtist;
        path = songPath;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}

    public String getPath() {
        return path;
    }


}
