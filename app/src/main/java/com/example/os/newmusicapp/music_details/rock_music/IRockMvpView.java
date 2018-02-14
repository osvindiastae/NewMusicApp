package com.example.os.newmusicapp.music_details.rock_music;

import com.example.os.newmusicapp.data_model.network.model.MusicDetails;
import com.example.os.newmusicapp.ui.base.MvpView;

/**
 * Created by Os on 13/02/2018.
 */

public interface IRockMvpView extends MvpView{

    void onFetchDataProgress();
    void onFetchDataSuccess(MusicDetails musicDetails);
    void onFetchDataError(String error);
}
