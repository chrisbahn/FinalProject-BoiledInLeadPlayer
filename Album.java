package com.christopherbahn;
import java.util.*;

/**
 * Created by christopherbahn on 5/11/15.
 */
public class Album {
    int releaseOrder;
    String albumName;
    int year;
    LinkedList<Song> tracks;
    String albumCover;

    public Album(int releaseOrder, String albumName, int year, LinkedList<Song> tracks, String albumCover) {
        this.releaseOrder = releaseOrder;
        this.albumName = albumName;
        this.year = year;
        this.tracks = tracks;
        this.albumCover = albumName;
    }

    public int getReleaseOrder() {
        return releaseOrder;
    }

    public void setReleaseOrder(int releaseOrder) {
        this.releaseOrder = releaseOrder;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LinkedList<Song> getTracks() {
        return tracks;
    }

    public void setTracks(LinkedList<Song> tracks) {
        this.tracks = tracks;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    @Override
    public String toString() {
        return "Album{" +
                "releaseOrder=" + releaseOrder +
                ", albumName='" + albumName + '\'' +
                ", year=" + year +
                ", tracks=" + tracks +
                '}';
    }
}




