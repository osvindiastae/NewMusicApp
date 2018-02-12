package com.example.os.newmusicapp.realmdb.controller;

/**
 * Created by Os on 11/02/2018.
 */

import com.example.os.newmusicapp.realmdb.model.MusicData;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Realm Helper is class that is used to control the operations on the Realm Database
 */
public class RealmHelper {

    private Realm realm;

    /**
     * RealmHelper Constructor with one parameter
     * @param realm
     */
    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    /**
     * Saves the data into the Realm Database has one parameter MusicData object,
     * the MusicData object acts as row in the Music Data Table
     * @param musicData
     */
    public void saveMusicData(final MusicData musicData){
        if (musicData != null){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(musicData);
                }
            });
        }
    }

    /**
     * getMusicData method returns a ArrayList<MusicData> which contains all the data from the
     * Music Data Table
     * @return
     */
    public ArrayList<MusicData> getMusicData(){
        ArrayList<MusicData> musicDataArrayList = new ArrayList<>();

        RealmResults<MusicData> realmResults = realm.where(MusicData.class).findAll();
        for (MusicData currMusicData: realmResults) {
            musicDataArrayList.add(currMusicData);
        }

        return musicDataArrayList;
    }

    /**
     * deleteMusicData deletes all the data in the Music Data Table
     */
    public void deleteMusicData(){
        RealmResults<MusicData> realmResults = realm.where(MusicData.class).findAll();
        realmResults.deleteAllFromRealm();
    }
}
