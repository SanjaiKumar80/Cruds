package com.app.Firebase;

/**
 * CREATED BY SANJAIKUMAR On 27-05-2020
 */
public class Track {
    private String trackId;
    private String trackName;
    private int trackRating;

    public Track() {

    }

    public Track(String trackId, String trackName, int trackRating) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackRating = trackRating;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getTrackRating() {
        return trackRating;
    }
}
