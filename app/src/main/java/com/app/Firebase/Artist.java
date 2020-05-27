package com.app.Firebase;

/**
 * CREATED BY SANJAIKUMAR On 27-05-2020
 */
public class Artist {


    String artistId;
    String artistName;
    String artistGenre;


    public Artist() {

    }

    public Artist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}
