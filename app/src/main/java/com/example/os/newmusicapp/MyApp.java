package com.example.os.newmusicapp;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Os on 10/02/2018.
 */

public class MyApp extends Application {
    private static MyApp sInstance;
    private static Context context;

    public static MyApp getInstance(){
        if (sInstance == null){
            sInstance = new MyApp();
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        // init Realm
        configureRealm();
    }

    public Context getAppContext(){
        return context;
    }

    public void configureRealm(){
        Realm.init(getApplicationContext());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("MusicDataBase")          // Name of the database
                .schemaVersion(1)               // version is needed when upgrading the database
                .deleteRealmIfMigrationNeeded() // will delete old old table
                .build();                       // build the database with specifications

        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
