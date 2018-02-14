package com.example.os.newmusicapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.os.newmusicapp.music_details.classic_music.ClassicFragment;
import com.example.os.newmusicapp.music_details.pop_music.PopFragment;
import com.example.os.newmusicapp.music_details.rock_music.RockFragment;
import com.example.os.newmusicapp.realmdb.controller.RealmHelper;
import com.example.os.newmusicapp.realmdb.model.MusicData;

import java.util.ArrayList;

import io.realm.Realm;

// Music API App
public class MainActivity extends AppCompatActivity {

    // Declared Fragment Manager
    private static FragmentManager fragmentManager;

    // Declare Realm Variables
    private Realm realm;
    private static RealmHelper realmHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        // Stitch case are used to switch between the fragments
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_rock:
                    // TODO: Rock fragment will load
                    loadRock();
                    return true;
                case R.id.navigation_classic:
                    // TODO: Classic fragment will load
                    loadClassic();
                    return true;
                case R.id.navigation_pop:
                    // TODO: Pop fragment will load
                    loadPop();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // FragmentManager initialised
        fragmentManager = getSupportFragmentManager();

        // Realm initiated
        initRealm();

        // sets the Rock fragment as a default fragment when the app loads
        if (savedInstanceState == null){
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, new RockFragment())
                    .disallowAddToBackStack()
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to close the app?")
                .setTitle("Terminate Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * method to load the Rock fragment
     */
    private void loadRock(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, new RockFragment())
                .disallowAddToBackStack()
                .commit();
    }

    /**
     * method to load the Classic Fragment
     */
    private void loadClassic(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, new ClassicFragment())
                .disallowAddToBackStack()
                .commit();
    }

    /**
     * method to load the pop fragment
     */
    private void loadPop(){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, new PopFragment())
                .disallowAddToBackStack()
                .commit();
    }

    /**
     * method to initiate the realm variables
     */
    private void initRealm(){
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
    }

    /**
     * Static method to save the data into Realm Database it has four parameters
     * @param artistName
     * @param collectionName
     * @param artworkUrl60
     * @param trackPrice
     */
    public static void saveToRealm(String artistName, String collectionName, String artworkUrl60, String trackPrice){
        MusicData sMusicData = new MusicData(artistName, collectionName, artworkUrl60, trackPrice);
        realmHelper.saveMusicData(sMusicData);
        Log.i("REALM_SIZE", String.valueOf(realmHelper.getMusicData().size()));
    }

    /**
     * Static method that returns the ArrayList<MusicData> from the Realm Database
     * @return ArrayList<MusicData>
     */
    public static ArrayList<MusicData> getRealmData(){
        Log.i("REALM_SIZE", String.valueOf(realmHelper.getMusicData().size()));
        return realmHelper.getMusicData();
    }

    /**
     * Static method that deletes the old data before adding the newly refreshed data from the api
     */
    public static void deleteRealmData(){
        realmHelper.deleteMusicData();
    }

}
