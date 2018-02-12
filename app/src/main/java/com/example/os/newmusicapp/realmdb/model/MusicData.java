package com.example.os.newmusicapp.realmdb.model;

/**
 * Created by Os on 11/02/2018.
 */

import io.realm.RealmObject;

/**
 * Music Data is a Realm Object Class use to store the rows of Music Data in a Table.
 * The class also has getters and setter to access the data
 */
public class MusicData extends RealmObject {
    private String artistName;
    private String collectionName;
    private String artworkUrl60;
    private String trackPrice;

    public MusicData() {
    }

    public MusicData(String artistName, String collectionName, String artworkUrl60, String trackPrice) {
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.artworkUrl60 = artworkUrl60;
        this.trackPrice = trackPrice;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }
}
