package com.christopherbahn;

/**
 * Created by christopherbahn on 5/11/15.
 */
public class Song {
    int trackNumber;
    String title;
    int duration;
    String audioURL;

    public Song(int trackNumber, String title, int duration, String audioURL) {
        this.trackNumber = trackNumber;
        this.title = title;
        this.duration = duration;
        this.audioURL = audioURL;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    @Override
    public String toString() {
        return "Song{" +
                "trackNumber=" + trackNumber +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", audioURL='" + audioURL + '\'' +
                '}';
    }
}
