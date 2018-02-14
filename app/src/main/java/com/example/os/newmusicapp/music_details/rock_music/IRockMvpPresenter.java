package com.example.os.newmusicapp.music_details.rock_music;

import com.example.os.newmusicapp.ui.base.MvpPresenter;

/**
 * Created by Os on 13/02/2018.
 */

public interface IRockMvpPresenter<V extends IRockMvpView> extends MvpPresenter<V>{
    void loadMusicDetailsList();
}
