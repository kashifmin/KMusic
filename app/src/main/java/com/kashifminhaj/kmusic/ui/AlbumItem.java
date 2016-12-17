package com.kashifminhaj.kmusic.ui;

import java.io.Serializable;

/**
 * Created by kashif on 16/12/16.
 */

public class AlbumItem implements Serializable {
    private long id;
    private String name;
    private int numOfSongs;
    private String thumbnail;

    public AlbumItem() {
    }

    public AlbumItem(long id, String name, int numOfSongs, String thumbnail) {
        this.id = id;
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

